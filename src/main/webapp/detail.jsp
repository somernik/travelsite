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
</style>
<%@include file="templates/nav.jsp" %>
<div class="container" id="main">
    <div class="row">
    <div class="col s12 m12 l12">
        <div class="card" id="imageContent">
            <div class="card-image">
                <img src="images/demo.png">
                <span class="card-title">Location Name + stars here</span>
                <div class="fixed-action-btn horizontal click-to-toggle">
                    <a class="btn-floating halfway-fab btn-large red">
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
        <div class="col s12 m9 l9">
            <ul class="tabs tabs-fixed-width">
                <li class="tab col s3"><a href="#reviews" class="active">Reviews</a></li>
                <li class="tab col s3"><a href="#photos">Photos</a></li>
                <li class="tab col s3"><a href="#allTags">All Tags</a></li>
            </ul>


            <div id="reviews" class="col s12">Test 1</div>
            <div id="photos" class="col s12">Test 2</div>
            <div id="allTags" class="col s12">Test 3</div>
        </div>
        <div class="col s12 m3 l3">
            <p>I am a very simple card. I am good at containing small bits of information. I am convenient because I require little markup to use effectively.</p>

        </div>

    </div>
</div>

</div>