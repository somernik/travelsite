<%--
  Created by IntelliJ IDEA.
  User: sarah
  Date: 9/14/2017
  Time: 4:48 PM
  To change this template use File | Settings | File Templates.
--%>

<div class="row">
    <form class="col s12">

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

</script>