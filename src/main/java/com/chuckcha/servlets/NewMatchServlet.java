package com.chuckcha.servlets;


import com.chuckcha.service.MatchScoreCalculationService;
import com.chuckcha.service.NewMatchService;
import com.chuckcha.service.OngoingMatchesService;
import com.chuckcha.util.JspHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

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

       //TODO: validate
        String uuid = newMatchService.createNewMatch(player1name, player2name);
        resp.sendRedirect(req.getContextPath() + "/match-score?uuid=" + uuid);
    }
}