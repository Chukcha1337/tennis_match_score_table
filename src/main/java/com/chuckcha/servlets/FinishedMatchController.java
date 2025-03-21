package com.chuckcha.servlets;

import com.chuckcha.entity.MatchScore;
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

@WebServlet("/finished-match/*")
public class FinishedMatchController extends HttpServlet {

    private OngoingMatchesService ongoingMatchesService;

    @Override
    public void init(ServletConfig config) {
        ongoingMatchesService = (OngoingMatchesService) config.getServletContext().getAttribute("ongoingMatchesService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uuid = req.getParameter("uuid");
        MatchScore matchScore = ongoingMatchesService.getCurrentMatch(uuid);
        int setsNumber = matchScore.getSetsNumber();
        req.setAttribute("uuid", uuid);
        req.setAttribute("match", matchScore);
        req.setAttribute("setsNumber", matchScore);
        req.getRequestDispatcher(JspHelper.getPath("finished-match"))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uuid = req.getParameter("uuid");
            ongoingMatchesService.removeCurrentMatch(uuid);
        resp.sendRedirect(req.getContextPath() + "/index");
    }
}
