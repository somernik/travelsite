<!-- modal user update forms -->

<div id="firstName" class="modal">

    <div class="modal-content">
        <div class="row">
            <a href="#!" class="modal-action modal-close">
                <i class="material-icons right">close</i></a>
        </div>
        <form class="col s12 m6 offset-m3" action="<c:url value="updateUser"/>">
            <div class="row">
                <h5>Current First Name: ${user.firstName}</h5>
            </div>

            <div class="row">
                <div class="input-field col s12">
                    <input id="firstNameInput" type="text" name="firstName" class="validate">
                    <label for="firstNameInput">First Name</label>
                </div>
            </div>

            <button class="btn waves-effect waves-light" type="submit" name="action">Save Changes</button>

        </form>
    </div>

</div>

<div id="lastName" class="modal">

    <div class="modal-content">
        <div class="row">
            <a href="#!" class="modal-action modal-close">
                <i class="material-icons right">close</i></a>
        </div>
        <form class="col s12 m6 offset-m3" action="updateUser">
            <div class="row">
                <h5>Current Last Name: ${user.lastName}</h5>
            </div>

            <div class="row">
                <div class="input-field col s12">
                    <input id="lastNameInput" type="text" name="lastName" class="validate">
                    <label for="lastNameInput">New Last Name</label>
                </div>
            </div>

            <button class="btn waves-effect waves-light" type="submit" name="action">Save Changes</button>

        </form>
    </div>

</div>

<div id="userName" class="modal">

    <div class="modal-content">
        <div class="row">
            <a href="#!" class="modal-action modal-close">
                <i class="material-icons right">close</i></a>
        </div>
        <form class="col s12 m6 offset-m3" action="updateUser">
            <div class="row">
                <h5>Current UserName: ${user.userName}</h5>
            </div>

            <div class="row">
                <div class="input-field col s12">
                    <input id="userNameInput" type="text" name="userName" class="validate">
                    <label for="userNameInput">New UserName</label>
                </div>
            </div>

            <button class="btn waves-effect waves-light" type="submit" name="action">Save Changes</button>

        </form>
    </div>

</div>

<div id="email" class="modal">

    <div class="modal-content">
        <div class="row">
            <a href="#!" class="modal-action modal-close">
                <i class="material-icons right">close</i></a>
        </div>
        <form class="col s12 m6 offset-m3" action="updateUser">
            <div class="row">
                <h5>Current Email: ${user.email}</h5>
            </div>

            <div class="row">
                <div class="input-field col s12">
                    <input id="emailInput" type="text" name="emil" class="validate">
                    <label for="emailInput">New Email</label>
                </div>
            </div>

            <button class="btn waves-effect waves-light" type="submit" name="action">Save Changes</button>

        </form>
    </div>

</div>

<div id="passwordUpdate" class="modal">

    <div class="modal-content">
        <div class="row">
            <a href="#!" class="modal-action modal-close">
                <i class="material-icons right">close</i></a>
        </div>
        <form class="col s12 m6 offset-m3" action="updateUser">

            <div class="row">
                <div class="input-field col s12">
                    <input id="passwordInput" type="text" name="password" class="validate">
                    <label for="passwordInput">New Password</label>
                </div>

                <div class="input-field col s12">
                    <input id="passwordConfirmInput" type="text" name="passwordConfirm" class="validate">
                    <label for="passwordConfirmInput">New Password Confirm</label>
                </div>
            </div>

            <div class="row">
                <div class="input-field col s12">
                    <input id="oldPasswordInput" type="text" name="oldPassword" class="validate">
                    <label for="oldPasswordInput">Old Password</label>
                </div>
            </div>

            <button class="btn waves-effect waves-light" type="submit" name="action">Save Changes</button>

        </form>
    </div>

</div>