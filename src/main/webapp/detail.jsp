<%@include file="templates/taglib.jsp" %>
<c:set var = "title" value = "Location Detail | WWW Travel"/>
<html>
<%@include file="templates/head.jsp" %>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.simpleWeather/3.1.0/jquery.simpleWeather.min.js"></script>
<script src="https://s.codepen.io/assets/libs/modernizr.js" type="text/javascript"></script>

<link href='https://fonts.googleapis.com/css?family=Open+Sans:400,300' rel='stylesheet' type='text/css'>
<body>
<style>

    #weather {
        margin: 0px auto;
        text-align: center;
        text-transform: uppercase;
    }

    i {
        color: #fff;
        font-family: weather;
        font-size: 150px;
        font-weight: normal;
        font-style: normal;
        line-height: 1.0;
        text-transform: none;
    }

    .icon-0:before { content: ":"; }
    .icon-1:before { content: "p"; }
    .icon-2:before { content: "S"; }
    .icon-3:before { content: "Q"; }
    .icon-4:before { content: "S"; }
    .icon-5:before { content: "W"; }
    .icon-6:before { content: "W"; }
    .icon-7:before { content: "W"; }
    .icon-8:before { content: "W"; }
    .icon-9:before { content: "I"; }
    .icon-10:before { content: "W"; }
    .icon-11:before { content: "I"; }
    .icon-12:before { content: "I"; }
    .icon-13:before { content: "I"; }
    .icon-14:before { content: "I"; }
    .icon-15:before { content: "W"; }
    .icon-16:before { content: "I"; }
    .icon-17:before { content: "W"; }
    .icon-18:before { content: "U"; }
    .icon-19:before { content: "Z"; }
    .icon-20:before { content: "Z"; }
    .icon-21:before { content: "Z"; }
    .icon-22:before { content: "Z"; }
    .icon-23:before { content: "Z"; }
    .icon-24:before { content: "E"; }
    .icon-25:before { content: "E"; }
    .icon-26:before { content: "3"; }
    .icon-27:before { content: "a"; }
    .icon-28:before { content: "A"; }
    .icon-29:before { content: "a"; }
    .icon-30:before { content: "A"; }
    .icon-31:before { content: "6"; }
    .icon-32:before { content: "1"; }
    .icon-33:before { content: "6"; }
    .icon-34:before { content: "1"; }
    .icon-35:before { content: "W"; }
    .icon-36:before { content: "1"; }
    .icon-37:before { content: "S"; }
    .icon-38:before { content: "S"; }
    .icon-39:before { content: "S"; }
    .icon-40:before { content: "M"; }
    .icon-41:before { content: "W"; }
    .icon-42:before { content: "I"; }
    .icon-43:before { content: "W"; }
    .icon-44:before { content: "a"; }
    .icon-45:before { content: "S"; }
    .icon-46:before { content: "U"; }
    .icon-47:before { content: "S"; }

    #weather h2 {
        margin: 0 0 8px;
        color: #26a69a;
        font-size: 100px;
        font-weight: 300;
        text-align: center;
        text-shadow: 0px 1px 3px rgba(0, 0, 0, 0.15);
    }

    #weather ul {
        margin: 0;
        padding: 0;
    }

    #weather li {
        background: #fff;
        background: rgba(255,255,255,0.90);
        padding: 20px;
        display: inline-block;
        border-radius: 5px;
    }

    #weather .currently {
        margin: 0 20px;
    }
</style>
<style>
    .btn-floating.btn-large.halfway-fab {
         bottom: -15px;
    }

    .btn-floating.halfway-fab {
        bottom: -10px;
    }
    .fixed-action-btn {
        position: inherit;
    }

    #fav, #favNotLoggedIn {
        right: 21px;
    }
    #addReview, #addReviewNotLoggedIn {
        right: 67px;
    }
    #addPhoto {
        right: 110px;
    }
    #allPhotos {
        right: 153px;
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

    .crop-height {
        /* max-width: 1200px; /* native or declared width of img src (if known) */
        max-height: 320px;
        overflow: hidden; }

    img.scale {
        /* corrects small inline gap at bottom of containing div */
        display: block;
        width: 100%; /* corrects obscure Firefox bug */
        max-width: 100%;
        /* just in case, to force correct aspect ratio */
        height: auto !important;
        /*width: auto\9;
        /* ie8+9 - use modernizr instead of this \9 hack */
        /* lt ie8 */
        -ms-interpolation-mode: bicubic;
        /* optionally force a minimum size if img src size is known: */
        /* min-height: 320px; /* max-height of .crop-height */
        /* min-width: 480px; /* proportional to above */ }
