const productList = document.getElementById("productList");

const productsPerPage = 12;

let currentPage = 1;

let products = [];

let filteredProducts = [];


// ========================================
// LOAD PRODUCTS
// ========================================

// async function loadProducts() {

//     const response = await fetch("http://localhost:8080/api/products");

//     products = await response.json();

//     console.log("Products:", products);

//     // Ban đầu hiển thị tất cả sản phẩm
//     filteredProducts = products;

//     renderProducts();
// }

async function loadProducts() {

    const params = new URLSearchParams(window.location.search);
    const categoryId = params.get("category");

    let response;

    if (categoryId) {

        response = await fetch(
            `http://localhost:8080/api/products/category/${categoryId}`
        );

    } else {

        response = await fetch(
            "http://localhost:8080/api/products"
        );
    }

    products = await response.json();

    console.log("Products:", products);

    filteredProducts = products;

    currentPage = 1;

    renderProducts();
}


// ========================================
// RENDER PRODUCTS
// ========================================

function renderProducts() {

    productList.innerHTML = "";

    // Tính tổng số trang dựa trên
    // danh sách sản phẩm ĐÃ LỌC
    const totalPages = Math.ceil(
        filteredProducts.length / productsPerPage
    );

    // Vị trí bắt đầu
    const start = (currentPage - 1) * productsPerPage;

    // Vị trí kết thúc
    const end = start + productsPerPage;

    // Lấy sản phẩm của trang hiện tại
    const productsToShow = filteredProducts.slice(start, end);


    // Render sản phẩm
    productsToShow.forEach(product => {

        productList.innerHTML += `

            <div class="product-card">

                <div class="img-sp">
                <a href="productdetail.html?id=${product.id}">

                    <img
                        src="images/products/${product.images[0]}"
                        alt="${product.name}"
                    >
                    </a>

                </div>

                <h3>${product.name}</h3>

                <p>
                    ${product.price.toLocaleString("vi-VN")}đ
                </p>

                <p>
                    Còn lại: ${product.quantity}
                </p>

            </div>

        `;
    });


    // Render pagination
    renderPagination();
}


// ========================================
// NEXT PAGE
// ========================================

function nextPage() {

    const totalPages = Math.ceil(
        filteredProducts.length / productsPerPage
    );

    if (currentPage < totalPages) {

        currentPage++;

        renderProducts();
    }
}


// ========================================
// PREVIOUS PAGE
// ========================================

function previousPage() {

    if (currentPage > 1) {

        currentPage--;

        renderProducts();
    }
}


// ========================================
// GO TO PAGE
// ========================================

function goToPage(page) {

    currentPage = page;

    renderProducts();
}


// ========================================
// RENDER PAGINATION
// ========================================

function renderPagination() {

    const pagination = document.getElementById("pagination");

    pagination.innerHTML = "";


    // Tổng số trang dựa trên filteredProducts
    const totalPages = Math.ceil(
        filteredProducts.length / productsPerPage
    );


    // Nếu không có sản phẩm
    if (totalPages === 0) {
        return;
    }


    // ====================================
    // PREVIOUS
    // ====================================

    pagination.innerHTML += `

        <button
            onclick="previousPage()"
            ${currentPage === 1 ? "disabled" : ""}
        >

            ←

        </button>

    `;


    // ====================================
    // CÁC NÚT SỐ
    // ====================================

    for (let page = 1; page <= totalPages; page++) {

        pagination.innerHTML += `

            <button
                onclick="goToPage(${page})"
                class="${page === currentPage ? "active" : ""}"
            >

                ${page}

            </button>

        `;
    }


    // ====================================
    // NEXT
    // ====================================

    pagination.innerHTML += `

        <button
            onclick="nextPage()"
            ${currentPage === totalPages ? "disabled" : ""}
        >

            →

        </button>

    `;
}


// ========================================
// LOAD BRANDS
// ========================================

const brandFilterBtn = document.getElementById("brandFilterBtn");

const brandDropdown = document.getElementById("brandDropdown");


brandFilterBtn.addEventListener("click", function () {

    if (brandDropdown.style.display === "block") {

        brandDropdown.style.display = "none";

    } else {

        brandDropdown.style.display = "block";

    }

});


// ========================================
// LOAD BRANDS FROM API
// ========================================

