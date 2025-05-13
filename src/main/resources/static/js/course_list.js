const courses = [
  {
    id: 1,
    title: "프론트엔드 마스터 클래스",
    instructor: "홍길동",
    rating: 4.8,
    desc: "HTML, CSS, JS, React까지 한 번에 배우는 실전 코스",
    thumbnail: "thumbnail1.jpg",
    link: "course-detail.html"
  },
  {
    id: 2,
    title: "백엔드 완전정복",
    instructor: "이몽룡",
    rating: 4.7,
    desc: "Node.js, Express, DB까지 백엔드의 모든 것",
    thumbnail: "thumbnail2.jpg",
    link: "course-detail.html"
  },
  {
    id: 3,
    title: "UI/UX 디자인 입문",
    instructor: "성춘향",
    rating: 4.9,
    desc: "Figma로 배우는 실전 UI/UX 디자인",
    thumbnail: "thumbnail3.jpg",
    link: "course-detail.html"
  }
];

const courseList = document.getElementById('courseList');

courses.forEach(course => {
  const card = document.createElement('div');
  card.className = 'course-card';
  card.onclick = () => {
    window.location.href = course.link + '?id=' + course.id;
  };

  card.innerHTML = `
    <img src="${course.thumbnail}" alt="${course.title} 썸네일" />
    <div class="course-card-content">
      <div class="course-title">${course.title}</div>
      <div class="course-instructor">강사: ${course.instructor}</div>
      <div class="course-rating">⭐ ${course.rating}</div>
      <div class="course-desc">${course.desc}</div>
    </div>
  `;
  courseList.appendChild(card);
});