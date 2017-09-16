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

            <div id="new_review" class="modal">
                <!--<div class="modal-footer">
                <div>
                     <a href="#!" class="modal-action modal-close waves-effect waves-green btn-flat">Close</a>


                </div>-->
                <div class="modal-content">
                    <a href="#!" class="modal-action modal-close">
                        <i class="material-icons right">close</i></a>
                    <%@include file="new_review.jsp" %>
                </div>

            </div>


            <div id="login" class="modal">

                <div class="modal-content">
                    <div class="row">
                    <a href="#!" class="modal-action modal-close">
                        <i class="material-icons right">close</i></a></div>
                    <%@include file="login.jsp" %>
                </div>

            </div>

            <div id="signup" class="modal">
                <div class="modal-content">
                    <div class="row">
                    <a href="#!" class="modal-action modal-close">
                        <i class="material-icons right">close</i></a></div>
                    <%@include file="signup.jsp" %>
                </div>

            </div>

        </div>
    </div>
</div>
</body>
</html>