<%--
  Created by IntelliJ IDEA.
  User: sarah
  Date: 10/3/2017
  Time: 1:55 PM
  To change this template use File | Settings | File Templates.
--%>
<%@include file="templates/taglib.jsp" %>
<c:set var = "title" value = "WWW Travel | Login Error"/>
<html>
<%@include file="templates/head.jsp" %>
<style>
    #errorContent {
        width: 50%;
        margin: auto;
        font-align: center;
    }
</style>
<body>
<%@include file="templates/nav.jsp" %>
    <div id="errorContent">
        <h3>Your username or password was incorrect.</h3>
        <p>Please try again <a href="login">here</a> or <a class="modal-trigger" href="#signup">sign up</a> for an account.</p>
    </div>
</body>
</html>