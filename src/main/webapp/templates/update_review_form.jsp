<%--
  Created by IntelliJ IDEA.
  User: sarah
  Date: 9/14/2017
  Time: 4:48 PM
  To change this template use File | Settings | File Templates.
--%>
<style>
    .row {
        width: 50%;
        margin: auto;
    }
</style>
<div class="row">
    <form class="col s12" action='updateReview' onsubmit="prepareInputs()">
        <!--<input id="placeId" type='hidden' name='placeId' />
        <input id="placeName" type='hidden' name='placeName' />-->
        <input id="reviewId" type="hidden" name="reviewId" value="${id}">
        <input id="rating" type='hidden' name='rating' />
        <input id="goodTags" type='hidden' name='goodTags' />
        <input id="badTags" type='hidden' name='badTags' />

        <span class="rating">
            <input type="radio" class="rating-input"
                   id="rating-input-1-1" name="rating-input-1">
            <label for="rating-input-1-1" class="rating-star"><i class="material-icons" id="1">star_border</i></label>
            <input type="radio" class="rating-input"
                   id="rating-input-1-2" name="rating-input-1">
            <label for="rating-input-1-2" class="rating-star"><i class="material-icons" id="2">star_border</i></label>
            <input type="radio" class="rating-input"
                   id="rating-input-1-3" name="rating-input-1">
            <label for="rating-input-1-3" class="rating-star"><i class="material-icons" id="3">star_border</i></label>
            <input type="radio" class="rating-input"
                   id="rating-input-1-4" name="rating-input-1">
            <label for="rating-input-1-4" class="rating-star"><i class="material-icons" id="4">star_border</i></label>
            <input type="radio" class="rating-input"
                   id="rating-input-1-5" name="rating-input-1">
            <label for="rating-input-1-5" class="rating-star"><i class="material-icons" id="5">star_border</i></label>
        </span>

        <div class="row">
            <div class="input-field col s12">
                <input id="date" type="text" class="datepicker" name="date" value="${date}">
                <label for="date">Date Visited</label>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s12">
                <textarea id="review" class="materialize-textarea" name="review" data-length="120">${body}</textarea>
                <label for="review">Review</label>
            </div>
        </div>

        <a href="user.jsp" class="btn waves-effect waves-light">Cancel</a>
        <button class="btn waves-effect waves-light" type="submit" name="action">Update <i class="material-icons right">send</i></button>
    </form>
</div>
<%@include file="review_accessory_js_css.jsp" %>
<script>
    var oldRating = ${rating};
    console.log("rating: " + oldRating);
    if (oldRating) {
        switch (oldRating) {
            case 5:
                var star = document.getElementById("rating-input-1-5");
                star.checked = true;
                updateStar(star);
                break;
            case 4:
                var star = document.getElementById("rating-input-1-4");
                star.checked = true;
                updateStar(star);
                break;
            case 3:
                var star = document.getElementById("rating-input-1-3");
                star.checked = true;
                updateStar(star);
                break;
            case 2:
                var star = document.getElementById("rating-input-1-2");
                star.checked = true;
                updateStar(star);
                break;
            case 1:
                var star = document.getElementById("rating-input-1-1");
                star.checked = true;
                updateStar(star);
                break;

        }
        function updateStar(element) {
            var current = element;
            modifyStars(current, "star");

            while (current = current.previousSibling) {

                modifyStars(current, "star");

            }

            current = element;
            while (current = current.nextSibling) {
                modifyStars(current, "star_border");

            }
        }

    }
</script>
