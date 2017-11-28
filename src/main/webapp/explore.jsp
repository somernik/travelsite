<%@include file="templates/taglib.jsp" %>
<c:set var = "title" value = "WWW Travel"/>
<html>
<%@include file="templates/head.jsp" %>
<body>

<%@include file="templates/nav.jsp" %>
<style>

</style><!-- previous style is the same as on the index.jsp -->

<style>
    form.filterForms p {
       display: inline;
       padding-top: 5px;
    }

    form.filterForms label {
        padding: 0 20px;
    }

</style>
<div id="side_nav">
    <!-- Side (Search) Nav -->
    <!-- add search and determine what will be shown here -->
    <ul id="slide-out" class="side-nav fixed collapsible" data-collapsible="expandable">
        <li>
            <!-- TODO search items here -->
            <div class="collapsible-header"><i class="material-icons">directions_bike</i>This Month's Top Activites</div>
            <div class="collapsible-body"><span><a href="#!">Hiking</a>Lorem ipsum dolor sit amet.</span></div>
            <div class="collapsible-body"><span><a href="#!">Biking</a>Lorem ipsum dolor sit amet.</span></div>

        </li>
        <li>
            <div class="collapsible-header"><i class="material-icons">location_on</i>Nearby Locations</div>
            <div class="collapsible-body"><span><a href="#!">Nearby things here</a>Lorem ipsum dolor sit amet.</span></div>
        </li>
    </ul>
</div>

<div id="not_side_nav">
    <div class="section no-pad-bot" id="filterDiv">

        <div id="filters">
            <div class="row">
                <div class="col s12">
                    <ul class="tabs">
                        <li class="tab col s2 disabled"><a href="#test3">Filters</a></li>
                        <li class="tab col s2"><a href="#test1"><i class="material-icons">directions_bike</i>Activities</a>

                        </li>
                        <li class="tab col s2"><a href="#test2"><i class="material-icons">event</i>Month</a></li>
                        <li class="tab col s2"><a href="#test3"><i class="material-icons">star</i>Rating</a></li>
                    </ul>
                </div>
                <div id="test1" class="col s12">
                    <form action="#" class="filterForms"><!-- pulled dynamically from DB -->
                        <c:forEach items="${tags}" var="tag">
                            <p>
                                <input type="checkbox" id="${tag.name}" />
                                <label for="${tag.name}">${tag.name}</label>
                            </p>
                        </c:forEach>
                    </form>
                    <div>
                    When filtering by activities... top activities with a "best in: < month here >, location: < location here (restricted to what is visible in map) >" will appear ranked by ratings</div>
                </div>
                <div id="test2" class="col s12">
                    <form action="#" class="filterForms"><!-- TODO pull dynamically from DB?     -->
                        <p>
                            <input type="checkbox" id="1" />
                            <label for="1">Jan</label>
                        </p>
                        <p>
                            <input type="checkbox" id="2" />
                            <label for="2">Feb</label>
                        </p>
                        <p>
                            <input type="checkbox" class="filled-in" id="3" />
                            <label for="3">Mar</label>
                        </p>
                        <p>
                            <input type="checkbox" id="4" />
                            <label for="4">Apr</label>
                        </p>
                        <p>
                            <input type="checkbox" id="5" />
                            <label for="5">May</label>
                        </p>
                        <p>
                            <input type="checkbox" id="6" />
                            <label for="6">Jun</label>
                        </p>
                        <p>
                            <input type="checkbox" class="filled-in" id="7" />
                            <label for="7">Jul</label>
                        </p>
                        <p>
                            <input type="checkbox" id="8" />
                            <label for="8">Aug</label>
                        </p>
                        <p>
                            <input type="checkbox" id="9" />
                            <label for="9">Sept</label>
                        </p>
                        <p>
                            <input type="checkbox" id="10" />
                            <label for="10">Oct</label>
                        </p>
                        <p>
                            <input type="checkbox" class="filled-in" id="11" />
                            <label for="11">Nov</label>
                        </p>
                        <p>
                            <input type="checkbox" id="12" />
                            <label for="12">Dec</label>
                        </p>
                    </form>
                    When filtering by month... shows top activities given month(s) selected and location on map</div>
                <div id="test3" class="col s12">only shows activities/locations with a given rating or higher</div>
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
    <div class="section no-pad-bot no-pad-top">


        <input id="pac-input" class="controls" type="text" placeholder="Search Box">
        <div id="map"></div>
    </div>
    <a href="#" data-activates="slide-out" class="button-collapse"><i class="material-icons">menu</i></a>
    <a class="waves-effect waves-light btn modal-trigger" href="#new_review">New Review</a>
    <a class="waves-effect waves-light btn modal-trigger" href="#">Location Details</a>
