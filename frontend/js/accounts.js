const API_URL = "http://127.0.0.1:8080/api/accounts";


const accountTableBody =
    document.getElementById("accountTableBody");

const searchInput =
    document.getElementById("searchInput");

const accountModal =
    document.getElementById("accountModal");

const accountDetail =
    document.getElementById("accountDetail");

const closeModal =
    document.getElementById("closeModal");


let accounts = [];


// =========================
// LOAD ACCOUNTS
// =========================

async function loadAccounts() {

    try {

        const response = await fetch(API_URL, {
            credentials: "include"
        });

        if (!response.ok) {
            throw new Error(
                `Cannot load accounts: ${response.status}`
            );
        }

        accounts = await response.json();

        displayAccounts(accounts);

    } catch (error) {

        console.error(error);

        accountTableBody.innerHTML = `
            <tr>
                <td colspan="6">
                    Cannot load accounts
                </td>
            </tr>
        `;
    }
}

// =========================
// DISPLAY ACCOUNTS
// =========================

function displayAccounts(accountList) {

    accountTableBody.innerHTML = "";


    accountList.forEach(account => {

        const row = document.createElement("tr");


        const photo =
            account.photo
                ? `../images/accounts/${account.photo}`
                : "../images/no-image.png";


        row.innerHTML = `

            <td>
                ${account.id}
            </td>


            <td>

                <img
                    class="account-photo"
                    src="${photo}"
                    onerror="this.src='../images/no-image.png'"
                >

            </td>   


            <td>
                ${account.username ?? ""}
            </td>


            <td>
                ${account.email ?? ""}
            </td>


            <td>

                ${getStatusHTML(account.activated)}

            </td>


            <td>

                <button
                    class="btn-view"
                    onclick="viewAccount(${account.id})"
                >
                    View
                </button>

            </td>

        `;


        accountTableBody.appendChild(row);

    });

}


// =========================
// STATUS
// =========================

function getStatusHTML(activated) {

    if (activated) {

        return `
            <span class="status status-active">
                Active
            </span>
        `;

    }


    return `
        <span class="status status-inactive">
            Inactive
        </span>
    `;
}


// =========================
// SEARCH
// =========================

searchInput.addEventListener("input", function () {

    const keyword =
        searchInput.value.toLowerCase();


    const filteredAccounts =
        accounts.filter(account => {

            const username =
                String(account.username ?? "")
                    .toLowerCase();

            const email =
                String(account.email ?? "")
                    .toLowerCase();


            return (
                username.includes(keyword) ||
                email.includes(keyword)
            );

        });


    displayAccounts(filteredAccounts);

});


// =========================
// VIEW ACCOUNT
// =========================

function viewAccount(id) {

    const account =
        accounts.find(account => account.id === id);


    if (!account) {
        return;
    }


    accountDetail.innerHTML = `

        <div class="detail-row">

            <span class="detail-label">
                ID
            </span>

            <span>
                ${account.id}
            </span>

        </div>


        <div class="detail-row">

            <span class="detail-label">
                Username
            </span>

            <span>
                ${account.username ?? ""}
            </span>

        </div>


        <div class="detail-row">

            <span class="detail-label">
                Email
            </span>

            <span>
                ${account.email ?? ""}
            </span>

        </div>


        <div class="detail-row">

            <span class="detail-label">
                Status
            </span>

            <span>

                ${
                    account.activated
                        ? "Active"
                        : "Inactive"
                }

            </span>

        </div>


        <button
            class="btn-status"
            onclick="toggleAccountStatus(${account.id})"
        >

            ${
                account.activated
                    ? "Deactivate Account"
                    : "Activate Account"
            }

        </button>

    `;


    accountModal.style.display = "flex";

}


// =========================
// CLOSE MODAL
// =========================

closeModal.addEventListener("click", function () {

    accountModal.style.display = "none";

});


accountModal.addEventListener("click", function (event) {

    if (event.target === accountModal) {

        accountModal.style.display = "none";

    }

});


// =========================
// TOGGLE STATUS
// =========================

async function toggleAccountStatus(id) {

    const account =
        accounts.find(account => account.id === id);


    if (!account) {
        return;
    }


    const newStatus =
        !account.activated;


    try {

        const response = await fetch(
            `${API_URL}/${id}`,
            {
                method: "PUT",

                headers: {
                    "Content-Type": "application/json"
                },

                credentials: "include",

                body: JSON.stringify({

                    username: account.username,

                    email: account.email,

                    activated: newStatus

                })
            }
        );


        if (!response.ok) {

            throw new Error(
                "Cannot update account"
            );

        }


        account.activated = newStatus;


        displayAccounts(accounts);


        accountModal.style.display = "none";


    } catch (error) {

        console.error(error);

        alert("Cannot update account");

    }

}


// =========================
// START
// =========================

loadAccounts();