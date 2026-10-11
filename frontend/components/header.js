
document.addEventListener("headerLoaded", async () => {

    const accountButton = document.getElementById("accountButton");
    const accountText = document.getElementById("accountText");
    const accountDropdown = document.getElementById("accountDropdown");

    const loginLink = document.getElementById("loginLink");
    const orderLink = document.getElementById("orderLink");
    const adminLink = document.getElementById("adminLink");
    const logoutLink = document.getElementById("logoutLink");

    // Kiểm tra các element cần thiết
    if (!accountButton || !accountDropdown) {
        return;
    }

    try {

        // =========================
        // KIỂM TRA USER HIỆN TẠI
        // =========================

        const response = await fetch(
            "http://127.0.0.1:8080/api/auth/me",
            {
                method: "GET",
                credentials: "include"
            }
        );

        // =========================
        // CHƯA ĐĂNG NHẬP
        // =========================

        if (!response.ok) {

            accountButton.addEventListener("click", () => {
                window.location.href = "login.html";
            });

            return;
        }

        // =========================
        // ĐÃ ĐĂNG NHẬP
        // =========================

        const user = await response.json();

        console.log("Current user:", user);
        console.log("USER ROLE =", user.role);

        // Hiển thị email
        accountText.textContent = user.email;

        // Ẩn đăng nhập
        if (loginLink) {
            loginLink.style.display = "none";
        }

        // Hiện đơn hàng
        if (orderLink) {
            orderLink.style.display = "block";
        }

        // Hiện đăng xuất
        if (logoutLink) {
            logoutLink.style.display = "block";
        }

        // =========================
        // ADMIN
        // =========================

        if (user.role === "ROLE_ADMIN") {

            console.log("ADMIN detected!");

            if (adminLink) {
                adminLink.style.display = "block";
            }
        }

        // =========================
        // ACCOUNT BUTTON
        // =========================

        accountButton.addEventListener("click", (event) => {

            event.preventDefault();

            accountDropdown.classList.toggle("show");

        });

        // =========================
        // LOGOUT
        // =========================

        if (logoutLink) {

            logoutLink.addEventListener("click", async (event) => {

                event.preventDefault();

                try {

                    const logoutResponse = await fetch(
                        "http://127.0.0.1:8080/api/auth/logout",
                        {
                            method: "POST",
                            credentials: "include"
                        }
                    );

                    if (logoutResponse.ok) {

                        console.log("Logout successful");

                        // Xóa accountId nếu bạn có lưu
                        localStorage.removeItem("accountId");

                        // Về trang chủ
                        window.location.href = "index.html";

                    } else {

                        console.error(
                            "Logout failed:",
                            logoutResponse.status
                        );

                    }

                } catch (error) {

                    console.error("Logout error:", error);

                }

            });
        }

    } catch (error) {

        console.error("Cannot get current user:", error);

        // Nếu backend không kết nối được
        accountButton.addEventListener("click", () => {

            window.location.href = "login.html";

        });

    }

});

