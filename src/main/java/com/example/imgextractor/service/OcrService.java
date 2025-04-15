package com.example.imgextractor.service;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;

@Service
public class OcrService {

    static {
        nu.pattern.OpenCV.loadShared();
    }

    @Autowired
    private Tesseract tesseract;

    private Mat lastProcessedImage;

    public String processImage(MultipartFile file) throws IOException {
        // Convert MultipartFile to Mat
        byte[] bytes = file.getBytes();
        Mat originalImage = Imgcodecs.imdecode(new MatOfByte(bytes), Imgcodecs.IMREAD_COLOR);
        
        // Convert to grayscale
        Mat grayImage = new Mat();
        Imgproc.cvtColor(originalImage, grayImage, Imgproc.COLOR_BGR2GRAY);
        
        // Apply thresholding
        Mat thresholdImage = new Mat();
        Imgproc.threshold(grayImage, thresholdImage, 0, 255, Imgproc.THRESH_BINARY + Imgproc.THRESH_OTSU);
        
        // Store the processed image
        this.lastProcessedImage = thresholdImage;
        
        // Convert to base64 for frontend display
        MatOfByte processedBytes = new MatOfByte();
        Imgcodecs.imencode(".png", thresholdImage, processedBytes);
        return Base64.getEncoder().encodeToString(processedBytes.toArray());
    }

    public String extractText() throws IOException, TesseractException {
        if (lastProcessedImage == null) {
            throw new IllegalStateException("No processed image available. Please process an image first.");
        }

        // Convert Mat to BufferedImage for Tesseract
        MatOfByte processedBytes = new MatOfByte();
        Imgcodecs.imencode(".png", lastProcessedImage, processedBytes);
        BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(processedBytes.toArray()));
        
        // Perform OCR
        return tesseract.doOCR(bufferedImage);
    }
} 