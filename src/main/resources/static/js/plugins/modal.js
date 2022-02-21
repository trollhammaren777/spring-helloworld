Element.prototype.appendAfter = function(element) {
    element.parentNode.insertBefore(this, element.nextSibling)
}

function noop() {

}

function _createModalFooter(buttons = []) {
    if (buttons.length === 0) {
        return document.createElement('div')
    }
    const wrap = document.createElement('div')
    wrap.classList.add('modal-footer')
    buttons.forEach(btn => {
        const $btn = document.createElement('button')
        $btn.textContent = btn.text
        $btn.classList.add(`${btn.type}`)
        $btn.classList.add('btn')
        $btn.classList.add(`btn-${btn.class || 'secondary'}`)
        $btn.onclick = btn.handler || noop
        wrap.appendChild($btn)
    })
    return wrap
}

function _createModal(options) {
    const DEFAULT_WIDTH = '600px'

    const modal = document.createElement('div')
    modal.classList.add('mmodal')

    if (options.editOrDelete === 'edit') {
        modal.insertAdjacentHTML('afterBegin', /*html*/`     
               <div class="modal-overlay" data-close="true">
                 <div class="modal-dialog modal-dialog-centered" role="document">                  
                   <div class="modal-window" style="width: ${options.width || DEFAULT_WIDTH}">
                     <div class="modal-content">
                      <div class="modal-header">
                         <h5 class="modal-title" id="exampleModalLongTitleEditUser">${options.title || 'Окно'}</h5>
                         ${options.closable ? /*html*/`
                             <button type="button" class="close" data-bs-dismiss="modal" aria-label="Close" data-close>
                                           <span class="modal-close" data-close="true" aria-hidden="false">&times;</span>
                             </button>` : ''}
                      </div>
                      <div class="modal-body" data-content>
                        <form name="editUserInfoForm" class="align-items-center" role="form">
                          <fieldset>
                            <div class="form-group col-md-6">
                              <label class="label-form" for="id">ID</label>
                              <input type="text" class="form-control"
                                     id="id" name="id" value="${options.id}" readonly>
                            </div>
                            <div class="form-group col-md-6">
                              <label class="label-form" for="first_name">First name</label>
                              <input type="text" class="form-control"
                                     th:field="firstName"
                                     name="firstName" value="${options.firstName}">
                            </div>
                            <div class="form-group col-md-6">
                              <label class="label-form" for="last_name">Last name</label>
                              <input type="text" class="form-control"
                                     id="last_name" name="lastName" value="${options.lastName}">
                            </div>
                            <div class="form-group col-md-6">
                              <label class="label-form" for="nickname">Nickname</label>
                              <input type="text" class="form-control"
                                     id="nickname" name="nickname" value="${options.nickname}">
                            </div>
                            <div class="form-group col-md-6">
                              <label class="label-form" for="age">Age</label>
                              <input type="number" min="0" max="130" step="1" class="form-control"
                                     id="age" name="age" value="${options.age}">
                            </div>
                            <div class="form-group col-md-6">
                              <label class="label-form" for="email">Email</label>
                              <input type="text" class="form-control"
                                     id="email" name="email" value="${options.email}">
                            </div>
                            <div class="form-group col-md-6">
                              <label class="label-form" for="password">Password</label>
                              <input type="password" class="form-control"
                                     id="password" name="password" value="${options.password}">
                            </div>
                            <div class="form-group col-md-6">
                              <label class="label-form" for="new_role">Role</label>
                              <select multiple class="form-select" size="3" id="new_role" required
                                      name="userRole">
                                <option value="USER">USER</option>
                                <option value="ADMIN">ADMIN</option>
                                <option value="RESET_ROLES">RESET ROLES</option>
                              </select>
                            </div>
                          </fieldset>
                        </form>
                      </div>
                   </div>
                 </div>
               </div>`)
    } else if (options.editOrDelete === 'delete') {
        modal.insertAdjacentHTML('afterBegin', /*html*/`
               <div class="modal-overlay" data-close="true">
                 <div class="modal-dialog modal-dialog-centered" role="document">                  
                   <div class="modal-window" style="width: ${options.width || DEFAULT_WIDTH}">
                     <div class="modal-content">
                      <div class="modal-header">
                         <h5 class="modal-title" id="exampleModalLongTitleEditUser">${options.title || 'Окно'}</h5>
                         ${options.closable ? /*html*/`
                             <button type="button" class="close" data-bs-dismiss="modal" aria-label="Close" data-close>
                                           <span class="modal-close" data-close="true" aria-hidden="false">&times;</span>
                             </button>` : ''}
                      </div>
                      <div class="modal-body" data-content>
                        <form class="align-items-center" role="form" action="#">
                          <fieldset>
                            <div class="form-group col-md-6">
                              <label class="label-form" for="id">ID</label>
                              <input type="text" class="form-control"
                                     id="id" value="${options.id}" readonly>
                            </div>
                            <div class="form-group col-md-6">
                              <label class="label-form" for="first_name">First name</label>
                              <input type="text" class="form-control"
                                     id="first_name" value="${options.firstName}" readonly>
                            </div>
                            <div class="form-group col-md-6">
                              <label class="label-form" for="last_name">Last name</label>
                              <input type="text" class="form-control"
                                     id="last_name" value="${options.lastName}" readonly>
                            </div>
                            <div class="form-group col-md-6">
                              <label class="label-form" for="nickname">Nickname</label>
                              <input type="text" class="form-control"
                                     id="nickname" value="${options.nickname}" readonly>
                            </div>
                            <div class="form-group col-md-6">
                              <label class="label-form" for="age">Age</label>
                              <input type="number" min="0" max="130" step="1" class="form-control"
                                     id="age" value="${options.age}" readonly>
                            </div>
                            <div class="form-group col-md-6">
                              <label class="label-form" for="email">Email</label>
                              <input type="text" class="form-control"
                                     id="email" value="${options.email}" readonly>
                            </div>
                          </fieldset>
                        </form>
                      </div>
                   </div>
                 </div>
               </div>`)
    }
    const footer = _createModalFooter(options.footerButtons)
    footer.appendAfter(modal.querySelector('[data-content]'))
    document.body.appendChild(modal)
    return modal
}

$.modal = function(options) {
    const $modal = _createModal(options)
    const ANIMATION_SPEED = 200
    let closing = false
    let destroyed = false

    const modal = {
        open() {
            if (destroyed) {
                return console.log('Modal is destroyed')
            }
            !closing && $modal.classList.add('open')
        },
        close() {
            closing = true
            $modal.classList.remove('open')
            $modal.classList.add('hide')
            setTimeout(() => {
                $modal.classList.remove('hide')
                closing = false
                if (typeof options.onClose === 'function') {
                    options.onClose()
                }
            }, ANIMATION_SPEED)
        },
    }

    const listener = event => {
        if (event.target.dataset.close) {
            modal.close()
        }
    }
    $modal.addEventListener('click', listener)

    return Object.assign(modal, {
        destroy() {
            $modal.parentNode.removeChild($modal)
            $modal.removeEventListener('click', listener)
            destroyed = true
        },
        setContent(html) {
            $modal.querySelector('[data-content]').innerHTML = html
        }
    })
}