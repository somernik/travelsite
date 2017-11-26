<div id="locations" class="col s12">
        <div class="row">
            <c:forEach items="${user.locations}" var="location">
                <div class="col s12 m6 l4">
                    <div class="card sticky-action">
                        <div class="card-image">
                            <img src="${imageUrls[location.id]}" />
                            <span class="card-title">${location.name}</span>
                            <a class="btn-floating halfway-fab waves-effect waves-light red tooltipped" data-position="right" data-delay="50" data-tooltip="Un-favorite this location"><i class="material-icons">favorite</i></a>
                            <!-- TODO remove location from saved values -->
                        </div>
                        <div class="card-content">
                        <span class="activator grey-text text-darken-4"><i class="material-icons right">more_vert</i>
                        I am a very simple card.</span>
                        </div>

                        <div class="card-action">
                            <a href="viewDetails?placeId=${location.googleId}">View Details</a>
                        </div>

                        <div class="card-reveal">
                            <span class="card-title grey-text text-darken-4">${location.name}<i class="material-icons right">close</i></span>
                            <p>Here is some more information about this product that is only revealed once clicked on.</p>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
</div>