document.addEventListener('DOMContentLoaded', function() {
    const imageInput = document.getElementById('courseImages');
    const previewContainer = document.getElementById('imagePreviewContainer');
    const uploadBox = document.getElementById('uploadBox');
    const maxImages = 10;
    let currentImages = [];
    let isProcessing = false;

    // 업로드 박스 전체 영역 클릭 이벤트
    uploadBox.addEventListener('click', function(e) {
        if (!isProcessing) {
            imageInput.click();
        }
    });

    imageInput.addEventListener('click', function(e) {
        e.stopPropagation();
    });

    imageInput.addEventListener('change', function(e) {
        if (isProcessing) return;
        isProcessing = true;

        const files = Array.from(e.target.files);
        
        // 현재 이미지 개수와 새로 추가될 이미지 개수의 합이 최대 개수를 초과하는지 확인
        if (currentImages.length + files.length > maxImages) {
            alert(`이미지는 최대 ${maxImages}장까지 업로드 가능합니다.`);
            isProcessing = false;
            return;
        }

        // 파일 크기 체크
        const maxSizePerFile = 10 * 1024 * 1024; // 10MB
        const oversizedFiles = files.filter(file => file.size > maxSizePerFile);
        if (oversizedFiles.length > 0) {
            alert('각 이미지의 크기는 10MB를 초과할 수 없습니다.');
            isProcessing = false;
            return;
        }

        const processFile = (file, index) => {
            return new Promise((resolve) => {
                if (!file.type.startsWith('image/')) {
                    alert('이미지 파일만 업로드 가능합니다.');
                    resolve();
                    return;
                }

                const reader = new FileReader();
                reader.onload = function(e) {
                    const preview = document.createElement('div');
                    preview.className = 'image-preview';
                    preview.innerHTML = `
                        <img src="${e.target.result}" alt="Preview">
                        <button type="button" class="remove-image">×</button>
                    `;
                    
                    // 현재 이미지 배열에 추가
                    currentImages.push({
                        file: file,
                        element: preview
                    });

                    // 업로드 박스 다음 위치에 삽입
                    uploadBox.insertAdjacentElement('afterend', preview);

                    preview.querySelector('.remove-image').addEventListener('click', function(e) {
                        e.stopPropagation();
                        const index = currentImages.findIndex(img => img.element === preview);
                        if (index > -1) {
                            currentImages.splice(index, 1);
                        }
                        preview.remove();
                    });
                    resolve();
                };
                reader.readAsDataURL(file);
            });
        };

        Promise.all(files.map(processFile)).then(() => {
            isProcessing = false;
            imageInput.value = ''; // 입력 필드 초기화
        });
    });

    document.querySelector('.button-submit').addEventListener('click', async function() {
        try {
            const formData = new FormData();
            const courseId = document.getElementById('courseId').value;
            
            if (!courseId) {
                throw new Error('코스 정보를 찾을 수 없습니다.');
            }

            if (currentImages.length === 0) {
                throw new Error('최소 1장의 이미지를 추가해주세요.');
            }

            formData.append('courseId', courseId);
            currentImages.forEach(img => {
                formData.append('images', img.file);
            });

            const response = await fetch('/api/course/recommend-course-regist', {
                method: 'POST',
                body: formData
            });

            if (!response.ok) {
                if (response.status === 413) {
                    throw new Error('파일 크기가 너무 큽니다. 각 이미지는 10MB 이하여야 합니다.');
                }
                const result = await response.json();
                throw new Error(result.message || result.data || '등록에 실패했습니다.');
            }

            const result = await response.json();
            alert('추천 코스가 등록되었습니다.');
            window.location.href = '/recommend-courses';
        } catch (error) {
            console.error('Error:', error);
            alert(error.message);
        }
    });

    document.querySelector('.button-cancel').addEventListener('click', function() {
        window.history.back();
    });
}); 