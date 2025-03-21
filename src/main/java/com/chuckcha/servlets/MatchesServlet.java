package com.chuckcha.servlets;

import com.chuckcha.service.MatchScoreCalculationService;
import com.chuckcha.service.NewMatchService;
import com.chuckcha.service.OngoingMatchesService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;

@WebServlet("/matches")
public class MatchesServlet extends HttpServlet {

    private ObjectMapper mapper;
    private OngoingMatchesService ongoingMatchesService;
    private MatchScoreCalculationService matchScoreCalculationService;
    private NewMatchService newMatchService;

    @Override
    public void init(ServletConfig config) {
        mapper = (ObjectMapper) config.getServletContext().getAttribute("objectMapper");
        ongoingMatchesService = (OngoingMatchesService) config.getServletContext().getAttribute("ongoingMatchesService");
        newMatchService = (NewMatchService) config.getServletContext().getAttribute("newMatchService");
        matchScoreCalculationService = (MatchScoreCalculationService) config.getServletContext().getAttribute("matchScoreCalculationService");
    }
}