</div>


<script>
    var currentPlaceId = '';
    var currentPlaceName = '';
    var currentLat = '';
    var currentLong = '';

    //////////////////////////////////////////////////////////
    // Get user location
    // https://www.w3schools.com/html/html5_geolocation.asp
    function getUserLocation() {
        console.log("here2");
        //var x = document.getElementById("demo");
        // previous var for error message
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(showPosition);
        } else {
            // User denied request oh well
            //x.innerHTML = "Geolocation is not supported by this browser.";
        }
    }
    function showPosition(position) {
        console.log("here3");
        currentLat = position.coords.latitude;
        currentLong = position.coords.longitude;
        /*
        x.innerHTML = "Latitude: " + position.coords.latitude +
            "<br>Longitude: " + position.coords.longitude;
            */
        console.log(currentLat);
        console.log(currentLong);
    }


    //////////////////////////////////////////////////////////




    //https://developers.google.com/maps/documentation/javascript/examples/places-searchbox

    // This example adds a search box to a map, using the Google Place Autocomplete
    // feature. People can enter geographical searches. The search box will return a
    // pick list containing a mix of places and predicted search terms.

    // This example requires the Places library. Include the libraries=places
    // parameter when you first load the API. For example:
    // <script src="https://maps.googleapis.com/maps/api/js?key=YOUR_API_KEY&libraries=places">

    function initAutocomplete() {

        getUserLocation();
        // TODO make this wait until the values are populated
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(function (position) {
                currentLat = position.coords.latitude;
                currentLong = position.coords.longitude;

                });
        } else {
            // User denied request oh well
            //x.innerHTML = "Geolocation is not supported by this browser.";
        }

        console.log(currentLat);
        console.log(currentLong);
        var map = new google.maps.Map(document.getElementById('map'), {
            center: {lat: -33.8688, lng: 151.2195},
            zoom: 13,
            mapTypeId: 'roadmap'
        });

        // Create the search box and link it to the UI element.
        var input = document.getElementById('pac-input');
        var searchBox = new google.maps.places.SearchBox(input);
        map.controls[google.maps.ControlPosition.TOP_LEFT].push(input);

        // Bias the SearchBox results towards current map's viewport.
        map.addListener('bounds_changed', function() {
            searchBox.setBounds(map.getBounds());
        });

        var markers = [];
        // Listen for the event fired when the user selects a prediction and retrieve
        // more details for that place.
        searchBox.addListener('places_changed', function() {
            var places = searchBox.getPlaces();

            if (places.length == 0) {
                return;
            }

            // Clear out the old markers.
            markers.forEach(function(marker) {
                marker.setMap(null);
            });
            markers = [];
            infowindow = new google.maps.InfoWindow();


            // For each place, get the icon, name and location.
            var bounds = new google.maps.LatLngBounds();
            places.forEach(function(place) {
                console.log(place);

                if (!place.geometry) {
                    console.log("Returned place contains no geometry");
                    return;
                }
                var icon = {
                    url: place.icon,
                    size: new google.maps.Size(71, 71),
                    origin: new google.maps.Point(0, 0),
                    anchor: new google.maps.Point(17, 34),
                    scaledSize: new google.maps.Size(25, 25)
                };

                var marker = new google.maps.Marker({
                    map: map,
                    icon: icon,
                    title: place.name,
                    position: place.geometry.location
                });

                google.maps.event.addListener(marker, 'click', function() {
                    currentPlaceId = place.place_id;
                    currentPlaceName = place.name;
                    ///////
                    var detailForm = "<form action='viewDetails'>" +
                        "<input value='" + place.place_id + "' type='hidden' name='placeId' />" +
                        "<input value='" + place.name + "' type='hidden' name='placeName' />" +
                        "<input type='submit' class='waves-effect waves-light btn button_in_popup' value='Details' />" +
                        "</form>";


                    <c:if test="${not empty user}">
                        var reviewForm = "<a class='waves-effect waves-light btn button_in_popup modal-trigger' href='#new_review'>New Review</a>";
                    </c:if>
                    <c:if test="${empty user}">
                        var reviewForm = "<p><a href='user.jsp'>Sign in</a> to add a review</p>";
                    </c:if>

                    ///////
                    infowindow.setContent(place.name + "<br/>" + detailForm + reviewForm);
                    // TODO add overall rating or something
                    infowindow.open(map, this);
                });

                // Create a marker for each place.
                markers.push(marker);

                if (place.geometry.viewport) {
                    // Only geocodes have viewport.
                    bounds.union(place.geometry.viewport);
                } else {
                    bounds.extend(place.geometry.location);
                }
            });
            map.fitBounds(bounds);
        });
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
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCDzIyqAs2QFZHodjbm8z7bnr233R9aSuo&libraries=places&callback=initAutocomplete"
        async defer></script>

