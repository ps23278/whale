const API_URL = "http://localhost:8080/api/orders";

const orderTableBody =
    document.getElementById("orderTableBody");

const searchInput =
    document.getElementById("searchInput");

const orderModal =
    document.getElementById("orderModal");

const orderDetail =
    document.getElementById("orderDetail");

const closeModal =
    document.getElementById("closeModal");


let orders = [];


// =========================
// LOAD ORDERS
// =========================

async function loadOrders() {

    try {

        const response = await fetch(API_URL);

        if (!response.ok) {
            throw new Error("Cannot load orders");
        }

        orders = await response.json();

        displayOrders(orders);

    } catch (error) {

        console.error(error);

        orderTableBody.innerHTML = `
            <tr>
                <td colspan="7">
                    Cannot load orders
                </td>
            </tr>
        `;
    }
}


// =========================
// DISPLAY ORDERS
// =========================

function displayOrders(orderList) {

    orderTableBody.innerHTML = "";

    orderList.forEach(order => {

        const row = document.createElement("tr");

        row.innerHTML = `

            <td>
                #${order.id}
            </td>

            <td>
                ${formatDate(order.orderDate)}
            </td>

            <td>
                ${order.accountId ?? ""}
            </td>

            <td>
                ${formatPrice(order.totalAmount)}
            </td>

            <td>
                ${order.paymentMethod ?? ""}
            </td>

            <td>
                ${getStatusHTML(order.status)}
            </td>

            <td>

                <button
                    class="btn-view"
                    onclick="viewOrder(${order.id})"
                >
                    View
                </button>

            </td>

        `;

        orderTableBody.appendChild(row);

    });
}


// =========================
// STATUS
// =========================

function getStatusHTML(status) {

    if (!status) {
        return "";
    }

    const statusClass =
        status.toLowerCase().replaceAll(" ", "-");

    return `
        <span class="status status-${statusClass}">
            ${status}
        </span>
    `;
}


// =========================
// FORMAT PRICE
// =========================

function formatPrice(price) {

    return Number(price).toLocaleString("vi-VN") + "đ";

}


// =========================
// FORMAT DATE
// =========================

function formatDate(date) {

    if (!date) {
        return "";
    }

    return new Date(date).toLocaleDateString("vi-VN");

}


// =========================
// SEARCH
// =========================

searchInput.addEventListener("input", function () {

    const keyword =
        searchInput.value.toLowerCase();

    const filteredOrders = orders.filter(order =>

        String(order.id)
            .toLowerCase()
            .includes(keyword)

        ||

        String(order.accountId ?? "")
            .toLowerCase()
            .includes(keyword)

    );

    displayOrders(filteredOrders);

});


// =========================
// VIEW ORDER
// =========================

async function viewOrder(id) {

    const order =
        orders.find(order => order.id === id);

    if (!order) {
        return;
    }


    orderDetail.innerHTML = `

        <div class="detail-row">
            <span class="detail-label">
                Order ID
            </span>

            <span>
                #${order.id}
            </span>
        </div>


        <div class="detail-row">
            <span class="detail-label">
                Date
            </span>

            <span>
                ${formatDate(order.orderDate)}
            </span>
        </div>


        <div class="detail-row">
            <span class="detail-label">
                Customer
            </span>

            <span>
                ${order.accountId ?? ""}
            </span>
        </div>


        <div class="detail-row">
            <span class="detail-label">
                Address
            </span>

            <span>
                ${order.address ?? ""}
            </span>
        </div>


        <div class="detail-row">
            <span class="detail-label">
                Payment
            </span>

            <span>
                ${order.paymentMethod ?? ""}
            </span>
        </div>


        <div class="detail-row">
            <span class="detail-label">
                Status
            </span>

            <span>
                ${order.status ?? ""}
            </span>
        </div>


        <div class="detail-row">
            <span class="detail-label">
                Total
            </span>

            <span>
                ${formatPrice(order.totalAmount)}
            </span>
        </div>

    `;


    orderModal.style.display = "flex";
}


// =========================
// CLOSE MODAL
// =========================

closeModal.addEventListener("click", function () {

    orderModal.style.display = "none";

});


orderModal.addEventListener("click", function (event) {

    if (event.target === orderModal) {

        orderModal.style.display = "none";

    }
    
});


// =========================
// START
// =========================

loadOrders();