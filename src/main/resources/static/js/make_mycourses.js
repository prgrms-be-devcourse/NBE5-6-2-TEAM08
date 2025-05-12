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
      if (fileInput.files && fileInput.files[0]) {
        photoBtn.textContent = '사진이 등록되었습니다!';
      }
      setTimeout(() => document.body.removeChild(fileInput), 500);
    };
  });
}

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