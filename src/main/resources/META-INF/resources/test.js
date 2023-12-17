document.addEventListener('DOMContentLoaded', () => {
    getAvions();
    getPassagers();
    getReservations();
    getVols();
});

function getAvions() {
    fetch('http://localhost:8080/planes') // Replace '/planes' with the correct endpoint for Avions
        .then(response => response.json())
        .then(data => updateTable('avionsTableBody', data));
}

function getPassagers() {
    fetch('http://localhost:8080/passengers') // Replace '/passengers' with the correct endpoint for Passagers
        .then(response => response.json())
        .then(data => updateTable('passagersTableBody', data));
}

function getReservations() {
    fetch('http://localhost:8080/reservations') // Replace '/reservations' with the correct endpoint for Reservations
        .then(response => response.json())
        .then(data => updateTable('reservationsTableBody', data));
}

function getVols() {
    fetch('http://localhost:8080/flights') // Replace '/flights' with the correct endpoint for Vols
        .then(response => response.json())
        .then(data => updateTable('volsTableBody', data));
}

function updateTable(tableId, data) {
    const tableBody = document.getElementById(tableId);

    // Clear existing rows
    tableBody.innerHTML = '';

    // Populate the table with data
    data.forEach(item => {
        const row = document.createElement('tr');
        Object.values(item).forEach(value => {
            const cell = document.createElement('td');
            cell.textContent = value;
            row.appendChild(cell);
        });
        tableBody.appendChild(row);
    });
}
