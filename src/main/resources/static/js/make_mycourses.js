// 사진 등록 버튼 클릭 시 파일 선택창
const photoBtn = document.querySelector('.photo-btn');
if (photoBtn) {
  photoBtn.addEventListener('click', () => {
    const fileInput = document.createElement('input');
    fileInput.type = 'file';
    fileInput.accept = 'image/*';
    fileInput.style.display = 'none';
    document.body.appendChild(fileInput);
    fileInput.click();
    fileInput.onchange = function() {
      setTimeout(() => document.body.removeChild(fileInput), 500);
    };
  });
}

document.addEventListener('DOMContentLoaded', function() {
    const photoInput = document.getElementById('photoInput');
    const fileList = document.getElementById('fileList');
    const photoBtn = document.querySelector('.photo-btn');
    let fileCounter = 0;

    // 선택된 장소 정보 불러오기
    const selectedPlaces = JSON.parse(sessionStorage.getItem('selectedPlaces') || '[]');
    const mycourseList = document.querySelector('.mycourse-list');
    
    // 기존 카드 제거
    mycourseList.innerHTML = '';
    
    // 선택된 장소들로 카드 생성
    selectedPlaces.forEach((place) => {
        const card = document.createElement('div');
        card.className = 'mycourse-card';
        card.innerHTML = `
            <div class="place-rank">${place.rank}st Place</div>
            <div class="place-img"></div>
            <div class="place-info">
                <img class="place-logo" src="https://cdn-icons-png.flaticon.com/512/5968/5968756.png" alt="logo">
                <span class="place-name">${place.name}</span>
            </div>
        `;
        mycourseList.appendChild(card);
    });

    // 사진 추가 버튼 클릭 시 파일 선택창 열기
    photoBtn.addEventListener('click', function() {
        photoInput.click();
    });

    photoInput.addEventListener('change', function(e) {
        if (this.files && this.files[0]) {
            const file = this.files[0];
            const fileId = `file-${fileCounter++}`;
            
            // 파일 항목 생성
            const fileItem = document.createElement('div');
            fileItem.className = 'file-item';
            fileItem.id = fileId;
            
            // 파일 이름
            const fileName = document.createElement('span');
            fileName.className = 'file-name';
            fileName.textContent = file.name;
            
            // 삭제 버튼
            const deleteBtn = document.createElement('button');
            deleteBtn.className = 'delete-btn';
            deleteBtn.innerHTML = '<i class="fas fa-times"></i>';
            
            // 삭제 버튼 클릭 이벤트
            deleteBtn.addEventListener('click', function() {
                document.getElementById(fileId).remove();
            });
            
            // 요소들을 조합
            fileItem.appendChild(fileName);
            fileItem.appendChild(deleteBtn);
            fileList.appendChild(fileItem);
            
            alert('사진이 추가되었습니다.');
            
            // 입력 필드 초기화 (다음 파일 선택을 위해)
            this.value = '';
        }
    });
});

// 카테고리/지역 버튼 토글
function toggleBtnGroup(selector) {
  document.querySelectorAll(selector).forEach(btn => {
    btn.addEventListener('click', function() {
      this.classList.toggle('selected');
    });
  });
}
toggleBtnGroup('.category-btn');
toggleBtnGroup('.region-btn');

// 저장 버튼 클릭 시 예시
const saveBtn = document.querySelector('.save-btn');
if (saveBtn) {
  saveBtn.addEventListener('click', function(e) {
    e.preventDefault();
    alert('내 데이트 코스가 저장되었습니다!');
  });
} 