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
                        <li class="tab col s3"><a href="#profile">Profile</a></li>
                        <li class="tab col s3"><a href="#reviews">Reviews</a></li>
                        <li class="tab col s3"><a href="#test3">Photos</a></li>
                        <li class="tab col s3"><a href="#locations">Favorites</a></li>

                    </ul>
                </div>
                <div id="profile" class="col s12">
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
                    <form class="col s12 m6 offset-m3" action="deleteUser">
                        <div class="row">
                            <div class="input-field col s12">
                                <input id="id" type="text" value="" name="id" class="validate">
                                <label for="id">User Id</label>
                            </div>
                        </div>
                        <button class="btn waves-effect waves-light" type="submit" name="action">Delete Account</button>

                    </form>
                </div>
                <!--<div id="test2" class="col s12">Test 2</div>-->
                <c:set var="reviews" value="${userReviews}" />
                <%@include file="templates/reviews_display.jsp" %><!-- display of reviews -->

                <div id="test3" class="col s12">Test 3
                    <c:set value="hi" var="test"/>
                    ${test}
                </div>

                <%@include file="templates/locations_display.jsp" %><!-- display of all favorited locations -->
            </div>

            <!-- modal forms -->
            <%@include file="templates/user_update_forms.jsp" %>

            <!-- end of modal forms -->
        </div>
    </div>
</div>

<%@include file="templates/footer.jsp" %>

</body>
</html>
<!-- style for side nav TODO make disabled tab & indicator color grayed out -->
<style>
    #slide-out {
        margin-top: 64px;
    }

    @media only screen and (max-width : 992px) {
        #slide-out {
            margin-top: 0;
        }
    }

    #not_side_nav {
        margin-left: 300px;
    }

    @media only screen and (max-width : 992px) {
        #not_side_nav {
            margin-left: 0;
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

    #profile p {
        display: inline-block;
    }

</style>
