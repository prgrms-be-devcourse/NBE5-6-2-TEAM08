function renderCourseDetail(course) {
    const detailSection = document.getElementById("mycourse-detail-section");
    detailSection.querySelector("h2").textContent = course.title;

    const placeContainer = detailSection.querySelector(".place-container");
    placeContainer.innerHTML = "";

    if (course.places && Array.isArray(course.places)) {
        course.places.forEach(place => {
            const placeCard = document.createElement("div");
            placeCard.className = "place-card";
            placeCard.innerHTML = `
                <p class="place-name">${place.title}</p>
                <p class="place-address">${place.address}</p>
            `;
            placeContainer.appendChild(placeCard);
        });
    } else {
        placeContainer.innerHTML = "<p>등록된 장소가 없습니다.</p>";
    }

    const desc = detailSection.querySelector(".course-description p");
    desc.textContent = course.description || "설명이 없습니다.";

    // 추천 코스 등록 페이지 이동
    const recommendButton = detailSection.querySelector("#recommend-button");
    if (recommendButton) {
        recommendButton.addEventListener("click", () => {
            window.location.href = `/recommend-course-regist?courseId=${course.coursesId}`;
        });
    }
}

window.renderCourseDetail = renderCourseDetail;