async function loadBrands() {

    const response = await fetch(
        "http://localhost:8080/api/brands"
    );

    console.log("Response:", response);

    const brands = await response.json();

    console.log("Brands:", brands);


    brands.forEach(brand => {

        brandDropdown.innerHTML += `

            <li data-brand-id="${brand.id}">

                ${brand.name}

            </li>

        `;

    });
}


// ========================================
// CLICK BRAND
// ========================================

brandDropdown.addEventListener("click", function (event) {

    if (event.target.tagName === "LI") {

        const brandId = Number(
            event.target.dataset.brandId
        );

        console.log("Brand ID:", brandId);


        // Lọc sản phẩm theo Brand
        filteredProducts = products.filter(product => {

            return product.brandId === brandId;

        });


        console.log(
            "Filtered products:",
            filteredProducts
        );


        // Quay về trang 1
        currentPage = 1;


        // Render lại
        renderProducts();


        // Đóng dropdown
        brandDropdown.style.display = "none";
    }

});
// ========================================
// SEARCH PRODUCTS
// ========================================

async function searchProducts() {

    const searchInput = document.getElementById("searchInput");

    const keyword = searchInput.value.trim();

    // Nếu ô tìm kiếm trống
    if (keyword === "") {
        loadProducts();
        return;
    }

    try {

          const response = await fetch(
            `http://localhost:8080/api/products/search?keyword=${encodeURIComponent(keyword)}`
        );

        if (!response.ok) {
            throw new Error("Không thể tìm kiếm sản phẩm");
        }

        const results = await response.json();

        console.log("Search results:", results);

        filteredProducts = results;
        currentPage = 1;

        renderProducts();

    } catch (error) {

        console.error("Lỗi tìm kiếm:", error);

    }
}

document.addEventListener("headerLoaded", function () {

    const searchButton = document.getElementById("searchButton");
    const searchInput = document.getElementById("searchInput");

    searchButton.addEventListener("click", function () {
        searchProducts();
    });

    searchInput.addEventListener("keydown", function (event) {
        if (event.key === "Enter") {
            searchProducts();
        }
    });

});

// Nhấn nút kính lúp
// searchButton.addEventListener("click", function () {
//     searchProducts();
// });


// // Nhấn Enter trong ô tìm kiếm
// searchInput.addEventListener("keydown", function (event) {

//     if (event.key === "Enter") {
//         searchProducts();
//     }

// });



// ========================================
// PRICE FILTER
// ========================================

const priceFilterBtn =
    document.getElementById("priceFilterBtn");

const priceDropdown =
    document.getElementById("priceDropdown");


// ========================================
// MỞ / ĐÓNG PRICE DROPDOWN
// ========================================

priceFilterBtn.addEventListener("click", function () {

    if (priceDropdown.style.display === "block") {

        priceDropdown.style.display = "none";

    } else {

        priceDropdown.style.display = "block";

    }

});


// ========================================
// CLICK PRICE
// ========================================

const priceItems =
    priceDropdown.querySelectorAll("li");


priceItems.forEach(item => {

    item.addEventListener("click", function () {

        const min = Number(
            this.dataset.min
        );


        const max =
            this.dataset.max === ""
                ? Infinity
                : Number(this.dataset.max);


        console.log("Min:", min);

        console.log("Max:", max);


        // Lọc sản phẩm theo giá
        filteredProducts = products.filter(product => {

            return (
                product.price >= min &&
                product.price <= max
            );

        });


        console.log(
            "Filtered products:",
            filteredProducts
        );


        // Quay về trang 1
        currentPage = 1;


        // Render lại
        renderProducts();


        // Đóng dropdown
        priceDropdown.style.display = "none";

    });

});
const params = new URLSearchParams(window.location.search);
const categoryId = params.get("category");

const categoryBanner = document.getElementById("categoryBanner");

const banners = {
    1: "images/banners/banner-ring.png",
    2: "images/banners/banner-necklace.png",
    3: "images/banners/banner-bracelet.png",
    4: "images/banners/banner-earring.png",
    6: "images/banners/banner-diamond.png",
    5: "images/banners/banner-tsc.png"
};

if (categoryId && banners[categoryId]) {
    categoryBanner.src = banners[categoryId];
}


// ========================================
// CHẠY
// ========================================

loadProducts();

loadBrands();
