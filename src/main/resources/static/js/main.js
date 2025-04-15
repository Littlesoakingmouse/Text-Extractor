document.addEventListener('DOMContentLoaded', function() {
    const dropZone = document.getElementById('dropZone');
    const fileInput = document.getElementById('fileInput');
    const uploadBtn = document.getElementById('uploadBtn');
    const imagePreview = document.getElementById('imagePreview');
    const extractedText = document.getElementById('extractedText');
    const loading = document.getElementById('loading');
    const languageSelect = document.getElementById('language');
    const downloadTxtBtn = document.getElementById('downloadTxt');
    const downloadDocxBtn = document.getElementById('downloadDocx');

    // Handle drag and drop
    dropZone.addEventListener('dragover', (e) => {
        e.preventDefault();
        dropZone.classList.add('dragover');
    });

    dropZone.addEventListener('dragleave', () => {
        dropZone.classList.remove('dragover');
    });

    dropZone.addEventListener('drop', (e) => {
        e.preventDefault();
        dropZone.classList.remove('dragover');
        const file = e.dataTransfer.files[0];
        handleFile(file);
    });

    // Handle file input
    uploadBtn.addEventListener('click', () => {
        fileInput.click();
    });

    fileInput.addEventListener('change', (e) => {
        const file = e.target.files[0];
        handleFile(file);
    });

    function handleFile(file) {
        if (!file.type.startsWith('image/')) {
            alert('Please upload an image file');
            return;
        }

        // Show preview
        const reader = new FileReader();
        reader.onload = function(e) {
            imagePreview.src = e.target.result;
            document.querySelector('.preview-container').hidden = false;
        };
        reader.readAsDataURL(file);

        // Upload and process image
        uploadImage(file);
    }

    async function uploadImage(file) {
        const formData = new FormData();
        formData.append('file', file);
        formData.append('language', languageSelect.value);

        loading.hidden = false;
        document.querySelector('.result-container').hidden = true;

        try {
            const response = await fetch('/api/ocr/extract', {
                method: 'POST',
                body: formData
            });

            const data = await response.json();
            
            if (response.ok) {
                extractedText.value = data.text;
                document.querySelector('.result-container').hidden = false;
            } else {
                alert('Error: ' + data.error);
            }
        } catch (error) {
            alert('Error processing image: ' + error.message);
        } finally {
            loading.hidden = true;
        }
    }

    // Download handlers
    downloadTxtBtn.addEventListener('click', () => {
        downloadText('txt');
    });

    downloadDocxBtn.addEventListener('click', () => {
        downloadText('docx');
    });

    function downloadText(format) {
        const text = extractedText.value;
        const blob = new Blob([text], { type: 'text/plain' });
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = `extracted-text.${format}`;
        document.body.appendChild(a);
        a.click();
        window.URL.revokeObjectURL(url);
        document.body.removeChild(a);
    }
}); 