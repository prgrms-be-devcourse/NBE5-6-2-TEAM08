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
        fetch('/api/course/recommend-course-regist', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ courseId })
        })
        .then(res => {
            if (!res.ok) throw new Error('추천 등록 실패');
            return res.json();
        })
        .then(() => {
            alert('추천 코스로 등록되었습니다.');
            location.href = '/recommend-courses';
        })
        .catch(err => {
            console.error(err);
            alert('추천 등록에 실패했습니다.');
        });
    });
});
