<%@include file="templates/taglib.jsp" %>
<c:set var = "title" value = "Location Detail | WWW Travel"/>
<html>
<%@include file="templates/head.jsp" %>

<body>
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

    #fav {
        right: 21px;
    }
    #addReview {
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
                <img src="${photoUrl}" class="scale"><!-- TODO pull first google image -->
                </div>
                <span class="card-title">${location.name} + stars here</span>
                <div class="fixed-action-btn horizontal click-to-toggle">
                    <a class="btn-floating halfway-fab btn-large">
                        <i class="large material-icons">menu</i>
                    </a>
                    <ul>
                        <li><a class="btn-floating halfway-fab yellow darken-1" id="addPhoto"><i class="material-icons">add_a_photo</i></a></li>
                        <li><a class="btn-floating halfway-fab blue" id="allPhotos"><i class="material-icons">photo_library</i></a></li>
                        <li><a class="btn-floating halfway-fab green modal-trigger" id="addReview" href="#new_review"><i class="material-icons">add</i></a></li>
                        <li><a class="btn-floating halfway-fab red" id="fav" href="favorite?placeId=${location.googleId}"><i class="material-icons">favorite</i></a></li>

                    </ul>
                </div>
            </div>
            <!--
            <div class="card-content">
                <p>I am a very simple card. I am good at containing small bits of information. I am convenient because I require little markup to use effectively.</p>
            </div>-->
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
            <p>I am a very simple card. I am good at containing small bits of information. I am convenient because I require little markup to use effectively.</p>

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
</div>

</div>
