package com.chuckcha.servlets;

import com.chuckcha.entity.MatchScore;
import com.chuckcha.service.OngoingMatchesService;
import com.chuckcha.service.ValidatorService;
import com.chuckcha.util.JspHelper;
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
    private ValidatorService validatorService;

    @Override
    public void init(ServletConfig config) {
        ongoingMatchesService = (OngoingMatchesService) config.getServletContext().getAttribute("ongoingMatchesService");
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
        req.getRequestDispatcher("finished-match.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uuid = req.getParameter("uuid");
        validatorService.validateUUID(uuid);
        ongoingMatchesService.removeCurrentMatch(uuid);
        resp.sendRedirect(req.getContextPath() + "/index");
    }
}
