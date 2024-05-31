/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qrproject;

/**
 *
 * @author adral
 */
import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class QRCodeGenerator {

    private static final String QR_CODE_IMAGE_PATH = "./QRCode.png";

    public static void main(String[] args) {
        // Example object
         String[] stringItems = {"item1", "item2", "item3"};
        MyObject<String> stringObject = new MyObject<>("StringObject", 1, stringItems);

        Integer[] intItems = {1, 2, 3};
        MyObject<Integer> intObject = new MyObject<>("IntObject", 2, intItems);

        // Utilisation des objets
        System.out.println("Name: " + stringObject.getName() + ", ID: " + stringObject.getId() + ", Items: " + Arrays.toString(stringObject.getItems()));
        System.out.println("Name: " + intObject.getName() + ", ID: " + intObject.getId() + ", Items: " + Arrays.toString(intObject.getItems()));
   

        // Convert object to JSON
        Gson gson = new Gson();
        String jsonObject = gson.toJson(stringObject);

        // Generate QR code
        generateQRCode(jsonObject, QR_CODE_IMAGE_PATH);

        // Simulate scanning the QR code and writing the content to a file
        simulateQRCodeScan(QR_CODE_IMAGE_PATH, "./output.txt");
    }

    public static void generateQRCode(String text, String filePath) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        int width = 300;
        int height = 300;
        String fileType = "png";

        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);

        try {
            BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height, hints);
            Path path = new File(filePath).toPath();
            MatrixToImageWriter.writeToPath(bitMatrix, fileType, path);
            System.out.println("QR Code generated and saved at: " + filePath);
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void simulateQRCodeScan(String qrCodeImagePath, String outputFilePath) {
        try {
            com.google.zxing.client.j2se.BufferedImageLuminanceSource source = new com.google.zxing.client.j2se.BufferedImageLuminanceSource(javax.imageio.ImageIO.read(new File(qrCodeImagePath)));
            com.google.zxing.BinaryBitmap bitmap = new com.google.zxing.BinaryBitmap(new com.google.zxing.common.HybridBinarizer(source));

            com.google.zxing.Result result = new com.google.zxing.qrcode.QRCodeReader().decode(bitmap);
            String textFromQRCode = result.getText();

            // Write the content to a file
            try (FileWriter writer = new FileWriter(outputFilePath)) {
                writer.write(textFromQRCode);
                System.out.println("Content from QR Code written to file: " + outputFilePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


