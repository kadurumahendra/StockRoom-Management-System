const BASE_URL = "http://localhost:8080";

// ================= LOGIN =================

function login() {
    console.log("Username:", document.getElementById("username").value);
    console.log("Password:", document.getElementById("password").value);

    const user = {
        username: document.getElementById("username").value,
        password: document.getElementById("password").value
    };

    fetch(`${BASE_URL}/users/login`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(user)
    })
    .then(response => response.text())
    .then(data => {
        console.log("Server Response:", data);

        if (data === "Login Successful") {
            window.location.href = "dashboard.html";
        } else {
            document.getElementById("message").innerText = data;
        }
    })
    .catch(error => {
        console.log(error);
    });
}

// ================= INGREDIENTS =================

function saveIngredient() {
    const id = document.getElementById("ingredientId").value;

    const ingredient = {
        name: document.getElementById("name").value,
        quantity: parseFloat(document.getElementById("quantity").value),
        unit: document.getElementById("unit").value,
        parLevel: parseFloat(document.getElementById("parLevel").value)
    };

    if (id !== "") {
        fetch(`${BASE_URL}/ingredients/${id}`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(ingredient)
        })
        .then(response => response.json())
        .then(() => {
            alert("Ingredient Updated");
            getIngredients();
            clearForm();
        });
    } else {
        fetch(`${BASE_URL}/ingredients`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(ingredient)
        })
        .then(response => response.json())
        .then(() => {
            alert("Ingredient Added");
            getIngredients();
            clearForm();
        });
    }
}

function getIngredients() {
    fetch(`${BASE_URL}/ingredients`)
    .then(response => response.json())
    .then(data => {
        let output = "";

        data.forEach(item => {
            let alertMessage = "";

            if (item.quantity < item.parLevel) {
                alertMessage = `<p style="color:red;font-weight:bold;">⚠ Low Stock Alert</p>`;
            } else {
                alertMessage = `<p style="color:green;">Stock Available</p>`;
            }

            output += `
                <div style="margin-top:15px; padding:15px; background:#f4f4f4;">
                    Name: ${item.name} |
                    Quantity: ${item.quantity} ${item.unit} |
                    Par Level: ${item.parLevel}
                    <br><br>
                    ${alertMessage}
                    <br>
                    <button onclick="editIngredient(${item.id}, '${item.name}', ${item.quantity}, '${item.unit}', ${item.parLevel})">Update</button>
                    <button onclick="deleteIngredient(${item.id})">Delete</button>
                </div>
            `;
        });

        document.getElementById("ingredientsList").innerHTML = output;
    });
}

function deleteIngredient(id) {
    fetch(`${BASE_URL}/ingredients/${id}`, {
        method: "DELETE"
    })
    .then(response => response.text())
    .then(data => {
        alert(data);
        getIngredients();
    });
}

function editIngredient(id, name, quantity, unit, parLevel) {
    document.getElementById("ingredientId").value = id;
    document.getElementById("name").value = name;
    document.getElementById("quantity").value = quantity;
    document.getElementById("unit").value = unit;
    document.getElementById("parLevel").value = parLevel;
}

function clearForm() {
    document.getElementById("ingredientId").value = "";
    document.getElementById("name").value = "";
    document.getElementById("quantity").value = "";
    document.getElementById("unit").value = "";
    document.getElementById("parLevel").value = "";
}

// ================= TRANSACTIONS =================

function saveTransaction() {
    const id = document.getElementById("transactionId").value;

    const transaction = {
        ingredientId: parseInt(document.getElementById("transactionIngredientId").value),
        storeId: parseInt(document.getElementById("transactionStoreId").value),
        transactionType: document.getElementById("transactionType").value,
        quantity: parseFloat(document.getElementById("transactionQuantity").value)
    };

    console.log(transaction);

    fetch(`${BASE_URL}/transactions`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(transaction)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("Transaction save failed");
        }
        return response.json();
    })
    .then(data => {
        alert("Transaction Added Successfully");
        getTransactions();
        clearTransactionForm();
    })
    .catch(error => {
        console.error(error);
        alert("Error saving transaction");
    });
}

function getTransactions() {
    fetch(`${BASE_URL}/transactions`)
    .then(response => response.json())
    .then(data => {
        console.log("Transactions:", data);

        let output = "";

        if (data.length === 0) {
            output = "<h3>No Transactions Found</h3>";
        } else {
			data.forEach(item => {
			    output += `
			        <div style="margin-top:15px; padding:15px; background:#f4f4f4;">
			            Ingredient ID: ${item.ingredientId} |
			            Store ID: ${item.storeId} |
			            Type: ${item.transactionType} |
			            Qty: ${item.quantity}
			            <br><br>
			            <button onclick="editTransaction(${item.id}, ${item.ingredientId}, ${item.storeId}, '${item.transactionType}', ${item.quantity})">Update</button>
			            <button onclick="deleteTransaction(${item.id})">Delete</button>
			        </div>
			    `;
			});
        }

        document.getElementById("transactionList").innerHTML = output;
    })
    .catch(error => {
        console.log(error);
    });
}

function editTransaction(id, ingredientId, storeId, transactionType, quantity) {
    document.getElementById("transactionId").value = id;
    document.getElementById("transactionIngredientId").value = ingredientId;
    document.getElementById("transactionStoreId").value = storeId;
    document.getElementById("transactionType").value = transactionType;
    document.getElementById("transactionQuantity").value = quantity;
}

function deleteTransaction(id) {
    fetch(`${BASE_URL}/transactions/${id}`, {
        method: "DELETE"
    })
    .then(response => response.text())
    .then(data => {
        alert(data);
        getTransactions();
    });
}

function clearTransactionForm() {
    document.getElementById("transactionId").value = "";
    document.getElementById("transactionIngredientId").value = "";
    document.getElementById("transactionStoreId").value = "";
    document.getElementById("transactionType").value = "RECEIVE";
    document.getElementById("transactionQuantity").value = "";
}

// ================= DASHBOARD =================

function loadDashboard() {
    fetch(`${BASE_URL}/ingredients`)
    .then(response => response.json())
    .then(data => {
        if (document.getElementById("ingredientCount"))
            document.getElementById("ingredientCount").innerText = data.length;
    });

    fetch(`${BASE_URL}/transactions`)
    .then(response => response.json())
    .then(data => {
        if (document.getElementById("transactionCount"))
            document.getElementById("transactionCount").innerText = data.length;
    });

    fetch(`${BASE_URL}/ingredients`)
    .then(response => response.json())
    .then(data => {
        let lowStock = 0;
        data.forEach(item => {
            if (item.quantity < item.parLevel) lowStock++;
        });

        if (document.getElementById("alertCount"))
            document.getElementById("alertCount").innerText = lowStock;
    });
}


function checkAlert() {
    const id = document.getElementById("alertIngredientId").value;

    fetch(`http://localhost:8080/ingredients/alert/${id}`)
    .then(response => response.text())
    .then(data => {
        document.getElementById("alertResult").innerHTML =
            `<h2>${data}</h2>`;
    })
    .catch(error => console.log(error));
}

window.onload = function () {
    if (document.getElementById("ingredientCount")) {
        loadDashboard();
    }
};