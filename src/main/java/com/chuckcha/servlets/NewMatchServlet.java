package com.chuckcha.servlets;

import com.chuckcha.exceptions.ValidationException;
import com.chuckcha.service.NewMatchService;
import com.chuckcha.util.JspHelper;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/new-match")
public class NewMatchServlet extends HttpServlet {

    private NewMatchService newMatchService;

    @Override
    public void init(ServletConfig config) {
        newMatchService = (NewMatchService) config.getServletContext().getAttribute("newMatchService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JspHelper.getPath("new-match"))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String player1name = req.getParameter("player1");
        String player2name = req.getParameter("player2");
        String uuid = "";
        try {
            uuid = newMatchService.createNewMatch(player1name, player2name);
            resp.sendRedirect(req.getContextPath() + "/match-score?uuid=" + uuid);
        } catch (ValidationException e) {
            req.setAttribute("errors", e.getErrors());
            req.getRequestDispatcher(JspHelper.getPath("new-match")).forward(req, resp);
        }
    }
}