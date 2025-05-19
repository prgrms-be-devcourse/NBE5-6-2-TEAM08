document.addEventListener('DOMContentLoaded', function() {
    const imageInput = document.getElementById('courseImages');
    const previewContainer = document.getElementById('imagePreviewContainer');
    const uploadBox = document.getElementById('uploadBox');
    const submitButton = document.querySelector('.button-submit');
    const cancelButton = document.querySelector('.button-cancel');
    const maxImages = 10;
    let currentImages = [];
    let uploadedImageUrls = [];
    let isProcessing = false;
    const courseId = document.getElementById('courseId').value;

    // 코스 상세 정보 불러오기
    fetch(`/api/course/mycourse/${courseId}`, {
        method: 'GET',
        headers: {
            'Accept': 'application/json'
        },
        credentials: 'include'
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('코스 정보를 불러오는데 실패했습니다.');
        }
        return response.json();
    })
    .then(course => {
        console.log('Loaded course details:', course);
        
        // 장소 목록 표시
        const placeContainer = document.querySelector('.place-grid');
        if (placeContainer && course.places && course.places.length > 0) {
            placeContainer.innerHTML = ''; // 기존 내용 초기화
            course.places.forEach(place => {
                const placeCard = document.createElement('div');
                placeCard.className = 'place-card';
                placeCard.innerHTML = `
                    <div class="place-image-container">
                        <input type="file" class="place-image-input" accept="image/*" hidden>
                        <div class="place-image-placeholder">
                            <svg width="24" height="24" viewBox="0 0 24 24" fill="none">
                                <path d="M12 5V19M5 12H19" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                            </svg>
                            <span>${place.title}</span>
                        </div>
                        <p class="place-address">${place.address}</p>
                    </div>
                `;
                placeContainer.appendChild(placeCard);
            });
        } else {
            console.error('Place container not found or no places available');
        }

        // 코스 제목 업데이트
        const sectionSubtitle = document.querySelector('.section-subtitle');
        if (sectionSubtitle && course.title) {
            sectionSubtitle.textContent = course.title;
        }
    })
    .catch(error => {
        console.error('코스 정보 로딩 실패:', error);
        alert('코스 정보를 불러오는데 실패했습니다. 페이지를 새로고침 해주세요.');
    });

    // 업로드 박스 전체 영역 클릭 이벤트
    uploadBox.addEventListener('click', function(e) {
        if (!isProcessing) {
            imageInput.click();
        }
    });

    imageInput.addEventListener('click', function(e) {
        e.stopPropagation();
    });

    imageInput.addEventListener('change', async function(e) {
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

        try {
            // 이미지 업로드
            const formData = new FormData();
            files.forEach(file => {
                console.log('파일 추가:', file.name, file.type, file.size);
                formData.append('images', file);
            });

            console.log('업로드 시작');
            const response = await fetch('/api/course/images', {
                method: 'POST',
                body: formData,
                // Content-Type 헤더를 명시적으로 설정하지 않음 (브라우저가 자동으로 설정)
                headers: {
                    'Accept': 'application/json'
                }
            });

            if (!response.ok) {
                console.error('업로드 실패:', response.status, response.statusText);
                const text = await response.text();
                console.error('에러 응답:', text);
                throw new Error('이미지 업로드에 실패했습니다.');
            }

            const urls = await response.json();
            console.log('업로드 성공:', urls);
            uploadedImageUrls = uploadedImageUrls.concat(urls);

            // 미리보기 표시
            files.forEach(file => {
                const reader = new FileReader();
                reader.onload = function(e) {
                    const previewDiv = document.createElement('div');
                    previewDiv.className = 'image-preview';
                    
                    const img = document.createElement('img');
                    img.src = e.target.result;
                    img.className = 'preview-image';
                    
                    const deleteBtn = document.createElement('button');
                    deleteBtn.innerHTML = '×';
                    deleteBtn.className = 'remove-image';
                    deleteBtn.onclick = function() {
                        previewDiv.remove();
                        const index = currentImages.indexOf(file);
                        if (index > -1) {
                            currentImages.splice(index, 1);
                            uploadedImageUrls.splice(index, 1);
                        }
                    };
                    
                    previewDiv.appendChild(img);
                    previewDiv.appendChild(deleteBtn);
                    previewContainer.appendChild(previewDiv);
                    currentImages.push(file);
                };
                reader.readAsDataURL(file);
            });
        } catch (error) {
            console.error('Error:', error);
            alert(error.message);
        } finally {
            isProcessing = false;
            imageInput.value = ''; // 입력 필드 초기화
        }
    });

    // 코스 등록 버튼 클릭 이벤트
    submitButton.addEventListener('click', async function() {
        if (isProcessing) return;
        isProcessing = true;

        try {
            if (uploadedImageUrls.length === 0) {
                alert('최소 1장의 이미지를 업로드해주세요.');
                isProcessing = false;
                return;
            }

            const response = await fetch('/api/course/recommend-course-regist', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    imageUrls: uploadedImageUrls,
                    courseId: courseId
                })
            });

            const result = await response.json();
            
            if (response.ok) {
                alert('코스가 성공적으로 등록되었습니다.');
                window.location.href = '/recommend-courses'; // 추천 코스 목록 페이지로 이동
            } else {
                throw new Error(result.message || '코스 등록에 실패했습니다.');
            }
        } catch (error) {
            console.error('Error:', error);
            alert(error.message);
        } finally {
            isProcessing = false;
        }
    });

    // 취소 버튼 클릭 이벤트
    cancelButton.addEventListener('click', function() {
        window.history.back();
    });
}); 