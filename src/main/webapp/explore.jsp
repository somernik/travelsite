<%@include file="templates/taglib.jsp" %>
<c:set var = "title" value = "WWW Travel"/>
<html>
<%@include file="templates/head.jsp" %>
<body>
<%@include file="templates/nav.jsp" %>

<div id="index-banner" class="parallax-container">
    <div class="section no-pad-bot">
        <div class="container">
            <br><br>
            <a class="waves-effect waves-light btn modal-trigger" href="#new_review">New Review</a>
            <!--
            <div id="new_review" class="modal">
                <div class="modal-footer">
                    <a href="#!" class="modal-action modal-close waves-effect waves-green btn-flat">Close</a>

                </div>
                <div class="modal-content">new review here</div>

            </div>-->
            <%@include file="new_review.jsp" %>
        </div>
    </div>
</div>
</body>
</html>