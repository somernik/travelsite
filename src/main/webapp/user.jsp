<%@include file="templates/taglib.jsp" %>
<c:set var = "title" value = "WWW Travel"/>
<html>
<%@include file="templates/head.jsp" %>
<body>
<%@include file="templates/nav.jsp" %>

<div id="index-banner">
    <div class="section no-pad-bot">
        <div class="container">
            <div class="row">
                <div class="col s12">
                    <ul class="tabs">
                        <li class="tab col s3"><a href="#test1">Profile</a></li>
                        <li class="tab col s3"><a href="#test2">Reviews</a></li>
                        <li class="tab col s3"><a href="#test3">Photos</a></li>
                        <li class="tab col s3"><a href="#test4">Favorites</a></li>

                    </ul>
                </div>
                <div id="test1" class="col s12">
                    <h5>User Info</h5>
                    <br />

                    <p>Fist Name: ${user.firstName}</p>
                    <a class="waves-effect waves-light btn modal-trigger" href="#firstName">Edit</a>
                    <br />

                    <p>Last Name: ${user.lastName}</p>
                    <a class="waves-effect waves-light btn modal-trigger" href="#lastName">Edit</a>
                    <br />

                    <p>Email: ${user.email}</p>
                    <a class="waves-effect waves-light btn modal-trigger" href="#email">Edit</a>
                    <br />

                    <p>Username: ${user.userName}</p>
                    <a class="waves-effect waves-light btn modal-trigger" href="#userName">Edit</a>
                    <br />
                    <a class="waves-effect waves-light btn modal-trigger" href="#password">Change Password</a>

                    <h3>Delete Account</h3>
                    <form class="col s12 m6 offset-m3" action="/deleteUser">
                        <div class="row">
                            <div class="input-field col s12">
                                <input id="id" type="text" value="" name="id" class="validate">
                                <label for="id">User Id</label>
                            </div>
                        </div>
                        <button class="btn waves-effect waves-light" type="submit" name="action">Delete Account</button>

                    </form>
                </div>
                <div id="test2" class="col s12">Test 2</div>
                <div id="test3" class="col s12">Test 3</div>
                <div id="test4" class="col s12">Test 4</div>
            </div>


            <!-- modal forms -->

            <div id="firstName" class="modal">

                <div class="modal-content">
                    <div class="row">
                        <a href="#!" class="modal-action modal-close">
                            <i class="material-icons right">close</i></a>
                    </div>
                    <form class="col s12 m6 offset-m3" action="/updateUser">
                        <div class="row">
                            <h5>Current First Name: {user.firstName}</h5>
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
                    <form class="col s12 m6 offset-m3" action="/updateUser">
                        <div class="row">
                            <h5>Current Last Name: {user.lastName}</h5>
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
                    <form class="col s12 m6 offset-m3" action="/updateUser">
                        <div class="row">
                            <h5>Current UserName: {user.userName}</h5>
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
                    <form class="col s12 m6 offset-m3" action="/updateUser">
                        <div class="row">
                            <h5>Current Email: {user.email}</h5>
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

            <div id="password" class="modal">

                <div class="modal-content">
                    <div class="row">
                        <a href="#!" class="modal-action modal-close">
                            <i class="material-icons right">close</i></a>
                    </div>
                    <form class="col s12 m6 offset-m3" action="/updateUser">

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

            <%@include file="templates/form_modals.jsp" %>
            <!-- end of modal forms -->
        </div>
    </div>
</div>


<footer class="page-footer teal">
    <div class="container">
        <div class="row">
            <div class="col l6 s12">
                <h5 class="white-text">Company Bio</h5>
                <p class="grey-text text-lighten-4">We are a team of college students working on this project like it's our full time job. Any amount would help support and continue development on this project and is greatly appreciated.</p>


            </div>
            <div class="col l3 s12">
                <h5 class="white-text">Settings</h5>
                <ul>
                    <li><a class="white-text" href="#!">Link 1</a></li>
                    <li><a class="white-text" href="#!">Link 2</a></li>
                    <li><a class="white-text" href="#!">Link 3</a></li>
                    <li><a class="white-text" href="#!">Link 4</a></li>
                </ul>
            </div>
            <div class="col l3 s12">
                <h5 class="white-text">Connect</h5>
                <ul>
                    <li><a class="white-text" href="#!">Link 1</a></li>
                    <li><a class="white-text" href="#!">Link 2</a></li>
                    <li><a class="white-text" href="#!">Link 3</a></li>
                    <li><a class="white-text" href="#!">Link 4</a></li>
                </ul>
            </div>
        </div>
    </div>
    <div class="footer-copyright">
        <div class="container">
            Made by <a class="brown-text text-lighten-3" href="http://materializecss.com">Materialize</a>
        </div>
    </div>
</footer>

</body>
</html>
<!-- style for side nav TODO make disabled tab & indicator color grayed out -->
<style>
    #slide-out {
        margin-top: 64px;
    }

    @media only screen and (max-width : 992px) {
        #slide-out {
            margin-top: 0px;
        }
    }

    #not_side_nav {
        margin-left: 300px;
    }

    @media only screen and (max-width : 992px) {
        #not_side_nav {
            margin-left: 0px;
        }
    }

    .tabs .tab a, .tabs .tab.disabled a {
        color: #26a69a;
    }

    .tabs .indicator {
        background-color: #26a69a;
    }

    .tabs .tab a:hover, .tabs .tab a.active {
        color: #26a69a;
    }

    #filterDiv {
        padding-top: 3px;
    }

</style>
