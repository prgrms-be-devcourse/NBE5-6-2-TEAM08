document.addEventListener('DOMContentLoaded', function () {
  loadFavoriteCourses();
});

function loadFavoriteCourses() {
  fetch('/api/members/favorites')
  .then(response => {
    if (!response.ok) {
      throw new Error('서버로부터 데이터를 가져오지 못했습니다.');
    }
    return response.json();
  })
  .then(data => {
    renderFavoriteCourses(data);
  })
  .catch(error => {
    console.error('에러 발생:', error);
  });
}

function renderFavoriteCourses(courseList) {
  const container = document.getElementById('favorite-course-list');
  container.innerHTML = '';

  if (courseList.length === 0) {
    container.innerHTML = '<p>찜한 코스가 없습니다.</p>';
    return;
  }

  courseList.forEach(course => {
    const card = document.createElement('div');
    card.className = 'course-card';

    const isRecommend = course.recommendCourseId !== null;
    const courseId = isRecommend ? course.recommendCourseId : course.editorCourseId;
    const url = isRecommend
        ? `/recommend-courses/${courseId}`
        : `/editor-recommand-courses/${courseId}`;

    card.innerHTML = `
      <h3>${course.title}</h3>
      <p>작성자: ${course.userId}</p>
    `;

    card.addEventListener('click', () => {
      // AJAX로 상세정보 받아오기
      window.location.href = url;
    });

    container.appendChild(card);
  });
}
