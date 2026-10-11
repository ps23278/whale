
document.addEventListener("DOMContentLoaded", function () {

    loadDashboard();

});


async function loadDashboard() {

    try {

        // Lấy dữ liệu từ backend
        const productsResponse = await fetch("http://localhost:8080/api/products");
        const ordersResponse = await fetch("http://localhost:8080/api/orders");
        const accountsResponse = await fetch("http://localhost:8080/api/accounts");


        const products = await productsResponse.json();
        const orders = await ordersResponse.json();
        const accounts = await accountsResponse.json();


        // Hiển thị số lượng
        document.getElementById("totalProducts").textContent = products.length;

        document.getElementById("totalOrders").textContent = orders.length;

        document.getElementById("totalAccounts").textContent = accounts.length;


        // Hiển thị đơn hàng
        displayRecentOrders(orders);


    } catch (error) {

        console.error("Cannot load dashboard:", error);

    }

}


function displayRecentOrders(orders) {

    const tableBody = document.getElementById("recentOrders");

    tableBody.innerHTML = "";


    // Lấy 5 đơn hàng gần nhất
    const recentOrders = orders.slice(-5).reverse();


    recentOrders.forEach(order => {

        const row = document.createElement("tr");


        row.innerHTML = `
            <td>#${order.id}</td>

            <td>
                ${order.accountName || "Customer"}
            </td>

            <td>
                ${formatCurrency(order.totalAmount)}
            </td>

            <td>
                <span class="status ${getStatusClass(order.status)}">
                    ${order.status}
                </span>
            </td>
        `;


        tableBody.appendChild(row);

    });

}


function getStatusClass(status) {

    if (!status) {
        return "";
    }


    switch (status.toLowerCase()) {

        case "completed":
            return "completed";

        case "pending":
            return "pending";

        case "shipping":
            return "shipping";

        default:
            return "";

    }

}


function formatCurrency(amount) {

    if (!amount) {
        return "0đ";
    }


    return Number(amount).toLocaleString("vi-VN") + "đ";

}

