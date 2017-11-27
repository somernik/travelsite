package com.sarah.controller;

import com.sarah.entity.LocationEntity;
import com.sarah.entity.ReviewEntity;
import com.sarah.entity.User;
import com.sarah.persistence.GenericDao;
import com.sarah.persistence.ReviewDao;
import com.sarah.persistence.UserDao;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Deletes review
 * Created by sarah on 11/26/2017.
 */

@WebServlet(
        urlPatterns = {"/deleteReview"}
)
public class DeleteReview extends HttpServlet {
    private final Logger logger = Logger.getLogger(this.getClass());

    @Override
    protected void doGet (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String referrer = req.getParameter("referrer");
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        if (referrer.isEmpty() || referrer.length() < 1) {
            referrer = "user.jsp";
        }

        UserDao userDao = new UserDao();
        ReviewDao reviewDao = new ReviewDao();

        String id = req.getParameter("id");

        reviewDao.delete(ReviewEntity.class, Long.parseLong(id));
        logger.info("Deleted: " + id);

        // Update session variables
        User updatedUser = userDao.getUserById(user.getId());
        List<ReviewEntity> reviews = reviewDao.findByAndInitializeProperties("user", updatedUser);

        session.setAttribute("userReviews", reviews);
        session.setAttribute("user", updatedUser);

        resp.sendRedirect(referrer);
    }
}
