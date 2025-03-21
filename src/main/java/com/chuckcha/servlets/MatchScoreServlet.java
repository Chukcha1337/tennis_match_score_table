package com.chuckcha.servlets;

import com.chuckcha.entity.MatchScore;
import com.chuckcha.service.FinishedMatchesPersistenceService;
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

@WebServlet("/match-score/*")
public class MatchScoreServlet extends HttpServlet {

    private ObjectMapper mapper;
    private OngoingMatchesService ongoingMatchesService;
    private MatchScoreCalculationService matchScoreCalculationService;
    private FinishedMatchesPersistenceService finishedMatchesPersistenceService;
    private NewMatchService newMatchService;

    @Override
    public void init(ServletConfig config) {
        mapper = (ObjectMapper) config.getServletContext().getAttribute("objectMapper");
        ongoingMatchesService = (OngoingMatchesService) config.getServletContext().getAttribute("ongoingMatchesService");
        newMatchService = (NewMatchService) config.getServletContext().getAttribute("newMatchService");
        matchScoreCalculationService = (MatchScoreCalculationService) config.getServletContext().getAttribute("matchScoreCalculationService");
        finishedMatchesPersistenceService = (FinishedMatchesPersistenceService) config.getServletContext().getAttribute("finishedMatchesPersistenceService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uuid = req.getParameter("uuid");
        MatchScore matchScore = ongoingMatchesService.getCurrentMatch(uuid);
        req.setAttribute("uuid", uuid);
        req.setAttribute("match", matchScore);
        req.getRequestDispatcher(JspHelper.getPath("match-score"))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pointWinnerId = req.getParameter("pointWinnerId");
        String uuid = req.getParameter("uuid");
        MatchScore matchScore = ongoingMatchesService.getCurrentMatch(uuid);
        matchScoreCalculationService.updateScore(matchScore, pointWinnerId);
        boolean isGameFinished = matchScore.isGameFinished();
        req.setAttribute("uuid", uuid);
        req.setAttribute("match", matchScore);
        if (isGameFinished) {
            finishedMatchesPersistenceService.save(matchScore);
            resp.sendRedirect(req.getContextPath() + "/finished-match?uuid=" + uuid);
        } else resp.sendRedirect(req.getContextPath() + "/match-score?uuid=" + uuid);
    }
}