</style>
<%@include file="templates/nav.jsp" %>

<div class="container" id="main">
    <div class="row">
    <div class="col s12 m12 l12">
        <div class="card" id="imageContent">
            <div class="card-image">
                <div class="crop-height">
                    <!--<img src="https://maps.googleapis.com/maps/api/place/photo?maxwidth=5000&photoreference=${location.photoReference}&key=AIzaSyA_wVJfh8Ov9cLUZDxSNhOpzw3OEx6y3HE" />-->
                    <img src="images/background1.jpg" class="scale">
                </div>
                <span class="card-title">${location.name} + stars here</span>
                <div class="fixed-action-btn horizontal click-to-toggle">
                    <a class="btn-floating halfway-fab btn-large">
                        <i class="large material-icons">menu</i>
                    </a>
                    <ul>
                        <li><a class="btn-floating halfway-fab yellow darken-1" id="addPhoto"><i class="material-icons">add_a_photo</i></a></li>
                        <li><a class="btn-floating halfway-fab blue" id="allPhotos"><i class="material-icons">photo_library</i></a></li>
                        <c:if test="${not empty user}">
                            <li><a class="btn-floating halfway-fab green modal-trigger" id="addReview" href="#new_review"><i class="material-icons">add</i></a></li>
                            <li><a class="btn-floating halfway-fab red" id="fav" href="favorite?placeId=${location.googleId}&placeName=${location.name}"><i class="material-icons">favorite</i></a></li>
                        </c:if>
                        <c:if test="${empty user}">
                            <li><a class="btn-floating halfway-fab green modal-trigger" id="addReviewNotLoggedIn" href="#pleaseLogIn"><i class="material-icons">add</i></a></li>
                            <li><a class="btn-floating halfway-fab red modal-trigger" id="favNotLoggedIn" href="#pleaseLogIn"><i class="material-icons">favorite</i></a></li>
                        </c:if>
                    </ul>
                </div>
            </div>
            <c:if test="${not empty message}">
            <div class="card-content">
                <p>${message}</p>
            </div>
            </c:if>
        </div>
    </div>

    <div class="row">
        <div class="col s12 m9 l9"><!-- reviews/photos/all tags -->
            <ul class="tabs tabs-fixed-width">
                <li class="tab col s3"><a href="#reviews" >Reviews</a></li>
                <li class="tab col s3"><a href="#photos">Photos</a></li>
                <li class="tab col s3"><a href="#allTags">All Tags</a></li>
            </ul>

            <%@include file="templates/reviews_display.jsp" %><!-- display of reviews -->

            <div id="photos" class="col s12"><!-- display of photos -->
                Test
            </div>

            <%@include file="templates/tags_display.jsp" %><!-- display of all tags -->

        </div>
        <div class="col s12 m3 l3"><!-- weather plugin? -->
            <div id="weather"></div>
        </div>

    </div>

    <!-- Modals -->

    <div id="new_review" class="modal">

        <div class="modal-content">
            <div class="row">
                <a href="#!" class="modal-action modal-close">
                    <i class="material-icons right">close</i></a>
            </div>

            <%@include file="new_review.jsp" %>
        </div>

    </div>

    <div id="pleaseLogIn" class="modal"><!-- display of reviews -->

        <div class="modal-content">
            <div class="row">
                <a href="#!" class="modal-action modal-close">
                    <i class="material-icons right">close</i></a>
            </div>

            <div class="col s12">
                <p>You must be logged in to do that. Login <a href="user.jsp">here</a></p>

            </div>
        </div>
    </div>
    <!-- END MODALS -->
</div>

</div>
<script>
    // v3.1.0
    //Docs at http://simpleweatherjs.com
    $(document).ready(function() {
        $.simpleWeather({
            location: '${location.name}',
            woeid: '',
            unit: 'f',
            success: function(weather) {
                html = '<h2>';
                //html += '<i class="icon-'+weather.code+'"></i> ';
                html += weather.temp+'&deg;'+weather.units.temp+'</h2>';
                html += '<ul>';
                html+= '<li>Current Weather</li>';
                //html+= '<li>'+weather.city+', '+weather.region+'</li>';
                html += '<li class="currently">'+weather.currently+'</li>';
                html += '<li>'+weather.wind.direction+' '+weather.wind.speed+' '+weather.units.speed+'</li></ul>';

                $("#weather").html(html);
            },
            error: function(error) {
                $("#weather").html('<p>'+error+'</p>');
            }
        });
    });

</script>