let products = [{ name: "", price: 0, quantity: 1 }];

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

// You can add functionality here to send the invoice data to the Spring Boot backend