<style>
    /* Always set the map height explicitly to define the size of the div
     * element that contains the map. */
    #map {
        height: 80%;
    }
    /* Optional: Makes the sample page fill the window. */
    html, body {
        height: 100%;
        margin: 0;
        padding: 0;
    }
    #description {
        font-family: Roboto;
        font-size: 15px;
        font-weight: 300;
    }

    #infowindow-content .title {
        font-weight: bold;
    }

    #infowindow-content {
        display: none;
    }

    #map #infowindow-content {
        display: inline;
    }

    .pac-card {
        margin: 10px 10px 0 0;
        border-radius: 2px 0 0 2px;
        box-sizing: border-box;
        -moz-box-sizing: border-box;
        outline: none;
        box-shadow: 0 2px 6px rgba(0, 0, 0, 0.3);
        background-color: #fff;
        font-family: Roboto;
    }

    #pac-container {
        padding-bottom: 12px;
        margin-right: 12px;
    }

    .pac-controls {
        display: inline-block;
        padding: 5px 11px;
    }

    .pac-controls label {
        font-family: Roboto;
        font-size: 13px;
        font-weight: 300;
    }

    #pac-input {
        background-color: #fff;
        font-family: Roboto;
        font-size: 15px;
        font-weight: 300;
        margin-left: 12px;
        padding: 0 11px 0 13px;
        text-overflow: ellipsis;
        width: 400px;
    }

    #pac-input:focus {
        border-color: #4d90fe;
    }

    #title {
        color: #fff;
        background-color: #4d90fe;
        font-size: 25px;
        font-weight: 500;
        padding: 6px 12px;
    }
    #target {
        width: 345px;
    }
</style>
</body>
</html>

<!-- style for side nav TODO make disabled tab & indicator color grayed out -->
<style>
    #slide-out {
        margin-top: 64px;
    }

    @media only screen and (max-width : 992px) {
        #slide-out {
            margin-top: 0;
        }
    }

    #not_side_nav {
        margin-left: 300px;
    }

    @media only screen and (max-width : 992px) {
        #not_side_nav {
            margin-left: 0;
        }
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

    #filterDiv {
        padding-top: 3px;
    }

</style>