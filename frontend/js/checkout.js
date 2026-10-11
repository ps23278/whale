// =========================
// CHECKOUT.JS
// =========================

// Không hard-code accountId nữa
let accountId = null;

const addressInput = document.getElementById("address");
const checkoutItems = document.getElementById("checkoutItems");
const subtotalElement = document.getElementById("subtotal");
const shippingFeeElement = document.getElementById("shippingFee");
const totalAmountElement = document.getElementById("totalAmount");
const btnPlaceOrder = document.getElementById("btnPlaceOrder");

let cartId = null;
let cartItems = [];
let subtotal = 0;


// =========================
// FORMAT TIỀN
// =========================

function formatPrice(price) {
    return new Intl.NumberFormat("vi-VN").format(price) + " ₫";
}


// =========================
// LẤY ACCOUNT ĐANG ĐĂNG NHẬP
// =========================

async function getCurrentAccount() {

    const response = await fetch(
        "http://127.0.0.1:8080/api/auth/me",
        {
            method: "GET",
            credentials: "include"
        }
    );

    if (!response.ok) {
        throw new Error("Không lấy được tài khoản đang đăng nhập.");
    }

    const account = await response.json();

    console.log("Account hiện tại:", account);

    accountId = account.id;

    console.log("Account ID hiện tại:", accountId);
}


// =========================
// LOAD CHECKOUT
// =========================

async function loadCheckout() {

    try {

        // 1. Lấy account đang đăng nhập
        await getCurrentAccount();

        // 2. Lấy cart của account
        const cartResponse = await fetch(
            "http://127.0.0.1:8080/api/carts",
            {
                credentials: "include"
            }
        );

        if (!cartResponse.ok) {
            throw new Error("Không thể lấy danh sách cart.");
        }

        const carts = await cartResponse.json();

        console.log("Tất cả Cart:", carts);
        console.log("Account ID đang tìm:", accountId);

        const cart = carts.find(
            item => Number(item.accountId) === Number(accountId)
        );

        console.log("Cart của account hiện tại:", cart);

        if (!cart) {

            checkoutItems.innerHTML =
                "<p>Không tìm thấy giỏ hàng.</p>";

            return;
        }

        cartId = cart.id;

        console.log("Cart ID:", cartId);


        // =========================
        // 3. LẤY CART ITEM
        // =========================

        const cartItemResponse = await fetch(
            "http://127.0.0.1:8080/api/cart-items",
            {
                credentials: "include"
            }
        );

        if (!cartItemResponse.ok) {
            throw new Error("Không thể lấy CartItem.");
        }

        const allCartItems = await cartItemResponse.json();

        console.log("All Cart Items:", allCartItems);

        console.log(
            "Thông tin CartItem:",
            allCartItems.map(item => ({
                id: item.id,
                cartId: item.cartId,
                productId: item.productId,
                quantity: item.quantity
            }))
        );


        // =========================
        // 4. LỌC CART ITEM
        // =========================

        cartItems = allCartItems.filter(
            item => Number(item.cartId) === Number(cartId)
        );

        console.log(
            "Cart Items của cart này:",
            cartItems
        );


        if (cartItems.length === 0) {

            checkoutItems.innerHTML =
                "<p>Giỏ hàng đang trống.</p>";

            return;
        }


        // =========================
        // 5. HIỂN THỊ SẢN PHẨM
        // =========================

        await renderCheckoutItems();

    } catch (error) {

        console.error("Lỗi checkout:", error);

        checkoutItems.innerHTML =
            "<p>Không thể tải thông tin giỏ hàng.</p>";
    }
}


// =========================
// HIỂN THỊ SẢN PHẨM
// =========================

async function renderCheckoutItems() {

    checkoutItems.innerHTML = "";

    subtotal = 0;

    for (const cartItem of cartItems) {

        const response = await fetch(
            `http://127.0.0.1:8080/api/products/${cartItem.productId}`,
            {
                credentials: "include"
            }
        );

        if (!response.ok) {
            throw new Error(
                `Không lấy được Product ${cartItem.productId}`
            );
        }

        const product = await response.json();

        const itemTotal =
            product.price * cartItem.quantity;

        subtotal += itemTotal;


        const image =
            product.images && product.images.length > 0
                ? `images/products/${product.images[0]}`
                : "images/no-image.jpg";


        const itemHTML = `
            <div class="checkout-item">

                <img
                    src="${image}"
                    class="checkout-item-image"
                    alt="${product.name}"
                >

                <div class="checkout-item-info">

                    <div class="checkout-item-name">
                        ${product.name}
                    </div>

                    <div class="checkout-item-price">
                        ${formatPrice(product.price)}
                    </div>

                </div>

                <div class="checkout-item-quantity">
                    x${cartItem.quantity}
                </div>

                <div class="checkout-item-total">
                    ${formatPrice(itemTotal)}
                </div>

            </div>
        `;

        checkoutItems.innerHTML += itemHTML;
    }


    // =========================
    // TÍNH TIỀN
    // =========================

    const shippingFee = 0;

    const totalAmount =
        subtotal + shippingFee;

    subtotalElement.textContent =
        formatPrice(subtotal);

    shippingFeeElement.textContent =
        formatPrice(shippingFee);

    totalAmountElement.textContent =
        formatPrice(totalAmount);
}


// =========================
// ĐẶT HÀNG
// =========================

btnPlaceOrder.addEventListener("click", async () => {

    const address = addressInput.value.trim();

    const paymentMethod =
        document.querySelector(
            'input[name="paymentMethod"]:checked'
        )?.value;


    // Kiểm tra địa chỉ
    if (!address) {

        alert("Vui lòng nhập địa chỉ giao hàng.");

        return;
    }


    // Kiểm tra phương thức thanh toán
    if (!paymentMethod) {

        alert("Vui lòng chọn phương thức thanh toán.");

        return;
    }


    const checkoutRequest = {

        accountId: accountId,

        address: address,

        paymentMethod: paymentMethod
    };


    console.log(
        "Checkout Request:",
        checkoutRequest
    );


    try {

        btnPlaceOrder.disabled = true;

        btnPlaceOrder.textContent =
            "ĐANG ĐẶT HÀNG...";


        const response = await fetch(
            "http://127.0.0.1:8080/api/checkout",
            {
                method: "POST",

                credentials: "include",

                headers: {
                    "Content-Type": "application/json"
                },

                body: JSON.stringify(checkoutRequest)
            }
        );


        if (!response.ok) {

            throw new Error(
                "Checkout thất bại: " +
                response.status
            );
        }


        const order = await response.json();

        console.log("Order:", order);


        alert("Đặt hàng thành công!");

        window.location.href = "order.html";


    } catch (error) {

        console.error(error);

        alert("Có lỗi xảy ra khi đặt hàng.");

    } finally {

        btnPlaceOrder.disabled = false;

        btnPlaceOrder.textContent =
            "ĐẶT HÀNG";
    }
});


// =========================
// START
// =========================

loadCheckout();