package com.shu.login.web.controller;

import com.shu.login.domain.Score;
import com.shu.login.domain.User;
import com.shu.login.service.IUserService;
import com.shu.login.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Qjkobe on 2016/2/21.
 */
public class QueryServlet extends HttpServlet{
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user=(User)request.getSession().getAttribute("user");
        String username=user.getUserName();

        IUserService service = new UserServiceImpl();

        Score score=service.queryScore(username);
        String scoreS=score.getScore();
        request.setAttribute("scoreS", scoreS);
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
