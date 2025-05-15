document.addEventListener("DOMContentLoaded", function () {
  const form = document.getElementById("editForm");
  const password = document.getElementById("password");
  const passwordConfirm = document.getElementById("passwordConfirm");
  const phone = document.getElementById("phone");
  const email = document.getElementById("email");
  const nickname = document.getElementById("nickname");

  const errorMsg = document.getElementById("error-message");

  form.addEventListener("submit", async function (e) {
    e.preventDefault();

    if (password.value !== passwordConfirm.value) {
      errorMsg.textContent = "비밀번호가 일치하지 않습니다.";
      errorMsg.style.display = "block";
      return;
    }

    errorMsg.style.display = "none";

    const userId = form.dataset.userid;
    const payload = {
      password: password.value,
      phone: phone.value,
      email: email.value,
      nickname: nickname.value
    };

    try {
      const response = await fetch(`/api/members/edit/${userId}`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify(payload)
      });

      if (response.ok) {
        alert("회원정보가 수정되었습니다.");
        window.location.href = "/mypage/main";
      } else {
        const res = await response.json();
        errorMsg.textContent = res.message || "수정에 실패했습니다.";
        errorMsg.style.display = "block";
      }
    } catch (err) {
      console.error(err);
      errorMsg.textContent = "서버와의 통신에 실패했습니다.";
      errorMsg.style.display = "block";
    }
  });
});