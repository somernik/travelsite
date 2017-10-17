<%@include file="../templates/taglib.jsp" %>
<c:set var = "title" value = "WWW Travel | Admin"/>
<html>
<%@include file="../templates/head.jsp" %>
<body>
<%@include file="../templates/nav.jsp" %>
<style>

    .tabs .tab a, .tabs .tab.disabled a {
        color: #26a69a;
    }

    .tabs .indicator {
        background-color: #26a69a;
    }

    .tabs .tab a:hover, .tabs .tab a.active {
        color: #26a69a;
    }

    form .in_table {
        margin: 0px;
    }
</style>
<div class="container">
    <div class="section no-pad-bot" id="adminOptions">

        <div id="filters">
            <div class="row">
                <div class="col s12">
                    <ul class="tabs">
                        <li class="tab col s3"><a href="#search">Search Users</a></li>
                        <li class="tab col s3"><a href="#privileges">Privileges</a></li>
                        <li class="tab col s3"><a href="#reported">Reported Items</a></li>
                        <li class="tab col s3"><a href="#other">Other Things</a></li>
                    </ul>
                </div>
                <div id="search">
                    <form action="searchUser">

                        <select id="searchType" name="searchType">
                            <option value="id">ID</option>
                            <option value="f_name">First Name</option>
                            <option value="l_name">Last Name</option>
                        </select>
                        <select id="searchOperator" name="searchOperator">
                            <option value="=">=</option>
                            <option value="LIKE">contains</option>
                        </select>
                        <input name="searchValue" id="searchValue" type="text" required />
                        <input type="submit" value="Search" />
                    </form>
                    <a href = "searchUser">See all users</a>
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

                                        <input type="hidden" value="${user.userid}"/>
                                        <button type="submit" class="btn waves-effect waves-light" value="Remove Admin">Remove Admin</button>
                                    </form>

                                </td></tr>
                            </c:forEach>
                        </table>
                        </c:if>
                    </div>

                    <form class="col s12 m6" action="addAdmin">
                        <p>Grant Admin</p>
                        <div class="row">
                            <div class="input-field col s4">
                                <input id="username" type="text" name="username" class="validate">
                                <label for="username">Username</label>
                            </div>
                            <div class="col s4">
                            <button class="btn waves-effect waves-light" type="submit" name="grant">Grant Admin</button>
                            </div>
                        </div>


                    </form>
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