package com.sarah.utility;

import com.sarah.controller.LocationPhoto;
import com.sarah.entity.LocationEntity;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sarah on 10/22/2017.
 */
@WebFilter("/*")
public class UserFilter implements Filter {
    private final Logger log = Logger.getLogger(this.getClass());
    private LocationPhoto locationPhoto = new LocationPhoto();

    public void init(FilterConfig config) {

    }

    /**
     * Filters when user logs in and adds user to session
     * @param req the request
     * @param res the response
     * @param chain the chain
     * @throws IOException the ioexception
     * @throws ServletException the servletexception
     */
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        String remoteUser = request.getRemoteUser();

        if (remoteUser != null) {
            HttpSession session = request.getSession();

            if (session.getAttribute("user") == null) {
                UserDao userDao = new UserDao();
                User user = userDao.getUserByUsernameWithPrivilege(remoteUser);
                log.info(user);

                ReviewDao reviewDao = new ReviewDao();
                List<ReviewEntity> reviews = reviewDao.findByAndInitializeProperties("user", user);
                //LocationDao locationDao = new LocationDao();
                //List<LocationEntity> locations = locationDao.findByProperty(LocationEntity.class,"user", user);
                Map<Long, String> locationImageURLs = new HashMap<Long, String>();

                for (LocationEntity location : user.getLocations()) {
                    String imageURL = locationPhoto.getPhotoFromGoogle(location.getGoogleId());
                    locationImageURLs.put(location.getId(), imageURL);
                }

                session.setAttribute("imageUrls", locationImageURLs);
                session.setAttribute("userReviews", reviews);
                session.setAttribute("user", user);
            }
        }

        chain.doFilter(req, res);
    }

    public void destroy() {
        // If you have assigned any expensive resources as field of
        // this Filter class, then you could clean/close them here.
    }
}