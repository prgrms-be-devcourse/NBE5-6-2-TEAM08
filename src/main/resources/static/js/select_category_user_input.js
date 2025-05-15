document.addEventListener('DOMContentLoaded', () => {
  // 1) 날짜 입력
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

  // 2) 카테고리(최대 3개) 선택 로직
  const MAX_CAT = 3;
  const selectedCats = new Set();
  document.querySelectorAll('.category-btn').forEach(btn => {
    btn.addEventListener('click', () => {
      const code = btn.dataset.code;
      if (selectedCats.has(code)) {
        // 이미 선택된 항목은 토글 해제
        selectedCats.delete(code);
        btn.classList.remove('selected');
      } else {
        // 최대 개수 체크
        if (selectedCats.size >= MAX_CAT) {
          alert(`카테고리는 최대 ${MAX_CAT}개까지 선택할 수 있어요.`);
          return;
        }
        selectedCats.add(code);
        btn.classList.add('selected');
      }
    });
  });

  document.querySelector('.ai-btn')?.addEventListener('click', () => {
    const date = document.getElementById('dateInput')?.value;
    if (!date) return alert('날짜를 선택해주세요.');
    if (selectedCats.size === 0) return alert('카테고리를 선택해주세요!');

    const moods = [...selectedCats];
    const moodDescriptions = {
      ROMANTIC: "로맨틱한",
      COZY: "아늑한",
      TRENDY: "트렌디한",
      COMFORTABLE: "편안한",
      QUIET: "조용한",
      LIVELY: "활기찬",
      LUXURIOUS: "고급스러운",
      UNIQUE: "독특한"
    };

    const moodText = moods.map(code => moodDescriptions[code]).join("하고 ");

    // 날짜를 세션에 저장
    sessionStorage.setItem("selectedDate", date);

    // 👉 코스 편집 페이지로 이동하면서 분위기 전달
    window.location.href = `/course-composition?mood=${encodeURIComponent(moodText)}`;
  });
});