
<!-- display of all tags -->
<div id="allTags" class="col s12">
    <ul class="collection">
        <li class="collection-item">
            <span class="title">Recommended Activities</span><br />

            <c:forEach var="taglocation" items="${tags}">
                <c:if test="${taglocation.rank > 0}">
                    <div class="chip positive">
                            ${taglocation.tag.name}
                            ${taglocation.rank}
                        <i class="add material-icons">add</i>
                    </div>
                </c:if>

            </c:forEach>
        </li>
        <li class="collection-item">
            <span class="title">Activities Not Recommended</span><br />

            <c:forEach var="taglocation" items="${tags}">
                <c:if test="${taglocation.negativeRank > 0}">
                    <div class="chip negative">
                            ${taglocation.tag.name}
                            ${taglocation.negativeRank}
                        <i class="add material-icons">add</i>
                    </div>
                </c:if>

            </c:forEach>
        </li>
    </ul>
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