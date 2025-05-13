// 에디터 픽 데이트 코스 카드 데이터
const editorPickCards = [
  {
    title: "한강 피크닉 코스 ❤️",
    author: "홍길동",
    image: "https://via.placeholder.com/260x160?text=대표사진1",
    avatar: "https://via.placeholder.com/32?text=프로필"
  },
  {
    title: "한강 피크닉 코스 🤍",
    author: "홍길동",
    image: "https://via.placeholder.com/260x160?text=대표사진2",
    avatar: "https://via.placeholder.com/32?text=프로필"
  }
];

// 추천 데이트 코스 카드 데이터
const recommendCards = [
  {
    title: "남산 야경 데이트",
    author: "홍길동",
    image: "https://via.placeholder.com/260x160?text=대표사진3",
    avatar: "https://via.placeholder.com/32?text=프로필"
  },
  {
    title: "한강 피크닉 코스",
    author: "홍길동",
    image: "https://via.placeholder.com/260x160?text=대표사진4",
    avatar: "https://via.placeholder.com/32?text=프로필"
  }
];

// 카드 리스트 렌더링 함수
function renderCards(cardData, containerId) {
  const cardList = document.getElementById(containerId);
  cardList.innerHTML = '';

  cardData.forEach(card => {
    const cardDiv = document.createElement('div');
    cardDiv.className = 'course-card';

    cardDiv.innerHTML = `
      <div class="card-image" style="background-image:url('${card.image}');"></div>
      <div class="card-title">${card.title}</div>
      <div class="card-author">
        <div class="author-avatar" style="background-image:url('${card.avatar}');"></div>
        <div class="author-name">${card.author}</div>
      </div>
    `;
    cardList.appendChild(cardDiv);
  });
}

// DOM 준비 시 각 섹션 렌더링
document.addEventListener('DOMContentLoaded', function () {
  renderCards(editorPickCards, 'editor-pick-list');
  renderCards(recommendCards, 'recommend-list');
});

// <div id="admin-courses"></div>
// <div id="user-courses"></div>
//
// <script>
//   fetch('/api/courses/top-list') // 서버에서 JSON 리턴하는 API 경로
//   .then(response => response.json())
//   .then(data => {
//   const adminCourses = data.adminlist;
//   const userCourses = data.userlist;
//
//   const adminDiv = document.getElementById('admin-courses');
//   const userDiv = document.getElementById('user-courses');
//
//   adminCourses.forEach(course => {
//   adminDiv.innerHTML += `<p>📘 ${course.title} - ${course.editor}</p>`;
// });
//
//   userCourses.forEach(course => {
//   userDiv.innerHTML += `<p>📗 ${course.title} - ${course.author}</p>`;
// });
// });
// </script>
