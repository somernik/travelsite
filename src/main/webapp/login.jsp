<%--
  Created by IntelliJ IDEA.
  User: sarah
  Date: 10/22/2017
  Time: 8:16 PM
  To change this template use File | Settings | File Templates.
--%>
<%@include file="templates/taglib.jsp" %>
<c:set var = "title" value = "WWW Travel"/>
<html>
<%@include file="templates/head.jsp" %>
<body>
<%@include file="templates/nav.jsp" %>

<div id="index-banner" class="parallax-container">
    <div class="section no-pad-bot">
        <div class="container">
            <form class="col s12 m6 offset-m3" ACTION="j_security_check" METHOD="POST">
                <div class="row">
                    <div class="input-field col s12">
                        <input id="username" type="text" name="j_username" class="validate">
                        <label for="username">Username</label>
                    </div>
                </div>

                <div class="row">
                    <div class="input-field col s12">
                        <input id="password" type="password" name="j_password" class="validate">
                        <label for="password">Password</label>
                    </div>
                </div>

                <button class="btn waves-effect waves-light" type="" onclick="document.getElementById('login').style.display='none'">Cancel</button>
                <button class="btn waves-effect waves-light" type="submit" name="action">Login</button>

            </form>
        </div>
    </div>
</div>
</body>
</html>
