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

  // 3) 주소(단일) 선택 로직
  let selectedRegion = null;
  document.querySelectorAll('.address-btn').forEach(btn => {
    btn.addEventListener('click', () => {
      // 이전 선택 해제
      document.querySelectorAll('.address-btn.selected')
      .forEach(b => b.classList.remove('selected'));
      // 새로 선택
      selectedRegion = btn.dataset.dong;
      btn.classList.add('selected');
    });
  });

  const aiBtn = document.querySelector('.ai-btn');
  const resultsContainer = document.getElementById('results');
  aiBtn?.addEventListener('click', () => {
    if (selectedCats.size === 0) return alert('카테고리를 선택해주세요!');
    if (!selectedRegion)         return alert('지역을 선택해주세요!');

    const payload = {
      categories: [...selectedCats],
      dong: selectedRegion
    };

    fetch('/llm/recommend/course', {   // (3) 여 경로 꼭 확인
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload)
    })
    .then(res => res.ok ? res.json() : Promise.reject(res))
    .then(places => {                    // (4) 래퍼 없이 배열 반환 가정
      resultsContainer.innerHTML = '';
      places.forEach(place => {
        const card = document.createElement('div');
        card.className = 'place-item';

        card.innerHTML = `
    <h4 class="cat">${place.categories}</h4>
    <h3>${place.name}</h3>
    <p class="address">${place.address}</p>
    <p class="desc">${place.description}</p>
  `;

        resultsContainer.appendChild(card);
      });
    })
    .catch(err => {
      console.error(err);
      alert('오류 발생: ' + err.message);
    });
  });
});
