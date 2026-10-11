document.addEventListener("DOMContentLoaded", () => {

    const decreaseBtn = document.getElementById("decreaseBtn");
    const increaseBtn = document.getElementById("increaseBtn");
    const quantityElement = document.getElementById("quantity");

    // QUAN TRỌNG:
    // ID này phải đúng với id của nút "Thêm vào giỏ hàng" trong HTML
    const addToCartBtn = document.getElementById("addToCartBtn");

    let quantity = 1;
    let product = null;


    // =========================================================
    // LOAD PRODUCT DETAIL
    // =========================================================

    async function loadProductDetail() {

        const params = new URLSearchParams(window.location.search);
        const productId = params.get("id");

        console.log("Product ID:", productId);

        if (!productId) {
            console.error("Không tìm thấy id sản phẩm");
            return;
        }

        try {

            const response = await fetch(
                `http://127.0.0.1:8080/api/products/${productId}`
            );

            if (!response.ok) {
                throw new Error("Không thể lấy thông tin sản phẩm");
            }

            product = await response.json();

            console.log("Product Detail:", product);


            // =================================================
            // HIỂN THỊ THÔNG TIN SẢN PHẨM
            // =================================================

            document.querySelector(".product-name").textContent =
                product.name;

            document.querySelector(".product-price").textContent =
                product.price.toLocaleString("vi-VN") + " vnđ";

            document.querySelector(".product-description").textContent =
                product.description;


            // =================================================
            // PRODUCT INFO
            // =================================================

            const productBrand = document.getElementById("productBrand");
            const productNameInfo = document.getElementById("productNameInfo");
            const productIdElement = document.getElementById("productId");
            const productQuantity = document.getElementById("productQuantity");
            const productDescriptionInfo =
                document.getElementById("productDescriptionInfo");

            if (productBrand) {
                productBrand.textContent = product.brandId;
            }

            if (productNameInfo) {
                productNameInfo.textContent = product.name;
            }

            if (productIdElement) {
                productIdElement.textContent = product.id;
            }

            if (productQuantity) {
                productQuantity.textContent = product.quantity;
            }

            if (productDescriptionInfo) {
                productDescriptionInfo.textContent = product.description;
            }


            // =================================================
            // PRODUCT IMAGE
            // =================================================

            const mainProductImage =
                document.getElementById("mainProductImage");

            if (
                mainProductImage &&
                product.images &&
                product.images.length > 0
            ) {

                mainProductImage.src =
                    `images/products/${product.images[0]}`;

                mainProductImage.alt = product.name;
            }


            // =================================================
            // BREADCRUMB
            // =================================================

            const breadcrumb =
                document.querySelector(".product-breadcrumb");

            if (breadcrumb) {
                breadcrumb.textContent =
                    `Trang chủ / Kim cương nuôi cấy / ${product.name}`;
            }

        } catch (error) {

            console.error("Lỗi khi load product:", error);

        }
    }


    // =========================================================
    // TĂNG SỐ LƯỢNG
    // =========================================================

    if (increaseBtn) {

        increaseBtn.addEventListener("click", () => {

            if (!product) {
                return;
            }

            if (quantity < product.quantity) {

                quantity++;

                quantityElement.textContent = quantity;
            }
        });
    }


    // =========================================================
    // GIẢM SỐ LƯỢNG
    // =========================================================

    if (decreaseBtn) {

        decreaseBtn.addEventListener("click", () => {

            if (quantity > 1) {
                quantity--;
            }

            quantityElement.textContent = quantity;
        });
    }


    // =========================================================
    // THÊM VÀO GIỎ HÀNG
    // =========================================================

    if (addToCartBtn) {

        addToCartBtn.addEventListener("click", async () => {

            if (!product) {
                alert("Sản phẩm chưa được tải xong!");
                return;
            }

            try {

                // =================================================
                // 1. LẤY ACCOUNT ĐANG ĐĂNG NHẬP
                // =================================================

                const userResponse = await fetch(
                    "http://127.0.0.1:8080/api/auth/me",
                    {
                        method: "GET",
                        credentials: "include"
                    }
                );

                if (!userResponse.ok) {

                    alert("Vui lòng đăng nhập trước khi thêm sản phẩm!");

                    window.location.href = "login.html";

                    return;
                }

                const user = await userResponse.json();

                console.log("Current user:", user);

                const accountId = user.id;

                console.log("Current accountId:", accountId);


                // =================================================
                // 2. LẤY TẤT CẢ CART
                // =================================================

                const cartResponse = await fetch(
                    "http://127.0.0.1:8080/api/carts",
                    {
                        method: "GET",
                        credentials: "include"
                    }
                );

                if (!cartResponse.ok) {
                    throw new Error("Không thể lấy danh sách cart");
                }

                const carts = await cartResponse.json();

                console.log("All carts:", carts);


                // =================================================
                // 3. TÌM CART CỦA ACCOUNT ĐANG ĐĂNG NHẬP
                // =================================================

                const myCart = carts.find(
                    cart => cart.accountId === accountId
                );

                console.log("My cart:", myCart);


                // =================================================
                // 4. NẾU CHƯA CÓ CART
                // =================================================

                if (!myCart) {

                    alert(
                        "Tài khoản này chưa có giỏ hàng!"
                    );

                    return;
                }


                // =================================================
                // 5. THÊM PRODUCT VÀO CART
                // =================================================

                const cartItemRequest = {

                    cartId: myCart.id,

                    productId: product.id,

                    quantity: quantity

                };

                console.log(
                    "Adding cart item:",
                    cartItemRequest
                );


                const addResponse = await fetch(
                    "http://127.0.0.1:8080/api/cart-items",
                    {
                        method: "POST",

                        headers: {
                            "Content-Type": "application/json"
                        },

                        credentials: "include",

                        body: JSON.stringify(cartItemRequest)
                    }
                );


                if (!addResponse.ok) {

                    const errorText = await addResponse.text();

                    console.error(
                        "Add cart item error:",
                        errorText
                    );

                    throw new Error(
                        "Không thể thêm sản phẩm vào giỏ hàng"
                    );
                }


                // =================================================
                // 6. THÀNH CÔNG
                // =================================================

                alert(
                    `Đã thêm ${quantity} sản phẩm vào giỏ hàng!`
                );

                console.log(
                    "Added successfully!"
                );


            } catch (error) {

                console.error(
                    "Lỗi thêm vào giỏ hàng:",
                    error
                );

                alert(
                    "Có lỗi xảy ra khi thêm vào giỏ hàng!"
                );
            }

        });

    } else {

        // Đây chính là lỗi bạn đang gặp
        console.error(
            "Không tìm thấy #addToCartBtn trong HTML!"
        );
    }


    // =========================================================
    // START
    // =========================================================

    loadProductDetail();

});