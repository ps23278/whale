document.addEventListener("DOMContentLoaded", () => {
    loadOrderDetail();
});


async function loadOrderDetail() {

    const params = new URLSearchParams(window.location.search);
    const orderId = params.get("id");

    if (!orderId) {
        alert("Không tìm thấy mã đơn hàng.");
        return;
    }

    const orderInfo = document.getElementById("orderInfo");
    const orderItems = document.getElementById("orderItems");
    const totalAmount = document.getElementById("totalAmount");

    try {

        // =========================
        // 1. LẤY THÔNG TIN ORDER
        // =========================

        const orderResponse = await fetch(
            `http://localhost:8080/api/orders/${orderId}`
        );

        if (!orderResponse.ok) {
            throw new Error("Không tìm thấy đơn hàng.");
        }

        const order = await orderResponse.json();

        console.log("Order:", order);


        // =========================
        // 2. HIỂN THỊ ORDER INFO
        // =========================

        orderInfo.innerHTML = `
            <div class="order-info">

                <div class="d-flex justify-content-between align-items-center">
                    <h4>Đơn hàng #${order.id}</h4>

                    <span class="badge ${getStatusClass(order.status)}">
                        ${order.status}
                    </span>
                </div>

                <p class="mb-2">
                    <strong>Ngày đặt:</strong>
                    ${formatDate(order.orderDate)}
                </p>

                <p class="mb-2">
                    <strong>Địa chỉ:</strong>
                    ${order.address}
                </p>

                <p class="mb-0">
                    <strong>Thanh toán:</strong>
                    ${order.paymentMethod ?? "Chưa xác định"}
                </p>

            </div>
        `;


        // =========================
        // 3. LẤY ORDER DETAILS
        // =========================

        const detailResponse = await fetch(
            "http://localhost:8080/api/order-details"
        );

        if (!detailResponse.ok) {
            throw new Error("Không thể lấy chi tiết đơn hàng.");
        }

        const allDetails = await detailResponse.json();

        console.log("Order Details:", allDetails);


        // Chỉ lấy detail thuộc order hiện tại
        const orderDetails = allDetails.filter(detail => {

            return Number(detail.orderId) === Number(orderId);

        });


        // =========================
        // 4. KHÔNG CÓ SẢN PHẨM
        // =========================

        if (orderDetails.length === 0) {

            orderItems.innerHTML = `
                <div class="alert alert-warning">
                    Đơn hàng này chưa có sản phẩm.
                </div>
            `;

            totalAmount.textContent = formatPrice(order.totalAmount);

            return;
        }


        // =========================
        // 5. HIỂN THỊ PRODUCTS
        // =========================

        orderItems.innerHTML = "";

        for (const detail of orderDetails) {

            // Lấy thông tin product
            const productResponse = await fetch(
                `http://localhost:8080/api/products/${detail.productId}`
            );

            if (!productResponse.ok) {
                continue;
            }

            const product = await productResponse.json();

            const quantity = detail.quantity;
            const price = detail.price ?? detail.unitPrice ?? product.price;

            const subtotal = Number(price) * Number(quantity);

            const itemHTML = `
                <div class="order-item border-bottom py-3">

                    <div class="row align-items-center">

                        <!-- PRODUCT -->
                        <div class="col-md-5">

                            <div class="d-flex align-items-center">

                                <div>
                                    <strong>
                                        ${product.name}
                                    </strong>
                                </div>

                            </div>

                        </div>


                        <!-- QUANTITY -->
                        <div class="col-md-2">

                            <span>
                                SL: ${quantity}
                            </span>

                        </div>


                        <!-- PRICE -->
                        <div class="col-md-2">

                            ${formatPrice(price)}

                        </div>


                        <!-- SUBTOTAL -->
                        <div class="col-md-3 text-end">

                            <strong>
                                ${formatPrice(subtotal)}
                            </strong>

                        </div>

                    </div>

                </div>
            `;

            orderItems.innerHTML += itemHTML;
        }


        // =========================
        // 6. TOTAL
        // =========================

        totalAmount.textContent =
            formatPrice(order.totalAmount);


    } catch (error) {

        console.error("Order detail error:", error);

        orderInfo.innerHTML = "";

        orderItems.innerHTML = `
            <div class="alert alert-danger">
                Không thể tải chi tiết đơn hàng.
            </div>
        `;

        totalAmount.textContent = "0đ";
    }
}


/* =========================
   FORMAT PRICE
========================= */

function formatPrice(price) {

    if (price == null) {
        return "0đ";
    }

    return Number(price).toLocaleString("vi-VN") + "đ";
}


/* =========================
   FORMAT DATE
========================= */

function formatDate(date) {

    if (!date) {
        return "";
    }

    return new Date(date).toLocaleDateString("vi-VN");
}


/* =========================
   ORDER STATUS
========================= */

function getStatusClass(status) {

    switch (status) {

        case "PENDING":
            return "bg-warning text-dark";

        case "CONFIRMED":
            return "bg-primary";

        case "SHIPPING":
            return "bg-info text-dark";

        case "COMPLETED":
            return "bg-success";

        case "CANCELLED":
            return "bg-danger";

        default:
            return "bg-secondary";
    }
}