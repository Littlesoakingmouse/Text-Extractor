Image Text Extractor
Project Overview
This is a web-based application that extracts text from images using OCR (Optical Character Recognition) technology. The application includes image preprocessing steps to improve text extraction accuracy.
Technical Stack
Backend: Spring Boot 3.2.3 (Java 17)
OCR Engine: Tesseract 5.11.0
Image Processing: OpenCV 4.7.0
Frontend: HTML, JavaScript, CSS
Key Features
1. Image Processing
Grayscale Conversion: Converts color images to grayscale for better text detection
Thresholding: Uses Otsu's thresholding method to create binary images
Preview: Shows the processed image before text extraction
2. Text Extraction
OCR Processing: Uses Tesseract engine for accurate text recognition
Multiple Language Support: Can detect text in various languages (based on installed Tesseract language packs)
Text Output: Displays extracted text in editable format
3. User Interface
Drag & Drop: Easy image upload through drag and drop interface
Two-Step Process:
Image processing (grayscale and threshold)
Text extraction
Download Options: Save extracted text as TXT file
