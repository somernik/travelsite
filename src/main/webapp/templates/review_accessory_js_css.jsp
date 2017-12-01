<script>
    console.log("here");
    var tags = {};

    // from database!
    <c:forEach var="tag" items="${tags}">
    tags['${tag.name}'] = null;
    </c:forEach>

    $(document).ready(function(){
        // the "href" attribute of the modal trigger must specify the modal ID that wants to be triggered
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

    function prepareInputs() {
        document.getElementById("placeId").value = currentPlaceId;
        document.getElementById("placeName").value = currentPlaceName;


        if (document.getElementById("rating-input-1-5").checked) {
            document.getElementById("rating").value = 5;

        } else if (document.getElementById("rating-input-1-4").checked) {
            document.getElementById("rating").value = 4;

        } else if (document.getElementById("rating-input-1-3").checked) {
            document.getElementById("rating").value = 3;

        } else if (document.getElementById("rating-input-1-2").checked) {
            document.getElementById("rating").value = 2

        } else if (document.getElementById("rating-input-1-1").checked) {
            document.getElementById("rating").value = 1;

        }
        console.log(document.getElementById("rating").value);

        document.getElementById("goodTags").value = processTags('good');

        document.getElementById("badTags").value = processTags('bad');

    }

    function processTags(inputId) {

        var tags = $('#' + inputId).material_chip('data');

        var string = "";

        tags.forEach(function(e, i) {
            //console.log(e.tag);
            //console.log(i);
            string += e.tag + ";";
        });

        return string;
    }
</script>
<style>
    [type="radio"] + label:before,
    [type="radio"] + label:after {
        display: none;
    }

    [type="radio"]:not(:checked) + label,
    [type="radio"]:checked + label {
        padding-left: 0;
    }

</style>