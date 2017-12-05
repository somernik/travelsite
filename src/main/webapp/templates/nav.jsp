<nav class="teal darken-5" role="navigation">
    <div class="nav-wrapper container">
        <ul class="left hide-on-med-and-down">
            <li><a href="explore" class="iconLinks"><i class="material-icons teal">search</i></a></li>
        </ul>
        <a href="index.jsp" class="brand-logo center white-text">WWW Travel</a>

        <!-- Dropdown Structure -->
        <ul id="userOptions" class="dropdown-content">
            <li><a href="user.jsp#favorites">favorites</a></li>
            <li><a href="user.jsp#reviews">reviews</a></li>
            <li><a href="user.jsp#profile">profile</a></li>
            <li class="divider"></li>
            <li><a href="logout">logout</a></li>
        </ul>
        <ul class="right hide-on-med-and-down">
            <c:if test="${empty user}">
                <li><a class="waves-effect waves-light btn-flat teal modal-trigger" href="#signup">Sign Up</a></li>
                <li><a class="waves-effect waves-light btn-flat teal" href="login">Login</a><li>
            </c:if>
            <c:if test="${not empty user}">
                <li>${user.userName}<a class="dropdown-button iconLinks btn-flat" href="#!" data-activates="userOptions"><i class="material-icons teal">more_vert</i></a><li>
            </c:if>

        </ul>

        <ul id="nav-mobile" class="side-nav">
            <li><a href="explore"><i class="material-icons">search</i>Search</a></li>
            <c:if test="${empty user}">
                <li><a class="modal-trigger" href="#signup">Sign Up</a></li>
                <li><a href="login">Login</a><li>
            </c:if>
            <c:if test="${not empty user}">
                <li class="divider"></li>
                <li><a href="user.jsp#favorites">${user.userName}'s favorites</a></li>
                <li><a href="user.jsp#reviews">${user.userName}'s reviews</a></li>
                <li><a href="user.jsp#profile">${user.userName}'s profile</a></li>
                <li class="divider"></li>
            <li><a href="logout">logout</a></li>
            </c:if>
        </ul>
        <a href="#" data-activates="nav-mobile" class="button-collapse iconLinks"><i class="material-icons teal">menu</i></a>
    </div>


</nav>
<!-- TODO add to css page
below css used to make forms in modals look nice with spacing and no scroll bar
-->
<style>
    form {
        color: black;
        margin-bottom: 0;
    }
    .modal-content .row {
        margin-bottom: 0;
    }

    .modal {
        max-height: 84%;
    }

    .iconLinks {
        color: white;
    }

</style>
<%@include file="form_modals.jsp" %>