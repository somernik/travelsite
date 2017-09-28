<%@include file="templates/taglib.jsp" %>
<c:set var = "title" value = "WWW Travel | Admin"/>
<html>
<%@include file="templates/head.jsp" %>
<body>
<%@include file="templates/nav.jsp" %>

<div id="index-banner">
    <div class="section no-pad-bot">
        <div class="container">
<h2>Admin</h2>
<div id="search">
    <h3>Search Users</h3>
    <form action="/searchUser">

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

    <h3>Grant Privileges</h3>
    <form class="col s12 m6 offset-m3" action="/updateUser">
        <div class="row">
            <div class="input-field col s12">
                <input id="id" type="text" name="id" class="validate">
                <label for="id">User Id</label>
            </div>

            <div class="input-field col s12">
                <input id="username" type="text" name="username" class="validate">
                <label for="username">Username</label>
            </div>

            <div class="input-field col s12">
                <input id="newUsername" type="text" name="newUsername" class="validate">
                <label for="newUsername">New Username</label>
            </div>
        </div>

        <input type="submit" value="Save Changes" />
        <label for="admin">Grant Admin Privileges</label>
        <input type="checkbox" id="admin" name="admin" style="opacity: initial; pointer-events: auto"  />
    </form>

    <h3>Delete Account</h3>
    <form class="col s12 m6 offset-m3" action="/deleteUser">
        <div class="row">
            <div class="input-field col s12">
                <input id="id" type="text" name="id" class="validate">
                <label for="id">User Id</label>
            </div>
        </div>

        <input type="submit" value="Delete Account" />
    </form>

</div>
        </div></div></div>
</body>
</html>