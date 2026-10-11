document.addEventListener("DOMContentLoaded", () => {

    const cartItems = document.getElementById("cartItems");
    const emptyCart = document.getElementById("emptyCart");

    const cartCount = document.getElementById("cartCount");
    const cartProductCount = document.getElementById("cartProductCount");
    const cartTotal = document.getElementById("cartTotal");

    const checkoutBtn = document.getElementById("checkoutBtn");


    // =====================================================
    // CHECKOUT
    // =====================================================

    checkoutBtn.addEventListener("click", () => {

        if (checkoutBtn.disabled) {
            return;
        }

        window.location.href = "checkout.html";
    });


    // =====================================================
    // LOAD CART CỦA TÀI KHOẢN ĐANG ĐĂNG NHẬP
    // =====================================================

    async function loadCart() {

        try {

            // =================================================
            // 1. LẤY TÀI KHOẢN HIỆN TẠI
            // =================================================

            const userResponse = await fetch(
                "http://127.0.0.1:8080/api/auth/me",
                {
                    method: "GET",
                    credentials: "include"
                }
            );


            // Chưa đăng nhập
            if (!userResponse.ok) {

                console.log("Chưa đăng nhập.");

                window.location.href = "login.html";

                return;
            }


            const user = await userResponse.json();

            console.log("Current user:", user);


            const accountId = user.id;

            console.log("Current accountId:", accountId);


            // =================================================
            // 2. LẤY DANH SÁCH CART
            // =================================================

            const cartsResponse = await fetch(
                "http://127.0.0.1:8080/api/carts",
                {
                    method: "GET",
                    credentials: "include"
                }
            );


            if (!cartsResponse.ok) {

                throw new Error(
                    "Không thể tải danh sách cart"
                );
            }


            const carts = await cartsResponse.json();

            console.log("All carts:", carts);


            // =================================================
            // 3. TÌM CART CỦA ACCOUNT HIỆN TẠI
            // =================================================

            const myCart = carts.find(
                cart => Number(cart.accountId) === Number(accountId)
            );


            console.log("My cart:", myCart);


            // =================================================
            // 4. ACCOUNT CHƯA CÓ CART
            // =================================================

            if (!myCart) {

                console.log(
                    "Tài khoản chưa có giỏ hàng."
                );

                cartItems.innerHTML = "";

                emptyCart.style.display = "block";

                checkoutBtn.disabled = true;

                cartCount.textContent = "0";

                cartProductCount.textContent = "0";

                cartTotal.textContent = "0₫";

                return;
            }


            // =================================================
            // 5. LẤY TOÀN BỘ CART ITEM
            // =================================================

            const cartItemResponse = await fetch(
                "http://127.0.0.1:8080/api/cart-items",
                {
                    method: "GET",
                    credentials: "include"
                }
            );


            if (!cartItemResponse.ok) {

                throw new Error(
                    "Không thể tải cart item"
                );
            }


            const allCartItems =
                await cartItemResponse.json();


            console.log(
                "All cart items:",
                allCartItems
            );


            // =================================================
            // 6. CHỈ LẤY CART ITEM CỦA CART HIỆN TẠI
            // =================================================

            const cartItemList = allCartItems.filter(
                item =>
                    Number(item.cartId) === Number(myCart.id)
            );


            console.log(
                "My cart items:",
                cartItemList
            );


            // =================================================
            // XÓA HTML CŨ
            // =================================================

            cartItems.innerHTML = "";


            // =================================================
            // 7. GIỎ HÀNG TRỐNG
            // =================================================

            if (cartItemList.length === 0) {

                emptyCart.style.display = "block";

                checkoutBtn.disabled = true;

                cartCount.textContent = "0";

                cartProductCount.textContent = "0";

                cartTotal.textContent = "0₫";

                return;
            }


            // =================================================
            // 8. CÓ SẢN PHẨM
            // =================================================

            emptyCart.style.display = "none";

            checkoutBtn.disabled = false;


            let total = 0;


            // =================================================
            // 9. RENDER CART ITEM
            // =================================================

            for (const cartItem of cartItemList) {

                const productResponse = await fetch(
                    `http://127.0.0.1:8080/api/products/${cartItem.productId}`,
                    {
                        method: "GET",
                        credentials: "include"
                    }
                );


                if (!productResponse.ok) {

                    throw new Error(
                        `Không tìm thấy sản phẩm ${cartItem.productId}`
                    );
                }


                const product =
                    await productResponse.json();


                console.log(
                    "Product:",
                    product
                );


                // =================================================
                // TÍNH TIỀN
                // =================================================

                total +=
                    product.price *
                    cartItem.quantity;


                // =================================================
                // HTML CART ITEM
                // =================================================

                const itemHTML = `

                    <div class="cart-item">

                        <img
                            src="images/products/${product.images[0]}"
                            class="cart-item-image"
                            alt="${product.name}"
                        >


                        <div class="cart-item-info">

                            <div class="cart-item-name">
                                ${product.name}
                            </div>

                            <div class="cart-item-price">
                                ${product.price.toLocaleString("vi-VN")}₫
                            </div>

                        </div>


                        <!-- QUANTITY -->

                        <div class="quantity-control">

                            <button
                                type="button"
                                class="decrease-btn"
                                data-id="${cartItem.id}"
                            >
                                -
                            </button>


                            <span>
                                ${cartItem.quantity}
                            </span>


                            <button
                                type="button"
                                class="increase-btn"
                                data-id="${cartItem.id}"
                            >
                                +
                            </button>

                        </div>


                        <!-- REMOVE -->

                        <button
                            type="button"
                            class="remove-item"
                            data-id="${cartItem.id}"
                        >
                            <i class="bi bi-trash"></i>
                        </button>

                    </div>

                `;


                cartItems.insertAdjacentHTML(
                    "beforeend",
                    itemHTML
                );
            }


            // =================================================
            // 10. NÚT +
            // =================================================

            document
                .querySelectorAll(".increase-btn")
                .forEach(button => {

                    button.addEventListener(
                        "click",
                        async () => {

                            const cartItemId =
                                button.dataset.id;


                            const cartItem =
                                cartItemList.find(
                                    item =>
                                        item.id == cartItemId
                                );


                            if (!cartItem) {
                                return;
                            }


                            const newQuantity =
                                cartItem.quantity + 1;


                            await updateCartItem(
                                cartItem,
                                newQuantity
                            );

                        }
                    );

                });


            // =================================================
            // 11. NÚT -
            // =================================================

            document
                .querySelectorAll(".decrease-btn")
                .forEach(button => {

                    button.addEventListener(
                        "click",
                        async () => {

                            const cartItemId =
                                button.dataset.id;


                            const cartItem =
                                cartItemList.find(
                                    item =>
                                        item.id == cartItemId
                                );


                            if (!cartItem) {
                                return;
                            }


                            // Không cho nhỏ hơn 1

                            if (cartItem.quantity <= 1) {
                                return;
                            }


                            const newQuantity =
                                cartItem.quantity - 1;


                            await updateCartItem(
                                cartItem,
                                newQuantity
                            );

                        }
                    );

                });


            // =================================================
            // 12. NÚT XÓA
            // =================================================

            document
                .querySelectorAll(".remove-item")
                .forEach(button => {

                    button.addEventListener(
                        "click",
                        async () => {

                            const cartItemId =
                                button.dataset.id;


                            console.log(
                                "Xóa cartItem:",
                                cartItemId
                            );


                            try {

                                const response =
                                    await fetch(
                                        `http://127.0.0.1:8080/api/cart-items/${cartItemId}`,
                                        {
                                            method: "DELETE",
                                            credentials: "include"
                                        }
                                    );


                                if (!response.ok) {

                                    throw new Error(
                                        "Không thể xóa sản phẩm"
                                    );
                                }


                                // Load lại giỏ hàng

                                await loadCart();

                            }
                            catch (error) {

                                console.error(
                                    "Lỗi khi xóa sản phẩm:",
                                    error
                                );

                            }

                        }
                    );

                });


            // =================================================
            // 13. SỐ DÒNG SẢN PHẨM
            // =================================================

            cartCount.textContent =
                cartItemList.length;


            // =================================================
            // 14. TỔNG SỐ LƯỢNG
            // =================================================

            const totalQuantity =
                cartItemList.reduce(
                    (sum, item) =>
                        sum + item.quantity,
                    0
                );


            cartProductCount.textContent =
                totalQuantity;


            // =================================================
            // 15. TỔNG TIỀN
            // =================================================

            cartTotal.textContent =
                total.toLocaleString("vi-VN") + "₫";


        }
        catch (error) {

            console.error(
                "Lỗi khi tải giỏ hàng:",
                error
            );

        }

    }


    // =====================================================
    // UPDATE CART ITEM
    // =====================================================

    async function updateCartItem(
        cartItem,
        newQuantity
    ) {

        try {

            const response = await fetch(
                `http://127.0.0.1:8080/api/cart-items/${cartItem.id}`,
                {
                    method: "PUT",

                    headers: {
                        "Content-Type": "application/json"
                    },

                    credentials: "include",

                    body: JSON.stringify({

                        quantity: newQuantity,

                        productId:
                            cartItem.productId,

                        cartId:
                            cartItem.cartId

                    })
                }
            );


            if (!response.ok) {

                throw new Error(
                    "Không thể cập nhật số lượng"
                );

            }


            const updatedCartItem =
                await response.json();


            console.log(
                "Cart item sau khi update:",
                updatedCartItem
            );


            // Load lại giao diện

            await loadCart();

        }
        catch (error) {

            console.error(
                "Lỗi khi cập nhật số lượng:",
                error
            );

        }

    }


    // =====================================================
    // CHẠY LẦN ĐẦU
    // =====================================================

    loadCart();

});