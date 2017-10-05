<%--
  Created by IntelliJ IDEA.
  User: sarah
  Date: 9/14/2017
  Time: 4:48 PM
  To change this template use File | Settings | File Templates.
--%>

<div class="row">
    <form class="col s12">
        <span class="rating">
            <input type="radio" class="rating-input"
                   id="rating-input-1-5" name="rating-input-1">
            <label for="rating-input-1-5" class="rating-star"><i class="material-icons" id="1">star_border</i></label>
            <input type="radio" class="rating-input"
                   id="rating-input-1-4" name="rating-input-1">
            <label for="rating-input-1-4" class="rating-star"><i class="material-icons" id="2">star_border</i></label>
            <input type="radio" class="rating-input"
                   id="rating-input-1-3" name="rating-input-1">
            <label for="rating-input-1-3" class="rating-star"><i class="material-icons" id="3">star_border</i></label>
            <input type="radio" class="rating-input"
                   id="rating-input-1-2" name="rating-input-1">
            <label for="rating-input-1-2" class="rating-star"><i class="material-icons" id="4">star_border</i></label>
            <input type="radio" class="rating-input"
                   id="rating-input-1-1" name="rating-input-1">
            <label for="rating-input-1-1" class="rating-star"><i class="material-icons" id="5">star_border</i></label>
        </span>
        <div class="row">
            <div class="input-field col s12">
                <div class="chips chips-placeholder" id="good"></div>
                <label for="good">Good Tags</label>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s12">
                <div class="chips chips-placeholder" id="bad"></div>
                <label for="bad">Bad Tags</label>
            </div>
        </div>

        <div class="row">
            <div class="input-field col s12">
                <input id="date" type="text" class="datepicker">
                <label for="date">Date Visited</label>
            </div>
        </div>
        <div class="row"><!-- TODO fix text area issue -->
            <div class="input-field col s12">
                <textarea id="review" class="materialize-textarea" data-length="120">test</textarea>
                <label for="review">Review</label>
            </div>
        </div>
        <button class="btn waves-effect waves-light" type="">Cancel</button>
        <button class="btn waves-effect waves-light" type="submit" name="action">Submit <i class="material-icons right">send</i></button>
    </form>
</div>
<script>/*
    $(document).ready(function(){
        // the "href" attribute of the modal trigger must specify the modal ID that wants to be triggered
        $('.modal').modal();
    });
    */

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

        var newIcon = document.createElement('i');
        newIcon.className = "material-icons";
        var iconText = document.createTextNode("star");
        var oldChild = this.firstChild;
        newIcon.appendChild(iconText);

        var current = this;
        while (current = current.previousSibling) {
            if (current.nodeName == 'LABEL' && current.hasChildNodes()) {
                alert("its a label");
                var old = current.firstChild;

                current.removeChild(old);

                //current.replaceChild(newIcon, old);
            }
        }

        var siblings = this.parentNode.childNodes;
        /*
        alert(this.nodeName);
        alert(this.previousSibling.nodeName);
        alert(this.previousSibling.previousSibling.nodeName);
        */

        //this.replaceChild(newIcon, oldChild);
    });
</script>
<style>

</style>
