// You can add functionality here to send the invoice data to the Spring Boot backend
let products = [];

function addProduct() {
    const productBody = document.getElementById('productBody');
    const newRow = `
        <tr>
            <td><input type="text" placeholder="Product name" oninput="updateProduct(this, 'name')"></td>
            <td><input type="number" placeholder="Price" oninput="updateAmount(this)"></td>
            <td><input type="number" placeholder="Quantity" oninput="updateAmount(this)"></td>
            <td class="amount">$0.00</td>
            <td><button class="remove-btn" onclick="removeProduct(this)">Remove</button></td>
        </tr>
    `;
    productBody.insertAdjacentHTML('beforeend', newRow);
}

function removeProduct(button) {
    const row = button.parentElement.parentElement;
    row.remove();
    calculateTotal();
}

function updateAmount(input) {
    const row = input.parentElement.parentElement;
    const priceInput = row.children[1].children[0];
    const quantityInput = row.children[2].children[0];
    const amountCell = row.children[3];

    const price = parseFloat(priceInput.value) || 0;
    const quantity = parseInt(quantityInput.value) || 0;
    const amount = (price * quantity).toFixed(2);

    amountCell.textContent = `$${amount}`;
    calculateTotal();
}

function calculateTotal() {
    const amounts = document.querySelectorAll('.amount');
    let total = 0;

    amounts.forEach(amount => {
        const amountValue = parseFloat(amount.textContent.replace('$', '')) || 0;
        total += amountValue;
    });

    document.getElementById('totalAmount').textContent = `$${total.toFixed(2)}`;
}

function updateProduct(input, field) {
    const row = input.parentElement.parentElement;
    const productName = input.value;
    const priceInput = row.children[1].children[0];
    const quantityInput = row.children[2].children[0];

    const price = parseFloat(priceInput.value) || 0;
    const quantity = parseInt(quantityInput.value) || 1;

    // Update the products array
    const productIndex = Array.from(row.parentNode.children).indexOf(row);
    if (products[productIndex]) {
        products[productIndex] = { productName: productName, unitPrice: price, quantity: quantity };
    } else {
        products.push({ productName: productName, unitPrice: price, quantity: quantity });
    }
}

// function submitInvoice() {
//     const customerName = document.getElementById('customerName').value;
//     const customerPhone = document.getElementById('customerPhone').value;
//     const customerAddress = document.getElementById('customerAddress').value;
//     console.log(products)
//     const invoice = {
//         customer: {
//             name: customerName,
//             mobileNumber: customerPhone,
//             address: customerAddress,
//             email: '', // Add email input if needed
//         },
//         product: products // Filter out any empty products
//     };

//     // Sending the invoice data to the Spring Boot backend
//     fetch('/order/newBill', {
//         method: 'POST',
//         headers: {
//             'Content-Type': 'application/json'
//         },
//         body: JSON.stringify(invoice)
//     })
//     .then(response => response.text())
//     .then(data => {
//         alert(data); // Show response from the server
//         // Optionally, clear the form and reset the state
//         resetForm();
//     })
//     .catch(error => console.error('Error:', error));
// }

function submitInvoice() {
    const customerName = document.getElementById('customerName').value;
    const customerPhone = document.getElementById('customerPhone').value;
    const customerAddress = document.getElementById('customerAddress').value;
    
    // Ensure all products in the table are added to the products array
    const productRows = document.querySelectorAll('#productBody tr');
    products = []; // Reset the products array
    let productId = 1;
    productRows.forEach(row => {
        const productName = row.children[0].children[0].value;
        const price = parseFloat(row.children[1].children[0].value) || 0;
        const quantity = parseInt(row.children[2].children[0].value) || 0;
        if (productName && price && quantity) {
            products.push({
                productId: productId++,
                productName: productName,
                unitPrice: price,
                quantity: quantity
            });
        }
    });

    // Prepare the invoice object
    const invoice = {
        customer: {
            name: customerName,
            mobileNumber: customerPhone,
            address: customerAddress,
            email: '' // Add email input if needed
        },
        product: products // This now includes all products
    };

    // Send the invoice data to the Spring Boot backend
    fetch('/order/newBill', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(invoice)
    })
    .then(response => response.text())
    .then(data => {
        alert(data); // Show response from the server
        // Optionally, clear the form and reset the state
        resetForm();
    })
    .catch(error => console.error('Error:', error));
}


function resetForm() {
    // Clear the input fields
    document.getElementById('customerName').value = '';
    document.getElementById('customerPhone').value = '';
    document.getElementById('customerAddress').value = '';
    products = []; // Clear the products array
    document.getElementById('productBody').innerHTML = `
        <tr>
            <td><input type="text" placeholder="Product name" oninput="updateProduct(this, 'name')"></td>
            <td><input type="number" placeholder="Price" oninput="updateAmount(this)"></td>
            <td><input type="number" placeholder="Quantity" oninput="updateAmount(this)"></td>
            <td class="amount">$0.00</td>
            <td><button class="remove-btn" onclick="removeProduct(this)">Remove</button></td>
        </tr>
    `; // Reset the product table
    calculateTotal();
}
