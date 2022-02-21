// users table initialization
sendRequest('GET', 'users/all')
    .then(data => addRecordsToTable(data))
    .catch(err => console.error(err))

function openEditOrDeleteUserModalWindow(elem, editOrDelete) {
    let stringElemId = elem.id.toString().substring(elem.id.toString().indexOf('-')+1)
    const url = '/admin/getUser/' + stringElemId
    sendRequest('GET', url)
        .then((data) => createEditOrDeleteUserModalOnclick(data, editOrDelete))
        .then((editUserModal) => editUserModal.open())
        .catch((err) => console.error(err))
}

function createEditOrDeleteUserModalOnclick(data, editOrDelete) {
    let footerButtons
    let title
    if (editOrDelete === 'edit') {
        title = 'Edit user'
        footerButtons = [
            {text: 'Close', type: "button", class: 'secondary', handler() {
                    userModal.close()
            }},
            {text: 'Edit', type:"submit", class: 'primary', handler() {
                    editUser('users/' + data.nickname, getUserDataFromForm('edit'))
                    userModal.close()
            }}
        ]
    } else if (editOrDelete === 'delete') {
        title = 'Delete user'
        footerButtons = [
            {text: 'Close', type: "button", class: 'secondary', handler() {
                    userModal.close()
                }},
            {text: 'Delete', type: "submit", class: 'danger', handler() {
                    deleteUser(data.nickname)
                    userModal.close()
            }}
        ]
    }
    const userModal = $.modal({
        title: title,
        editOrDelete: editOrDelete,
        closable: true,
        onClose() {
            userModal.destroy()
        },
        footerButtons: footerButtons,
        nickname: data.nickname,
        id: data.id,
        firstName: data.firstName,
        lastName: data.lastName,
        age: data.age,
        email: data.email,
        password: data.password,
    })
    return userModal
}

function getUserDataFromForm(type) {
    let userDataFromForm
    if (type === 'edit') {
        userDataFromForm = new FormData(document.forms.editUserInfoForm)
    } else if (type === 'create') {
        userDataFromForm = new FormData(document.forms.newUserDataForm)
    }
    let userData = {}
    for (let pair of userDataFromForm.entries()) {
        userData[`${pair[0]}`] = pair[1]
    }
    return userData
}

function editUser(url, data) {
    sendRequest('PUT', url, data)
        .then(getTableRecords)
        .catch(err => console.error(err))
}

function addRecordsToTable (data) {
    return $.tableOfUsers(data)
}

function getTableRecords() {
    return sendRequest('GET', 'users/all')
        .then(data => addRecordsToTable(data))
        .catch(err => console.error(err))
}

function deleteUser(userNickname) {
    sendRequest('DELETE', 'users/' + userNickname)
        .then(getTableRecords)
        .catch(err => console.error(err))
}

let newUserForm = document.getElementById('button-new-user-form')
newUserForm.addEventListener("click", () => {
    createNewUser()
    document.getElementById("new-user-form").reset()
})

function createNewUser() {
    sendRequest('POST', "/admin/users", getUserDataFromForm('create'))
        .then(getTableRecords)
        .catch(err => console.error(err))
    let createNewUserTabPanFade = document.getElementById("create-new-user")
    createNewUserTabPanFade.classList.remove("active", "show")
    let navLinkCreateNewUser = document.getElementById("nav-link-create-new-user")
    navLinkCreateNewUser.classList.remove("active")
    let usersTableTabPanFade = document.getElementById("users-table")
    usersTableTabPanFade.classList.add("active", "show")
    let navLinkUsersTable = document.getElementById("nav-link-users-table")
    navLinkUsersTable.classList.add("active")
}

function sendRequest(method, url, body = null) {
    const headers = {
        'Content-Type': 'application/json',
        'X-CSRF-TOKEN': window.$('meta[name="_csrf"]').attr('content')
    }
    let init
    if (method === 'GET' || method === 'DELETE') {
        init = {
            method: method,
            headers: headers
        }
    } else if (method === 'POST' || method === 'PUT') {
        init = {
            method: method,
            body: JSON.stringify(body),
            headers: headers
        }
    }
    return fetch(url, init).then(response => {
        if (response.ok) {
            if (method === 'GET') {
                return response.json()
            } else if (method === 'POST' || method === 'PUT' || method === 'DELETE') {
                return response
            }
        }
        return response.json().then(error => {
            const e = new Error('Что-то пошло не так')
            e.data = error
            console.log(e.data)
            throw e
        })
    })
}