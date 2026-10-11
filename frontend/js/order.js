document.addEventListener("DOMContentLoaded", () => {
    loadOrders();
});


// async function loadOrders() {

//     const orderList = document.getElementById("orderList");
//     const emptyOrder = document.getElementById("emptyOrder");

//     try {

//         const response = await fetch("http://localhost:8080/api/orders");

//         if (!response.ok) {
//             throw new Error("Không thể lấy danh sách đơn hàng");
//         }

//         const orders = await response.json();

//         console.log("Orders:", orders);

//         // Không có đơn hàng
//         if (orders.length === 0) {

//             orderList.innerHTML = "";
//             emptyOrder.classList.remove("d-none");

//             return;
//         }

//         // Có đơn hàng
//         emptyOrder.classList.add("d-none");

//         orderList.innerHTML = "";

//         orders.forEach(order => {

//             const orderHTML = `
//                 <div class="order-card mb-4">

//                     <div class="order-header d-flex justify-content-between align-items-center">

//                         <div>
//                             <h5>Đơn hàng #${order.id}</h5>

//                             <p class="text-muted mb-0">
//                                 ${formatDate(order.orderDate)}
//                             </p>
//                         </div>

//                         <span class="badge ${getStatusClass(order.status)}">
//                             ${order.status}
//                         </span>

//                     </div>

//                     <hr>

//                     <div class="order-info">

//                         <p class="mb-2">
//                             <strong>Địa chỉ:</strong>
//                             ${order.address}
//                         </p>

//                         <p class="mb-2">
//                             <strong>Thanh toán:</strong>
//                             ${order.paymentMethod}
//                         </p>

//                         <div class="d-flex justify-content-between align-items-center">

//                             <span>
//                                 Tổng tiền
//                             </span>

//                             <strong>
//                                 ${formatPrice(order.totalAmount)}
//                             </strong>

//                         </div>

//                     </div>

//                     <div class="text-end mt-3">

//                         <a href="orderdetail.html?id=${order.id}"
//                            class="btn btn-outline-dark">
//                             Xem chi tiết
//                         </a>

//                     </div>

//                 </div>
//             `;

//             orderList.innerHTML += orderHTML;
//         });

//     } catch (error) {

//         console.error("Lỗi:", error);

//         orderList.innerHTML = `
//             <div class="alert alert-danger">
//                 Không thể tải danh sách đơn hàng.
//             </div>
//         `;
//     }
// }
async function loadOrders() {

    const orderList = document.getElementById("orderList");
    const emptyOrder = document.getElementById("emptyOrder");

    try {

        const response = await fetch(
            "http://127.0.0.1:8080/api/orders/my-orders",
            {
                credentials: "include"
            }
        );

        if (!response.ok) {
            throw new Error("Không thể lấy danh sách đơn hàng");
        }

        const orders = await response.json();

        console.log("Orders của tài khoản hiện tại:", orders);

        // Không có đơn hàng
        if (orders.length === 0) {

            orderList.innerHTML = "";

            emptyOrder.classList.remove("d-none");

            return;
        }

        // Có đơn hàng
        emptyOrder.classList.add("d-none");

        orderList.innerHTML = "";

        orders.forEach(order => {

            const orderHTML = `
                <div class="order-card mb-4">

                    <div class="order-header d-flex justify-content-between align-items-center">

                        <div>

                            <h5>Đơn hàng #${order.id}</h5>

                            <p class="text-muted mb-0">
                                ${formatDate(order.orderDate)}
                            </p>

                        </div>

                        <span class="badge ${getStatusClass(order.status)}">
                            ${order.status}
                        </span>

                    </div>

                    <hr>

                    <div class="order-info">

                        <p class="mb-2">
                            <strong>Địa chỉ:</strong>
                            ${order.address}
                        </p>

                        <p class="mb-2">
                            <strong>Thanh toán:</strong>
                            ${order.paymentMethod}
                        </p>

                        <div class="d-flex justify-content-between align-items-center">

                            <span>
                                Tổng tiền
                            </span>

                            <strong>
                                ${formatPrice(order.totalAmount)}
                            </strong>

                        </div>

                    </div>

                    <div class="text-end mt-3">

                        <a
                            href="orderdetail.html?id=${order.id}"
                            class="btn btn-outline-dark"
                        >
                            Xem chi tiết
                        </a>

                    </div>

                </div>
            `;

            orderList.innerHTML += orderHTML;
        });

    } catch (error) {

        console.error("Lỗi:", error);

        orderList.innerHTML = `
            <div class="alert alert-danger">
                Không thể tải danh sách đơn hàng.
            </div>
        `;
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

    const dateObject = new Date(date);

    return dateObject.toLocaleDateString("vi-VN");
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