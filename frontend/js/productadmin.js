const API_URL = "http://localhost:8080/api/products";

const productTableBody = document.getElementById("productTableBody");

const searchInput = document.getElementById("searchInput");

const productModal = document.getElementById("productModal");

const productForm = document.getElementById("productForm");

const modalTitle = document.getElementById("modalTitle");

const addProductBtn = document.getElementById("addProductBtn");

const cancelBtn = document.getElementById("cancelBtn");


// =========================
// LOAD PRODUCTS
// =========================

async function loadProducts() {

    try {

        const response = await fetch(API_URL);

        const products = await response.json();

        displayProducts(products);

    } catch (error) {

        console.error("Error loading products:", error);

    }
}


// =========================
// DISPLAY PRODUCTS
// =========================

function displayProducts(products) {

    productTableBody.innerHTML = "";

    products.forEach(product => {

        const row = document.createElement("tr");

        row.innerHTML = `

            <td>

                <img
                    class="product-image"
                     src="${
                product.images && product.images.length > 0
                    ? "../images/products/" + product.images[0]
                    : "../images/no-image.png"
            }"
                >

            </td>

            <td>
                ${product.name}
            </td>

            <td>
                ${product.brandId ?? ""}
            </td>

            <td>
                ${formatPrice(product.price)}
            </td>

            <td>
                ${product.quantity}
            </td>

            <td>

                <button
                    class="btn-edit"
                    onclick="editProduct(${product.id})"
                >
                    Edit
                </button>

                <button
                    class="btn-delete"
                    onclick="deleteProduct(${product.id})"
                >
                    Delete
                </button>

            </td>

        `;

        productTableBody.appendChild(row);

    });
}


// =========================
// FORMAT PRICE
// =========================

function formatPrice(price) {

    return Number(price).toLocaleString("vi-VN") + "đ";

}


// =========================
// SEARCH
// =========================

searchInput.addEventListener("input", async function () {

    const keyword = searchInput.value.toLowerCase();

    const response = await fetch(API_URL);

    const products = await response.json();

    const filteredProducts = products.filter(product =>
        product.name.toLowerCase().includes(keyword)
    );

    displayProducts(filteredProducts);

});


// =========================
// OPEN ADD MODAL
// =========================

addProductBtn.addEventListener("click", function () {

    modalTitle.textContent = "Add Product";

    productForm.reset();

    document.getElementById("productId").value = "";

    productModal.style.display = "flex";

});


// =========================
// CLOSE MODAL
// =========================

cancelBtn.addEventListener("click", function () {

    productModal.style.display = "none";

});


// =========================
// ADD / UPDATE PRODUCT
// =========================

productForm.addEventListener("submit", async function (event) {

    event.preventDefault();

    const productId =
        document.getElementById("productId").value;

    const productData = {

        name: document.getElementById("productName").value,

        price: document.getElementById("productPrice").value,

        quantity: document.getElementById("productQuantity").value,

        description:
            document.getElementById("productDescription").value,

        brandId:
            document.getElementById("productBrand").value

    };


    try {

        let response;


        // ADD
        if (!productId) {

            response = await fetch(API_URL, {

                method: "POST",

                headers: {
                    "Content-Type": "application/json"
                },

                credentials: "include",

                body: JSON.stringify(productData)

            });

        }


        // UPDATE
        else {

            response = await fetch(
                `${API_URL}/${productId}`,
                {

                    method: "PUT",

                    headers: {
                        "Content-Type": "application/json"
                    },

                    credentials: "include",

                    body: JSON.stringify(productData)

                }
            );

        }


        if (!response.ok) {

            throw new Error("Failed to save product");

        }


        productModal.style.display = "none";

        productForm.reset();

        loadProducts();


    } catch (error) {

        console.error(error);

        alert("Cannot save product");

    }

});


// =========================
// EDIT PRODUCT
// =========================

async function editProduct(id) {

    try {

        const response =
            await fetch(`${API_URL}/${id}`);

        const product =
            await response.json();


        modalTitle.textContent = "Edit Product";


        document.getElementById("productId").value =
            product.id;

        document.getElementById("productName").value =
            product.name;

        document.getElementById("productPrice").value =
            product.price;

        document.getElementById("productQuantity").value =
            product.quantity;

        document.getElementById("productDescription").value =
            product.description || "";

        document.getElementById("productBrand").value =
            product.brandId || "";


        productModal.style.display = "flex";


    } catch (error) {

        console.error(error);

    }

}


// =========================
// DELETE PRODUCT
// =========================

async function deleteProduct(id) {

    const confirmDelete =
        confirm("Are you sure you want to delete this product?");


    if (!confirmDelete) {
        return;
    }


    try {

        const response =
            await fetch(`${API_URL}/${id}`, {

                method: "DELETE",

                credentials: "include"

            });


        if (!response.ok) {

            throw new Error("Delete failed");

        }


        loadProducts();


    } catch (error) {

        console.error(error);

        alert("Cannot delete product");

    }

}


// =========================
// START
// =========================

loadProducts();