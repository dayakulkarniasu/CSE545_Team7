package com.sbs.sbsgroup7.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.PDSignature;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.SignatureInterface;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.nio.file.Files;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Calendar;
import com.sbs.sbsgroup7.model.Signature;

@Service
public class SigningService {

    private static final String KEY_STORE_TYPE = "jks";
    private final String keyStorePath;
    private final String keyStorePassword;
    private final String certificateAlias;

    public SigningService(@Value("${keystore.pdf}") String keyStorePath, @Value("${server.ssl.key-store-password}") String keyStorePassword, @Value("${certificate-alias}") String certificateAlias) {
        this.keyStorePath = keyStorePath;
        this.keyStorePassword = keyStorePassword;
        this.certificateAlias = certificateAlias;
    }

    public byte[] signPdf(byte[] pdfToSign) {
        try {
            KeyStore keyStore = this.getKeyStore();
            Signature signature = new Signature(keyStore, this.keyStorePassword.toCharArray(), certificateAlias);
            //create temporary pdf file
            File pdfFile = File.createTempFile("pdf", "");
            //write bytes to created pdf file
            FileUtils.writeByteArrayToFile(pdfFile, pdfToSign);

            //create empty pdf file which will be signed
            File signedPdf = File.createTempFile("signedPdf", "");
            //sign pdfFile and write bytes to signedPdf
            this.signDetached(signature, pdfFile, signedPdf);

            byte[] signedPdfBytes = Files.readAllBytes(signedPdf.toPath());

            //remove temporary files
            pdfFile.deleteOnExit();
            signedPdf.deleteOnExit();

            return signedPdfBytes;
        } catch (NoSuchAlgorithmException | CertificateException | UnrecoverableKeyException | KeyStoreException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }
        //if pdf cannot be signed, then return plain, not signed pdf
        return pdfToSign;
    }

    private KeyStore getKeyStore() throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException {
        KeyStore keyStore = KeyStore.getInstance(KEY_STORE_TYPE);
        File key = ResourceUtils.getFile(keyStorePath);
        keyStore.load(new FileInputStream(key), keyStorePassword.toCharArray());
        return keyStore;
    }

    private void signDetached(SignatureInterface signature, File inFile, File outFile) throws IOException {
        if (inFile == null || !inFile.exists()) {
            throw new FileNotFoundException("Document for signing does not exist");
        }
        try (FileOutputStream fos = new FileOutputStream(outFile);
             PDDocument doc = PDDocument.load(inFile)) {
            signDetached(signature, doc, fos);
        }
    }

    private void signDetached(SignatureInterface signature, PDDocument document, OutputStream output) throws IOException {
        PDSignature pdSignature = new PDSignature();
        pdSignature.setFilter(PDSignature.FILTER_ADOBE_PPKLITE);
        pdSignature.setSubFilter(PDSignature.SUBFILTER_ADBE_X509_RSA_SHA1);
        pdSignature.setName("Sparky's bank");
        pdSignature.setReason("Sparky's bank has signed and authorized this download");

        // the signing date, needed for valid signature
        pdSignature.setSignDate(Calendar.getInstance());

        // register signature dictionary and sign interface
        document.addSignature(pdSignature, signature);

        // write incremental (only for signing purpose)
        // use saveIncremental to add signature, using plain save method may break up a document
        document.saveIncremental(output);
    }
}
