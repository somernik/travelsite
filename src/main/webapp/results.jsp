<%@include file="templates/taglib.jsp"%>
<%@include file="templates/head.jsp"%>

<html>
<style>
    table {
        border-collapse: collapse;
        width: 50%;
    }

    th, td {
        padding: 8px;
        text-align: left;
        border-bottom: 1px solid #ddd;
    }
    th {
        background-color: #4CAF50;
        color: white;
    }
    tr:hover{background-color:#f5f5f5}
</style>
<body>

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
    <p><a href="<c:url value="/" />">Search Again!</a></p>
</div>

</body>
</html>
