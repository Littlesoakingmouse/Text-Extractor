package com.example.imgextractor.controller;

import com.example.imgextractor.service.OcrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class OcrController {

    @Autowired
    private OcrService ocrService;

    @PostMapping("/process")
    public ResponseEntity<?> processImage(@RequestParam("file") MultipartFile file) {
        try {
            String processedImageBase64 = ocrService.processImage(file);
            Map<String, String> response = new HashMap<>();
            response.put("processedImage", processedImageBase64);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PostMapping("/extract")
    public ResponseEntity<?> extractText() {
        try {
            String extractedText = ocrService.extractText();
            Map<String, String> response = new HashMap<>();
            response.put("text", extractedText);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
} 