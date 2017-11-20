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
</style>
<%@include file="templates/nav.jsp" %>
<div class="container" id="main">
    <div class="row">
    <div class="col s12 m12 l12">
        <div class="card" id="imageContent">
            <div class="card-image">
                <img src="images/demo.png"><!-- TODO pull first google image -->
                <span class="card-title">${location.name} + stars here</span>
                <div class="fixed-action-btn horizontal click-to-toggle">
                    <a class="btn-floating halfway-fab btn-large">
                        <i class="large material-icons">menu</i>
                    </a>
                    <ul>
                        <li><a class="btn-floating halfway-fab yellow darken-1" id="addPhoto"><i class="material-icons">add_a_photo</i></a></li>
                        <li><a class="btn-floating halfway-fab blue" id="allPhotos"><i class="material-icons">photo_library</i></a></li>
                        <li><a class="btn-floating halfway-fab green" id="addReview" ><i class="material-icons">add</i></a></li>
                        <li><a class="btn-floating halfway-fab red" id="fav"><i class="material-icons">favorite</i></a></li>

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
                <li class="tab col s3"><a href="#allTags" class="active">All Tags</a></li>
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
</div>

</div>