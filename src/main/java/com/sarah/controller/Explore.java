package com.sarah.controller;

import com.sarah.entity.TagEntity;
import com.sarah.persistence.TagDao;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Prepares tags for explore page.
 * @author somernik
 */
@WebServlet(
        urlPatterns = {"/explore"}
)
public class Explore extends HttpServlet {
    private final Logger logger = Logger.getLogger(this.getClass());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TagDao tagDao = new TagDao();
        List<TagEntity> tags = tagDao.findAll(TagEntity.class);

        req.setAttribute("tags", tags);
        // TODO add any search criteria to google map

        RequestDispatcher dispatcher = req.getRequestDispatcher("explore.jsp");
        dispatcher.forward(req, resp);
    }

}