package com.sarah.controller;

import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

/**
 * Created by sarah on 10/5/2017.
 */
@WebServlet(
        urlPatterns = {"/addReview"}
)
public class AddReview extends HttpServlet {
    private final Logger log = Logger.getLogger(this.getClass());

    @Override
    protected void doGet (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        // TODO validate input
        log.info(req.getParameter("placeId"));
        log.info(req.getParameter("date"));
        log.info(req.getParameter("review"));
        log.info(req.getParameter("rating"));

        log.info(req.getParameter("goodTags"));
        log.info(req.getParameter("badTags"));

        String[] badTags = req.getParameter("badTags").split("\\;");
        for (String tag: badTags) {
            // TODO process each tag
            log.info(tag);
        }

        RequestDispatcher dispatcher = req.getRequestDispatcher("detail.jsp");
        dispatcher.forward(req, resp);
    }
}
