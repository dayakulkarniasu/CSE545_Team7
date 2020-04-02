package com.sbs.sbsgroup7.service;

import com.sbs.sbsgroup7.DataSource.TransRepository;
import com.sbs.sbsgroup7.model.Transaction;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PdfService {

    @Autowired
    private TransRepository transRepository;

    public byte[] generatePdf(String userId, Long accountNumber) throws IOException {
        PDDocument pdDocument = new PDDocument();
        PDPage pdPage = new PDPage();
        PDFont pdfFont = PDType1Font.HELVETICA_BOLD;
        int fontSize = 28;
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

        List<Transaction> transactions = transRepository.findAll().stream()
                .filter(e -> e.getFromAccount().getAccountNumber().equals(accountNumber) || e.getToAccount().getAccountNumber().equals(accountNumber))
                .collect(Collectors.toList());

        if (transactions.size() == 0) {
            try (PDPageContentStream contentStream = new PDPageContentStream(pdDocument, pdPage, PDPageContentStream.AppendMode.APPEND, true, true)) {
                contentStream.setFont(pdfFont, fontSize);
                contentStream.beginText();
                contentStream.newLineAtOffset(200, 685);
                contentStream.showText("No records found");
                contentStream.endText();
            }
        } else {
            String[][] toInject = new String[transactions.size() + 1][5];
            toInject[0] = new String[]{"Index", "ToAccount", "Amount", "Type", "Date"};
            for (int i = 0; i < transactions.size(); i++) {
                toInject[i+1][0] = String.valueOf(i + 1);
                toInject[i+1][1] = transactions.get(i).getToAccount().getAccountNumber().toString();
                toInject[i+1][2] = transactions.get(i).getAmount().toString();
                toInject[i+1][3] = transactions.get(i).getTransactionType();
                Date transactionTime = Date.from(transactions.get(i).getTransactionTime());
                toInject[i+1][4] = formatter.format(transactionTime);
            }
            PDPageContentStream contentStream = new PDPageContentStream(pdDocument, pdPage);
            drawTable(pdPage, contentStream, 770, 20, toInject);
            contentStream.close();
        }
        pdDocument.addPage(pdPage);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        pdDocument.save(byteArrayOutputStream);
        pdDocument.close();

        return byteArrayOutputStream.toByteArray();
    }

    private static void drawTable(PDPage page, PDPageContentStream contentStream,
                                 float y, float margin,
                                 String[][] content) throws IOException {
        final int rows = content.length;
        final int cols = content[0].length;
        final float rowHeight = 20f;
        final float tableWidth = page.getMediaBox().getWidth() - margin - margin;
        final float tableHeight = rowHeight * rows;
        final float colWidth = tableWidth / (float) cols;
        final float cellMargin = 5f;

        //draw the rows
        float nexty = y ;
        for (int i = 0; i <= rows; i++) {
            contentStream.drawLine(margin, nexty, margin+tableWidth, nexty);
            nexty-= rowHeight;
        }

        //draw the columns
        float nextx = margin;
        for (int i = 0; i <= cols; i++) {
            contentStream.drawLine(nextx, y, nextx, y-tableHeight);
            nextx += colWidth;
        }

        //now add the text
        contentStream.setFont( PDType1Font.HELVETICA_BOLD , 10 );

        float textx = margin+cellMargin;
        float texty = y-15;
        for(int i = 0; i < content.length; i++){
            for(int j = 0 ; j < content[i].length; j++){
                String text = content[i][j];
                contentStream.beginText();
                contentStream.moveTextPositionByAmount(textx,texty);
                contentStream.drawString(text);
                contentStream.endText();
                textx += colWidth;
            }
            texty-=rowHeight;
            textx = margin+cellMargin;
        }
    }
}
