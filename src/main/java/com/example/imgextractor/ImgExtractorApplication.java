package com.example.imgextractor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import nu.pattern.OpenCV;

@SpringBootApplication
public class ImgExtractorApplication {
    
    static {
        // Initialize OpenCV
        OpenCV.loadShared();
    }

    public static void main(String[] args) {
        SpringApplication.run(ImgExtractorApplication.class, args);
    }
}