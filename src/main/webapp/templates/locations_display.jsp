<style>
    #cardBody {
        font-size: 20px;
    }
</style>
<div id="locations" class="col s12">
        <div class="row">
            <c:forEach items="${user.locations}" var="location">
                <div class="col s12 m6 l4">
                    <div class="card sticky-action">
                        <div class="card-image">

                            <!--<img src="https://maps.googleapis.com/maps/api/place/photo?maxwidth=5000&photoreference=${location.photoReference}&key=AIzaSyA_wVJfh8Ov9cLUZDxSNhOpzw3OEx6y3HE" />
                            -->
                            <img src="images/background1.jpg" alt="for testing"><span class="card-title"><!-- Previously location name --></span>
                            <a class="btn-floating halfway-fab waves-effect waves-light red tooltipped" data-position="right" data-delay="50" data-tooltip="Un-favorite this location"><i class="material-icons">favorite</i></a>
                            <!-- TODO remove location from saved values -->
                        </div>
                        <div class="card-content">
                        <span class="activator grey-text text-darken-4" id="cardBody"><i class="material-icons right">more_vert</i>
                        ${location.name}</span>
                        </div>

                        <div class="card-action">
                            <a href="viewDetails?placeId=${location.googleId}">View Details</a>
                        </div>

                        <div class="card-reveal">
                            <span class="card-title grey-text text-darken-4">${location.name}<i class="material-icons right">close</i></span>
                            <p>${location.name} is great! Visit soon!</p>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
</div>