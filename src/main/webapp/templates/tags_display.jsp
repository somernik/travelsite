
<!-- display of all tags -->
<div id="allTags" class="col s12">
    <c:if test="${empty tagLocations}">
        <p>No tags yet!</p>
    </c:if>
    <c:if test="${not empty tagLocations}">
        <ul class="collection">
            <li class="collection-item">
                <span class="title">Recommended Activities</span><br />

                <c:forEach var="taglocation" items="${tagLocations}">
                    <c:if test="${taglocation.positive}">
                        <div class="chip positive">
                                ${taglocation.tag.name}
                            <i class="add material-icons">add</i>
                        </div>
                    </c:if>

                </c:forEach>
            </li>
            <li class="collection-item">
                <span class="title">Activities Not Recommended</span><br />

                <c:forEach var="taglocation" items="${tagLocations}">
                    <c:if test="${not taglocation.positive}">
                        <div class="chip negative">
                                ${taglocation.tag.name}
                            <i class="add material-icons">add</i>
                        </div>
                    </c:if>


                </c:forEach>
            </li>
        </ul>
    </c:if>
</div>
<style>
    .positive {
        background-color: mediumseagreen;
    }
    .negative {
        background-color: orangered;
    }

    .add {
        cursor: pointer;
        float: right;
        font-size: 16px;
        line-height: 32px;
        padding-left: 8px;
    }
</style>