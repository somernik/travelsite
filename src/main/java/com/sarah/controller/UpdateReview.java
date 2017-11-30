package com.sarah.controller;

import com.sarah.entity.ReviewEntity;
import com.sarah.entity.User;
import com.sarah.persistence.ReviewDao;
import com.sarah.persistence.UserDao;
import com.sarah.utility.DateFormatter;
import com.sarah.utility.Validator;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/**
 * Gets Reviews info from db
 * Created by sarah on 10/5/2017.
 */
@WebServlet(
        urlPatterns = {"/updateReview"}
)
public class UpdateReview extends HttpServlet{
    private final Logger logger = Logger.getLogger(this.getClass());
    private Validator validator = new Validator();

    @Override
    protected void doGet (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        String id = req.getParameter("reviewId");
        logger.info(id);
        User user = (User) session.getAttribute("user");
        if (validator.isStringNumeric(id)) {
            logger.info("id is numeric");
            // get values
            ReviewDao reviewDao = new ReviewDao();
            ReviewEntity review = reviewDao.find(ReviewEntity.class, Long.parseLong(id));
            String rating = req.getParameter("rating");
            String body = req.getParameter("review");
            String date = req.getParameter("date");
            logger.info("rating: "+ rating);
            logger.info("body: " + body);
            logger.info("date: " + date);
            logger.info("review: " + review.getId());
            boolean updated = false;
            if (rating != null && validator.isStringNumeric(rating)) {
                review.setStars(Integer.parseInt(rating));
                updated = true;
            }
            if (body != null && body.length() > 1) {
                review.setBody(validator.cleanInput(body));
                updated = true;
            }
            if (date != null) {
                DateFormatter formatter = new DateFormatter();
                LocalDate localDate = formatter.convertStringToLocalDate(date);
                review.setDate(localDate);
                updated = true;
            }
            logger.info("before update: " + review.getId());
            if (updated) {

                reviewDao.update(review);
                logger.info("before update: " + review.getId());
                List<ReviewEntity> reviews = reviewDao.findByAndInitializeProperties("user", user);
                logger.info("all reviews: " + reviews);
                session.setAttribute("userReviews", reviews);
            }
        } else {
            // not valid id
        }

        // TODO update user session

        resp.sendRedirect("user.jsp");

        //RequestDispatcher dispatcher = req.getRequestDispatcher("user.jsp");
        //dispatcher.forward(req, resp);
    }
}
