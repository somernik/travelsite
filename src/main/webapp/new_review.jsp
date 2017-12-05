<%--
  Created by IntelliJ IDEA.
  User: sarah
  Date: 9/14/2017
  Time: 4:48 PM
  To change this template use File | Settings | File Templates.
--%>

<div class="row">
    <form class="col s12" action='addReview' onsubmit="return prepareInputs()">
        <input id="placeId" type='hidden' name='placeId' />
        <input id="placeName" type='hidden' name='placeName' />
        <input id="rating" type='hidden' name='rating' />
        <input id="goodTags" type='hidden' name='goodTags' />
        <input id="badTags" type='hidden' name='badTags' />
        <input type="hidden" name="referrer" value="${referrer}">

        <span class="rating">
            <input type="radio" class="rating-input"
                   id="rating-input-1-1" name="rating-input-1" required>
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
                <div class="chips chips-autocomplete" id="good"></div>
                <label for="good">Recommended Activities</label>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s12">
                <div class="chips chips-autocomplete" id="bad"></div>
                <label for="bad">Activities to Avoid</label>
            </div>
        </div>

        <div class="row">
            <div class="input-field col s12">
                <input id="date" type="text" class="datepicker" name="date" pattern=""
                       data-error="Please select a date" data-success="Date Selected" required>
                <label for="date">Date Visited (Required)</label>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s12">
                <textarea id="review" class="materialize-textarea" name="review" maxlength="255"
                          data-length="255" required></textarea>
                <label for="review">Review (Required)</label>
            </div>
        </div>

        <button class="btn waves-effect waves-light" type="">Cancel</button>
        <button class="btn waves-effect waves-light" type="submit" name="action">Submit <i class="material-icons right">send</i></button>
    </form>
</div>
<script>

    var currentPlaceId = '';
    var currentPlaceName = '';
    var referrer = '${referrer}';

    if (referrer == "viewDetails") {
        // We are on the view details page and need to set the placeid/name in the form
        var id = document.getElementById("placeId");
        var name = document.getElementById("placeName");
        var locationId = '${location.googleId}';
        var locationPlace = '${location.name}';

        id.value = locationId;
        name.value = locationPlace;

        currentPlaceId = locationId;
        currentPlaceName = locationPlace;
    }
</script>

<%@include file="templates/review_accessory_js_css.jsp" %>