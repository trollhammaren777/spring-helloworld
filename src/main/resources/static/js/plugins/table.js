$.tableOfUsers = function(users) {
    let bodyOfTableElement = document.querySelector('#users-table-body')
    bodyOfTableElement.innerHTML = null
    let tableRecords
    if (users.length === 0) {
        tableRecords = document.createElement('tr')
        tableRecords.insertAdjacentHTML('afterBegin', `
               <td class="text-center" colspan="3">No records about users found.</td>
        `)
    } else {
        for (let i = 0; i < users.length; i++) {
            let rolesString = []
            for (let key in users[i].roles) {
                rolesString.push(users[i].roles[key]['roleName'])
            }
            tableRecords = document.createElement('tr')
            tableRecords.insertAdjacentHTML('afterBegin',`
                <td class="td-table" th:text="${users[i].id}">${users[i].id}</td>
                <td class="td-table" th:text="${users[i].firstName}">${users[i].firstName}</td>
                <td class="td-table" th:text="${users[i].lastName}">${users[i].lastName}</td>
                <td class="td-table" th:text="${users[i].age}">${users[i].age}</td>
                <td class="td-table" th:text="${users[i].email}">${users[i].email}</td>
                <td class="td-table" th:text="${users[i].role}">${rolesString}</td>
                <td class="td-table">
                  <button type="button" class="btn btn-info"
                          data-bs-toggle="modal"
                          data-bs-target="#edit-user-js"
                          th:attrappend="data-bs-target=${i}" th:id="${users[i].nickname}" data-btn="edit with js" id="edit-${users[i].nickname}"
                          onclick="openEditOrDeleteUserModalWindow(this, 'edit')"
                  >Edit
                  </button>
                </td>
                <td class="td-table">
                  <button type="button" class="btn btn-danger"
                          data-bs-toggle="modal"
                          data-bs-target="#delete-user"
                          th:attrappend="data-bs-target=${i}" data-btn="delete with js" th:id="${users[i].nickname}" id="delete-${users[i].nickname}"
                          onclick="openEditOrDeleteUserModalWindow(this, 'delete')">Delete
                  </button>
                </td>  
            `)
            bodyOfTableElement.appendChild(tableRecords)
        }
    }
    return bodyOfTableElement
}