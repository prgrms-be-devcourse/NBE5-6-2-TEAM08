// 날짜 입력창 클릭 시 date picker
const dateInput = document.getElementById('dateInput');
if (dateInput) {
  dateInput.addEventListener('click', () => {
    const picker = document.createElement('input');
    picker.type = 'date';
    picker.style.position = 'absolute';
    picker.style.opacity = 0;
    document.body.appendChild(picker);
    picker.focus();
    picker.onchange = function() {
      dateInput.value = this.value;
      document.body.removeChild(picker);
    };
    picker.onblur = function() {
      setTimeout(() => {
        if (document.body.contains(picker)) document.body.removeChild(picker);
      }, 200);
    };
  });
}

// 카테고리/주소 버튼 토글
function toggleBtnGroup(selector) {
  document.querySelectorAll(selector).forEach(btn => {
    btn.addEventListener('click', function() {
      this.classList.toggle('selected');
    });
  });
}
toggleBtnGroup('.category-btn');
toggleBtnGroup('.address-btn');

// AI 추천코스 버튼 클릭 시 예시
const aiBtn = document.querySelector('.ai-btn');
if (aiBtn) {
  aiBtn.addEventListener('click', () => {
    alert('AI 추천코스 기능이 준비 중입니다!');
  });
} 