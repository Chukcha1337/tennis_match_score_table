package com.chuckcha.servlets;

import com.chuckcha.entity.MatchScore;
import com.chuckcha.service.*;
import com.chuckcha.util.JspHelper;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/match-score/*")
public class MatchScoreServlet extends HttpServlet {

    private OngoingMatchesService ongoingMatchesService;
    private MatchScoreCalculationService matchScoreCalculationService;
    private FinishedMatchesPersistenceService finishedMatchesPersistenceService;
    private ValidatorService validatorService;

    @Override
    public void init(ServletConfig config) {
        ongoingMatchesService = (OngoingMatchesService) config.getServletContext().getAttribute("ongoingMatchesService");
        matchScoreCalculationService = (MatchScoreCalculationService) config.getServletContext().getAttribute("matchScoreCalculationService");
        finishedMatchesPersistenceService = (FinishedMatchesPersistenceService) config.getServletContext().getAttribute("finishedMatchesPersistenceService");
        validatorService = (ValidatorService) config.getServletContext().getAttribute("validatorService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uuid = req.getParameter("uuid");
        validatorService.validateUUID(uuid);
        MatchScore matchScore = ongoingMatchesService.getCurrentMatch(uuid);
        validatorService.validateMatchScore(matchScore, uuid);
        req.setAttribute("uuid", uuid);
        req.setAttribute("match", matchScore);
        req.getRequestDispatcher("match-score.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pointWinnerId = req.getParameter("pointWinnerId");
        String uuid = req.getParameter("uuid");
        validatorService.validateUUID(uuid);
        MatchScore matchScore = ongoingMatchesService.getCurrentMatch(uuid);
        validatorService.validateMatchScore(matchScore, uuid);
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
