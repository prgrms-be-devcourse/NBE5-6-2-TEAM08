document.addEventListener('DOMContentLoaded', function () {
  const placeList = document.getElementById('placeList');
  const courseList = document.getElementById('courseList');

  // 장소 추가 버튼 이벤트 위임
  placeList.addEventListener('click', function (e) {
    if (e.target.classList.contains('add-btn')) {
      const placeName = e.target.parentElement.querySelector('span').textContent;
      addCourse(placeName);
    }
  });

  // 코스 삭제 버튼 이벤트 위임
  courseList.addEventListener('click', function (e) {
    if (e.target.classList.contains('delete-btn')) {
      e.target.closest('.course-item').remove();
    }
  });

  function addCourse(placeName) {
    const courseItem = document.createElement('div');
    courseItem.className = 'course-item';
    courseItem.innerHTML = `
      <div>
        <div class="course-info">주변 코스</div>
        <div class="course-title">${placeName}</div>
      </div>
      <button class="delete-btn" aria-label="코스 삭제">&times;</button>
    `;
    courseList.appendChild(courseItem);
  }
}); 