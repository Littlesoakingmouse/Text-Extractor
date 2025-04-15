package com.example.imgextractor.config;

import net.sourceforge.tess4j.Tesseract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.io.File;

@Configuration
public class TesseractConfig {
    
    @Bean
    public Tesseract tesseract() {
        Tesseract tesseract = new Tesseract();
        
        // Try different possible paths for tessdata
        String[] possiblePaths = {
            "C:\\Program Files\\Tesseract-OCR\\tessdata",  // Default Windows installation
            "C:\\Program Files (x86)\\Tesseract-OCR\\tessdata",  // 32-bit Windows installation
            "src/main/resources/tessdata",  // Project resources
            "E:\\Java\\Resource\\tessdata"  // Your custom path
        };
        
        for (String path : possiblePaths) {
            File tessdataDir = new File(path);
            if (tessdataDir.exists() && tessdataDir.isDirectory()) {
                tesseract.setDatapath(path);
                System.out.println("Tesseract data path set to: " + path);
                return tesseract;
            }
        }
        
        // If no valid path found, throw an exception
        throw new RuntimeException("Could not find Tesseract data directory. Please ensure Tesseract is installed and the tessdata directory exists.");
    }
} 