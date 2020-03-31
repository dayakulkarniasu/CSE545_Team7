import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Map;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;

protected void renderMergedOutputModel(Map<String, Object> model) throws Exception {
    Document document = new Document(PageSize.A4, 50, 50, 70, 50);
    PdfWriter writer = newWriter(document, baos);
    //prepareWriter(model, writer, request);


    // Build PDF document.
        document.open();
        buildPdfDocument(model, document, writer);
        document.close();

        // Flush to HTTP response.
        writeToResponse(response, baos);
}
protected void buildPdfDocument(Map<String, Object> model,
    Document document, PdfWriter writer) throws Exception {
    // TODO Auto-generated method stub
        //ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        //classLoader.getResourceAsStream("/views.properties");
        List<TransactionRequestDetails> balanceDetails=(List<TransactionRequestDetails>) model.get("transactionDetails");

        document.add(new Paragraph("Transaction Details of your accounts"));

        PdfPTable table = new PdfPTable(7);
        table.setWidthPercentage(100.0f);
        table.setWidths(new float[] {1.5f,1.5f,1.5f,1.5f,1.5f,1.5f,1.5f});
        table.setSpacingBefore(10);

        // define font for table header row
        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(BaseColor.WHITE);

        // define table header cell
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(BaseColor.BLUE);
        cell.setPadding(5);

        // write table header
        cell.setPhrase(new Phrase("Transaction Id", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Transaction Amount", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Source Account", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Destination Account", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Date and Time", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Transfer Type", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Status", font));
        table.addCell(cell);

        for(TransactionRequestDetails b:balanceDetails){
        table.addCell(b.getTransactionID());
        table.addCell(b.getTransactionAmount());
        table.addCell(String.valueOf(b.getSourceAccount()));
        table.addCell(String.valueOf(b.getDestAccount()));
        table.addCell(String.valueOf(b.getDateandTime()));
        table.addCell(String.valueOf(b.getTransferType()));
        table.addCell(String.valueOf(b.getStatus()));
        }
        document.add(table);
        }

        }


