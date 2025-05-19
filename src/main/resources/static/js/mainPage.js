function renderCards(cardData, containerId, type) {
  const cardList = document.getElementById(containerId);
  cardList.innerHTML = '';

  cardData.forEach(card => {
    const title = card.title;
    const author = type === 'admin' ? card.editorNickname : card.creatorNickname;
    const imageUrl = card.imageurl || 'https://via.placeholder.com/260x160?text=No+Image';
    const courseId = card.courseId;
    const favoriteCount = card.favoriteCnt || 0;
    const commentCount = card.reviewCnt || 0;

    const cardDiv = document.createElement('div');
    cardDiv.className = 'course-card';
    cardDiv.style.cursor = 'pointer';


    cardDiv.addEventListener('click', () => {
      if (type === 'admin') {
        window.location.href = `/editor-recommand-courses/${courseId}`;
      } else {
        window.location.href = `/recommend-courses/${courseId}`;
      }
    });
    let extraInfo = `<div class="card-stats">❤️ ${favoriteCount}</div>`;

    if (type === 'user') {
      extraInfo += `<div class="card-stats">💬 ${commentCount}</div>`;
    }
    cardDiv.innerHTML = `
      <div class="card-image" style="background-image:url('${imageUrl}');"></div>
      <div class="card-title">${title}</div>
      <div class="card-author">
        <div class="author-avatar" style="background-image:url('/images/user.jpg');"></div>
        <div class="author-name">${author}</div>
      </div>
      <div class="card-footer">
        ${extraInfo}
      </div>
    `;
    cardList.appendChild(cardDiv);
  });
}

document.addEventListener('DOMContentLoaded', function () {
  fetch('/api')
  .then(response => response.json())
  .then(data => {
    const adminCourses = data.adminlist;
    const userCourses = data.userlist;

    renderCards(adminCourses, 'editor-pick-list', 'admin');
    renderCards(userCourses, 'recommend-list', 'user');
  })
  .catch(error => {
    console.error('데이터 불러오기 실패:', error);
  });
  document.getElementById('editor-more').addEventListener('click', function (e) {
    e.preventDefault();
    window.location.href = '/editor-recommand-courses';
  });

  document.getElementById('user-more').addEventListener('click', function (e) {
    e.preventDefault();
    window.location.href = '/recommend-courses';
  });
});
