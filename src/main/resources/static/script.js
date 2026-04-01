let currentPage = 0;
const pageSize = 10;

function fetchUsers(page) {
    fetch(`/user?page=${page}&size=${pageSize}`)
        .then(response => response.json())
        .then(data => {
            const tbody = document.getElementById('userTableBody');
            tbody.innerHTML = '';

            if (data.content && data.content.length > 0) {
                data.content.forEach(user => {
                    const row = `<tr>
                            <td>${user.name}</td>
                            <td>${user.lastName}</td>
                            <td>${user.age}</td>
                            <td class="server-ip">${user.serverIp}</td>
                            <td class="server-info">${user.serverMark}</td>
                        </tr>`;
                    tbody.innerHTML += row;
                });
            } else {
                tbody.innerHTML = '<tr><td colspan="5" style="text-align:center;">No hay usuarios registrados.</td></tr>';
            }

            document.getElementById('pageInfo').innerText = `Página ${data.number + 1} de ${data.totalPages || 1}`;
            document.getElementById('btnPrev').disabled = data.first || data.totalPages === 0;
            document.getElementById('btnNext').disabled = data.last || data.totalPages === 0;
        })
        .catch(error => console.error('Error al obtener usuarios:', error));
}

// 2. FUNCIÓN PARA CAMBIAR DE PÁGINA
function changePage(direction) {
    currentPage += direction;
    fetchUsers(currentPage);
}

// 3. FUNCIÓN PARA CREAR UN NUEVO USUARIO (POST)
function createUser(event) {
    event.preventDefault(); // Evita que la página se recargue al enviar el formulario

    // Construir el objeto JSON con los datos del formulario
    const newUser = {
        name: document.getElementById('name').value,
        lastName: document.getElementById('lastName').value,
        age: parseInt(document.getElementById('age').value)
    };

    // Enviar la petición POST al backend
    fetch('/user', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(newUser)
    })
        .then(response => {
            if (response.ok) {
                alert('¡Usuario creado con éxito!');
                document.getElementById('userForm').reset(); // Limpiar el formulario
                currentPage = 0; // Volver a la página 1
                fetchUsers(currentPage); // Recargar la tabla para ver el nuevo usuario
            } else {
                alert('Error al crear el usuario. Revisa los logs de la consola.');
            }
        })
        .catch(error => console.error('Error en la petición POST:', error));
}

// 4. INICIALIZAR LA PÁGINA
window.onload = () => fetchUsers(0);

