<%@include file="../templates/taglib.jsp" %>
<c:set var = "title" value = "WWW Travel | Admin"/>
<html>
<%@include file="../templates/head.jsp" %>
<body>
<%@include file="../templates/nav.jsp" %>

<!--<img src="images/admin_background.jpeg" />-->
<style>

    .tabs .tab a, .tabs .tab.disabled a {
        color: #26a69a;
    }

    .tabs .indicator {
        background-color: #26a69a;
    }

    .tabs {
        box-shadow: grey 2px 2px 2px;
    }

    .tabs .tab a:hover, .tabs .tab a.active {
        color: #26a69a;
    }

    form .in_table {
        margin: 0;
    }

    img {
        opacity : 0.25;
        position: absolute;
        width: 100%;
    }
    #addadmin, table {
        background-color:darkslategray;
        width: 100%;
    }
    #formDiv {
        width: 70%;
        margin: 1em auto;
    }

    #grantFormInput1 {
        margin-left: 15%;
    }
    #grantFormInput2 {
        margin-right: 15%;
    }

    form p, table {
        color: white;
    }


    .formHeader {
        text-align: center;
    }

    #tabsFilters {
        margin-bottom: 1em;
    }

    #filters div.row {
        margin:0;
    }

    body {
        background-image: url("images/admin_background.jpeg");
        background-size: cover;
    }

</style>
<div class="container">
    <div class="section no-pad-bot" id="adminOptions">

        <div id="filters">
            <div class="row">
                <div class="col s12" id="tabsFilters">
                    <ul class="tabs">
                        <li class="tab col s3"><a href="#search">Search Users</a></li>
                        <li class="tab col s3"><a href="#privileges">Privileges</a></li>
                        <li class="tab col s3"><a href="#reported">Reported Items</a></li>
                        <li class="tab col s3"><a href="#other">Other Things</a></li>
                    </ul>
                </div>
                <div id="search">
                    <form action="searchUser">
                        <label for="searchType">Search Type</label>
                        <select id="searchType" name="searchType" title="searchType">
                            <option value="id">ID</option>
                            <option value="f_name">First Name</option>
                            <option value="l_name">Last Name</option>
                        </select>
                        <label for="searchOperator">searchOperator</label>
                        <select id="searchOperator" name="searchOperator">
                            <option value="=">=</option>
                            <option value="LIKE">contains</option>
                        </select>
                        <label for="searchValue">searchValue</label>
                        <input name="searchValue" id="searchValue" type="text" required />
                        <input type="submit" value="Search" />
                    </form>

                    <c:if test="${empty users}"><p>Enter an id to search!</p></c:if>
                    <c:if test="${not empty users}">
                    <div class="container-fluid">
                        <h5>Search: ${type} ${operator} ${value} <c:if test="${empty value}">all</c:if></h5>
                        <h5>Search Results: </h5>
                        <c:if test="${empty users}"><p>No Results!</p></c:if>
                        <table>
                            <tr><th>Id</th><th>First Name</th><th>Last Name</th></tr>
                            <c:forEach var="user" items="${users}">
                                <tr><td>${user.userid}</td><td>${user.firstName}</td><td>${user.lastName}</td></tr>
                            </c:forEach>
                        </table>
                    </div>
                    </c:if>
                </div>
                <div id="privileges">

                    <div class="container-fluid">
                        <c:if test="${empty adminUsers}"><p>No Admin Users!</p></c:if>
                        <c:if test="${not empty adminUsers}">
                        <table>
                            <tr><th>Username</th><th>First Name</th><th>Last Name</th><th>Privileges</th></tr>
                            <c:forEach var="user" items="${adminUsers}">
                                <tr><td>${user.userName}</td><td>${user.firstName}</td><td>${user.lastName}</td><td>
                                    <c:forEach var="priv" items="${user.userPrivileges}">
                                        ${priv.pk.privilege.value}
                                    </c:forEach>
                                    <form class="in_table" action="removeAdmin">

                                        <input type="hidden" name="removeId" value="${user.userid}"/>
                                        <button type="submit" class="btn waves-effect waves-light" value="Remove Admin">Remove Admin</button>
                                    </form>

                                </td></tr>
                            </c:forEach>
                        </table>
                        </c:if>
                    </div>
                    <div id="formDiv">
                    <form class="col s12 m6" action="addAdmin" id="addadmin">
                        <h5 class="formHeader">Grant Admin</h5>
                        <!--<div class="row">-->
                            <div class="input-field col s4" id="grantFormInput1">
                                <input id="username" type="text" name="username" class="validate">
                                <label for="username">Username</label>
                            </div>
                            <div class="col s4" id="grantFormInput2">
                            <button class="btn waves-effect waves-light" type="submit" name="grant">Grant Admin</button>
                            </div>
                        <!--</div>-->


                    </form>
                    </div>
                </div>
                <div id="reported">
                    <p>list of reported reviews, images, users?</p>
                    <p>Delete Account</p><!-- TODO -->
                    <form class="col s12 m6 offset-m3" action="deleteUser">
                        <div class="row">
                            <div class="input-field col s12">
                                <input id="id" type="text" name="id" class="validate">
                                <label for="id">User Id</label>
                            </div>
                        </div>

                        <button type="submit" class="btn waves-effect waves-light" value="Delete Account">Delete Account</button>
                    </form>

                </div>
                <div id="other">
                    <p>nothing here yet</p>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>