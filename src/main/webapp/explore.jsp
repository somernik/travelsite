<%@include file="templates/taglib.jsp" %>
<c:set var = "title" value = "WWW Travel"/>
<html>
<%@include file="templates/head.jsp" %>
<body>

<%@include file="templates/nav.jsp" %>

<div id="side_nav">
    <!-- Side (Search) Nav -->
    <!-- add search and determine what will be shown here -->
    <ul id="slide-out" class="side-nav fixed collapsible" data-collapsible="expandable">
        <li>
            <!-- TODO search items here -->
            <div class="collapsible-header"><i class="material-icons">filter_drama</i>First</div>
            <div class="collapsible-body"><span><a href="#!">First Sidebar Link</a>Lorem ipsum dolor sit amet.</span></div>
        </li>
        <li>                <div class="collapsible-header"><i class="material-icons">filter_drama</i>First</div>
            <div class="collapsible-body"><span><a href="#!">First Sidebar Link</a>Lorem ipsum dolor sit amet.</span></div>
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
                        <li class="tab col s2"><a href="#test1">Activities</a></li>
                        <li class="tab col s2"><a class="active" href="#test2">Locations</a></li>
                        <li class="tab col s2"><a href="#test3">Month</a></li>
                        <li class="tab col s2"><a href="#test4">Rating</a></li>
                    </ul>
                </div>
                <div id="test1" class="col s12">Test 1</div>
                <div id="test2" class="col s12">Test 2</div>
                <div id="test3" class="col s12">Test 3</div>
                <div id="test4" class="col s12">Test 4</div>
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