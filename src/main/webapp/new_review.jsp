<%--
  Created by IntelliJ IDEA.
  User: sarah
  Date: 9/14/2017
  Time: 4:48 PM
  To change this template use File | Settings | File Templates.
--%>

<div class="row">
    <form class="col s12" action='addReview' onsubmit="prepareInputs()">
        <input id="placeId" type='hidden' name='placeId' />
        <input id="placeName" type='hidden' name='placeName' />
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
                <input id="date" type="text" class="datepicker" name="date">
                <label for="date">Date Visited</label>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s12">
                <textarea id="review" class="materialize-textarea" name="review" data-length="120"></textarea>
                <label for="review">Review</label>
            </div>
        </div>

        <button class="btn waves-effect waves-light" type="">Cancel</button>
        <button class="btn waves-effect waves-light" type="submit" name="action">Submit <i class="material-icons right">send</i></button>
    </form>
</div>
<script>

    var tags = {};

    <c:forEach var="tag" items="${tags}">
    tags['${tag.name}'] = null;
    </c:forEach>
/*
    $(document).ready(function(){
        // the "href" attribute of the modal trigger must specify the modal ID that wants to be triggered
        // TODO populate from database
        $('.modal').modal();
        $('.chips-autocomplete').material_chip({
            autocompleteOptions: {
                data: tags,
                limit: Infinity,
                minLength: 1
            }
        });
    });
    */

var test = {};
test['Apple'] = null;
console.log(test);
console.log(tags);
    $(document).ready(function(){
        // the "href" attribute of the modal trigger must specify the modal ID that wants to be triggered
        // TODO populate from database
        $('.modal').modal();
        $('.chips-autocomplete').material_chip({
            autocompleteOptions: {
                data: tags,
                limit: Infinity,
                minLength: 1
            }
        });
    });



    $('.chips-placeholder').material_chip({
        //placeholder: 'Enter a tag',
        secondaryPlaceholder: '+Tag',
    });

    $('.datepicker').pickadate({
        selectMonths: true, // Creates a dropdown to control month
        selectYears: 15, // Creates a dropdown of 15 years to control year,
        today: 'Today',
        clear: 'Clear',
        close: 'Ok',
        closeOnSelect: false // Close upon selecting a date,
    });

    $('.rating-star').click(function(){

        var current = this;
        modifyStars(current, "star");

        while (current = current.previousSibling) {

            modifyStars(current, "star");

        }

        current = this;
        while (current = current.nextSibling) {
            modifyStars(current, "star_border");

        }
    });

    function modifyStars(currentNode, starType){

        if (currentNode.nodeName == 'LABEL' && currentNode.hasChildNodes()) {

            var old = currentNode.firstChild;

            var newIcon = document.createElement('i');
            newIcon.className = "material-icons";

            var iconText = document.createTextNode(starType);
            newIcon.appendChild(iconText);

            currentNode.replaceChild(newIcon, old);
        }
    }
</script>
<style>
    [type="radio"] + label:before,
    [type="radio"] + label:after {
        display: none;
    }

    [type="radio"]:not(:checked) + label,
    [type="radio"]:checked + label {
        padding-left: 0px;
    }

    .row {
        max-height:
    }
</style>
