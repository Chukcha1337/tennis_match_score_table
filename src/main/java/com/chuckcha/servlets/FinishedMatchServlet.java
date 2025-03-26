package com.chuckcha.servlets;

import com.chuckcha.entity.MatchScore;
import com.chuckcha.service.FinishedMatchesPersistenceService;
import com.chuckcha.service.OngoingMatchesService;
import com.chuckcha.service.ValidatorService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/finished-match/*")
public class FinishedMatchServlet extends HttpServlet {

    private OngoingMatchesService ongoingMatchesService;
    private ValidatorService validatorService;
    private FinishedMatchesPersistenceService finishedMatchesPersistenceService;

    @Override
    public void init(ServletConfig config) {
        ongoingMatchesService = (OngoingMatchesService) config.getServletContext().getAttribute("ongoingMatchesService");
        validatorService = (ValidatorService) config.getServletContext().getAttribute("validatorService");
        finishedMatchesPersistenceService = (FinishedMatchesPersistenceService) config.getServletContext().getAttribute("finishedMatchesPersistenceService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uuid = req.getParameter("uuid");
        validatorService.validateUUID(uuid);
        MatchScore matchScore = ongoingMatchesService.getCurrentMatch(uuid);
        validatorService.validateMatchScore(matchScore, uuid);
        req.setAttribute("uuid", uuid);
        req.setAttribute("match", matchScore);
        req.getRequestDispatcher("finished-match.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String uuid = req.getParameter("uuid");
        validatorService.validateUUID(uuid);
        MatchScore matchScore = ongoingMatchesService.getCurrentMatch(uuid);
        if (matchScore != null) {
            finishedMatchesPersistenceService.save(matchScore);
            ongoingMatchesService.removeCurrentMatch(uuid);
        }
        resp.sendRedirect(req.getContextPath() + "/index");
    }
}
