document.addEventListener('DOMContentLoaded', () => {
    const urlParams = new URLSearchParams(window.location.search);
    const courseId = urlParams.get('courseId');

    if (!courseId) {
        alert('코스 ID가 없습니다.');
        return;
    }

    fetch(`/api/course/my-course/${courseId}`, {
        method: 'GET',
        headers: { 'Accept': 'application/json' },
        credentials: 'include'
    })
    .then(res => res.json())
    .then(course => {
        document.getElementById('detail-course-title').textContent = course.title;
        document.getElementById('course-description').textContent = course.description;

        const placeContainer = document.querySelector('.place-container');
        placeContainer.innerHTML = '';
        course.places.forEach(place => {
            const card = document.createElement('div');
            card.className = 'place-card';
            card.innerHTML = `
          <h3>${place.title}</h3>
          <p>${place.address}</p>
        `;
            placeContainer.appendChild(card);
        });
    })
    .catch(err => {
        console.error(err);
        alert('코스 정보를 불러오는 데 실패했습니다.');
    });

    document.getElementById('recommend-button').addEventListener('click', () => {
        const courseId = new URLSearchParams(window.location.search).get('courseId');
        if (!courseId) {
            alert('코스 ID가 없습니다.');
            return;
        }

        // ✅ PathVariable 형식으로 전달
        window.location.href = `/recommend-course/register/${courseId}`;
    });
});