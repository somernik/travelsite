<div id="reviews" class="col s12"><!-- display of reviews -->

    <ul class="collection">
        <c:forEach var="review" items="${reviews}">

            <li class="collection-item avatar">
                <img src="images/background1.jpg" alt="" class="circle"><!-- or <i class="material-icons circle">folder</i> -->
                <span class="title">
                <c:forEach begin="1" end="${review.stars}" varStatus="loop">
                    <i class="material-icons star_icon">star</i>
                </c:forEach>
                <c:forEach begin="${review.stars + 1}" end="5">
                    <i class="material-icons star_icon">star_border</i>
                </c:forEach>
                <p>${review.body}</p>
                    <!-- TODO maybe tags added with this review? -->
                    <p class="extra_info">Visited <a href="viewDetails?placeId=${review.location.googleId}">${review.location.name}</a> on ${review.date} <br/>
                    Posted on ${review.created} by ${review.user.userName}
                </p>

                <c:if test="${user.userName != review.user.userName}">
                    <!-- check for not owner/admin -->
                    <a href="#!" class="secondary-content tooltipped" id="report"
                       data-position="right" data-delay="50" data-tooltip="Report Review"><i class="material-icons comment_icon">announcement</i></a>
                </c:if>

                <c:if test="${user.userName == review.user.userName}"><!-- if user is review owner -->
                      <a href="prepareReview?id=${review.id}" class="secondary-content tooltipped" id="edit"
                         data-position="right" data-delay="50" data-tooltip="Edit Review">
                          <i class="material-icons comment_icon">create</i>
                      </a>

                      <a class="secondary-content tooltipped" id="delete" href="deleteReview?id=${review.id}"
                         data-position="right" data-delay="50" data-tooltip="Delete Review">
                          <i class="material-icons comment_icon">delete</i>
                      </a>

                </c:if>

                <!-- check for admin -->
                <c:forEach var="priv" items="${user.userPrivileges}">
                    <c:if test="${priv.pk.privilege.value == 'administrator'}">
                         <a class="secondary-content tooltipped" id="delete" href="deleteReview?id=${review.id}&referrer=${referrer}"
                            data-position="right" data-delay="50" data-tooltip="Delete Review">
                             <i class="material-icons comment_icon">delete</i>
                         </a>
                    </c:if>
                </c:forEach>
            </li>

        </c:forEach>
    </ul>

</div>
<style>
    .comment_icon {
        font-size: 2.5rem;
    }

    .star_icon {
        color: #26a69a;
        font-size: 2.5rem;
    }

    #report i {
        color: red;
    }

    #edit i {
    }

    #edit {
        right: 55px;
    }

    #delete i {
        color: darkgray;
    }
    .extra_info {
        font-size: small;
    }
</style>