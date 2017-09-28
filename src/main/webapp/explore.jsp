<%@include file="templates/taglib.jsp" %>
<c:set var = "title" value = "WWW Travel"/>
<html>
<%@include file="templates/head.jsp" %>
<body>

<%@include file="templates/nav.jsp" %>
<style>
    #search {
        padding-left: 3em;
        width: 95%;
    }


</style><!-- previous style is the same as on the index.jsp -->

<style>
   form p {
       display: inline;
       padding-top: 5px;
   }
    form label {
        padding: 0 30px;
    }

</style>
<div id="side_nav">
    <!-- Side (Search) Nav -->
    <!-- add search and determine what will be shown here -->
    <ul id="slide-out" class="side-nav fixed collapsible" data-collapsible="expandable">
        <li>
            <form>
                <div class="input-field">
                    <input id="search" type="search" required>
                    <label class="label-icon" for="search"><i class="material-icons">search</i></label>
                </div>
                <a href="#" class="btn-large waves-effect waves-light teal lighten-1">Search</a>
            </form>
        </li>
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
                    <form action="#"><!-- TODO pull dynamically from DB -->
                        <p>
                            <input type="checkbox" id="test5" />
                            <label for="test5">Backpacking</label>
                        </p>
                        <p>
                            <input type="checkbox" id="test6" />
                            <label for="test6">Biking</label>
                        </p>
                        <p>
                            <input type="checkbox" class="filled-in" id="filled-in-box" />
                            <label for="filled-in-box">Hiking</label>
                        </p>
                        <p>
                            <input type="checkbox" id="indeterminate-checkbox" />
                            <label for="indeterminate-checkbox">Kayaking</label>
                        </p>
                    </form>
                    <div>
                    When filtering by activities... top activities with a "best in: < month here >, location: < location here (restricted to what is visible in map) >" will appear ranked by ratings</div>
                </div>
                <div id="test2" class="col s12">
                    <form action="#"><!-- TODO pull dynamically from DB -->
                        <p>
                            <input type="checkbox" id="1" />
                            <label for="1">January</label>
                        </p>
                        <p>
                            <input type="checkbox" id="2" />
                            <label for="2">February</label>
                        </p>
                        <p>
                            <input type="checkbox" class="filled-in" id="3" />
                            <label for="3">March</label>
                        </p>
                        <p>
                            <input type="checkbox" id="4" />
                            <label for="4">April</label>
                        </p>
                        <p>
                            <input type="checkbox" id="5" />
                            <label for="5">May</label>
                        </p>
                        <p>
                            <input type="checkbox" id="6" />
                            <label for="6">June</label>
                        </p><br />
                        <p>
                            <input type="checkbox" class="filled-in" id="7" />
                            <label for="7">July</label>
                        </p>
                        <p>
                            <input type="checkbox" id="8" />
                            <label for="8">August</label>
                        </p>
                        <p>
                            <input type="checkbox" id="9" />
                            <label for="9">September</label>
                        </p>
                        <p>
                            <input type="checkbox" id="10" />
                            <label for="10">October</label>
                        </p>
                        <p>
                            <input type="checkbox" class="filled-in" id="11" />
                            <label for="11">November</label>
                        </p>
                        <p>
                            <input type="checkbox" id="12" />
                            <label for="12">December</label>
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

        <%@include file="templates/form_modals.jsp" %>

    </div>
    <div class="section no-pad-bot no-pad-top">

            <div class="row">
            <img src="images/demo.png" style="height:20em"/>

            </div>
    </div>

    <a href="#" data-activates="slide-out" class="button-collapse"><i class="material-icons">menu</i></a>
    <a class="waves-effect waves-light btn modal-trigger" href="#new_review">New Review</a>
    <a class="waves-effect waves-light btn modal-trigger" href="#">Location Details</a>
</div>


</body>
</html>

<!-- style for side nav TODO make disabled tab & indicator color grayed out -->
<style>
    #slide-out {
        margin-top: 64px;
    }

    @media only screen and (max-width : 992px) {
        #slide-out {
            margin-top: 0px;
        }
    }

    #not_side_nav {
        margin-left: 300px;
    }

    @media only screen and (max-width : 992px) {
        #not_side_nav {
            margin-left: 0px;
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