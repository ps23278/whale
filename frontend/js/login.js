const loginForm = document.getElementById("loginForm");
const loginMessage = document.getElementById("loginMessage");

loginForm.addEventListener("submit", async function (event) {

    // Ngăn form reload trang
    event.preventDefault();

    // Lấy dữ liệu từ form
    const email = document.getElementById("email").value.trim();
    const password = document.getElementById("password").value;

    // Xóa message cũ
    loginMessage.textContent = "";

    // Kiểm tra dữ liệu
    if (!email || !password) {
        loginMessage.textContent = "Vui lòng nhập đầy đủ email và mật khẩu.";
        return;
    }

    try {

        const response = await fetch(
    "http://127.0.0.1:8080/api/auth/login", {
            method: "POST",

            headers: {
                "Content-Type": "application/json"
            },

            credentials: "include",

            body: JSON.stringify({
                email: email,
                password: password
            })
        });

        // Login thành công
        if (response.ok) {

            const message = await response.text();

            console.log("Login:", message);

            // Chuyển về trang chủ
            window.location.href = "index.html";

            return;
        }

        // Login thất bại
        loginMessage.textContent =
            "Email hoặc mật khẩu không chính xác.";

    } catch (error) {

        console.error("Login error:", error);

        loginMessage.textContent =
            "Không thể kết nối đến máy chủ.";
    }
});