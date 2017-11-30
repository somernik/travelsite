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
import java.util.List;

/**
 * Gets Reviews info from db
 * Created by sarah on 10/5/2017.
 */
@WebServlet(
        urlPatterns = {"/prepareReview"}
)
public class PrepareReview extends HttpServlet{
    private final Logger logger = Logger.getLogger(this.getClass());
    private Validator validator = new Validator();
    private DateFormatter formatter = new DateFormatter();

    @Override
    protected void doGet (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        String id = req.getParameter("id");
        if (validator.isStringNumeric(id)) {
            // get values
            ReviewDao reviewDao = new ReviewDao();
            ReviewEntity review = reviewDao.find(ReviewEntity.class, Long.parseLong(id));
            req.setAttribute("id", review.getId());
            req.setAttribute("rating", review.getStars());
            req.setAttribute("body", review.getBody());
            req.setAttribute("date", formatter.convertLocalDateToString(review.getDate()));
        } else {
            // not valid id
        }

        //resp.sendRedirect("updateReview");
        RequestDispatcher dispatcher = req.getRequestDispatcher("updateReview.jsp");
        dispatcher.forward(req, resp);
    }
}
