package com.sarah.controller;

import com.sarah.entity.TagEntity;
import com.sarah.entity.User;
import com.sarah.persistence.TagDao;
import com.sarah.persistence.UserDao;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple servlet to welcome the user.
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

        RequestDispatcher dispatcher = req.getRequestDispatcher("explore.jsp");
        dispatcher.forward(req, resp);
    }

}