package com.sarah.utility;

import com.sarah.entity.ReviewEntity;
import com.sarah.entity.User;
import com.sarah.persistence.ReviewDao;
import com.sarah.persistence.UserDao;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * Created by sarah on 10/22/2017.
 */
@WebFilter("/*")
public class UserFilter implements Filter {
    private final Logger log = Logger.getLogger(this.getClass());

    public void init(FilterConfig config) {

    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        String remoteUser = request.getRemoteUser();

        if (remoteUser != null) {
            HttpSession session = request.getSession();

            if (session.getAttribute("user") == null) {
                UserDao userDao = new UserDao();
                User user = userDao.getUserByUsernameWithPrivilege(remoteUser);
                log.info(user);
                /*
                log.info(user.getReviews().size());
                Set<ReviewEntity> reviews = user.getReviews();
                log.info(reviews.size());
                Set<ReviewEntity> initializedReviews = user.getReviews();
                ReviewDao reviewDao = new ReviewDao();
                for (ReviewEntity review: reviews) {
                    review = reviewDao.getReviewById(review.getId());
                    log.info(review.getLocation().getName());
                    initializedReviews.add(review);
                }
*/

                ReviewDao reviewDao = new ReviewDao();
                List<ReviewEntity> reviews = reviewDao.findByAndInitializeProperties("user", user);

                session.setAttribute("userReviews", reviews);
                session.setAttribute("user", user);
                //session.setAttribute("userReviews", initializedReviews);
            }
        }

        chain.doFilter(req, res);
    }

    public void destroy() {
        // If you have assigned any expensive resources as field of
        // this Filter class, then you could clean/close them here.
    }
}