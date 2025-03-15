package com.chuckcha.servlets;

import com.chuckcha.entity.CurrentMatch;
import com.chuckcha.service.OngoingMatchesService;
import com.chuckcha.util.JspHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.Servlet;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/match-score")
public class MatchScoreServlet extends HttpServlet {

    private ObjectMapper mapper;

    @Override
    public void init(ServletConfig config) {
        mapper = (ObjectMapper) config.getServletContext().getAttribute("objectMapper");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uuidString = req.getParameter("uuid");
        UUID uuid = UUID.fromString(uuidString);

        req.setAttribute("uuid", uuid);
        req.getRequestDispatcher(JspHelper.getPath("new-match"))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {



//        CurrentMatch currentMatch = OngoingMatchesService.getInstance().getCurrentMatch(uuid);


    }
}
