package com.chuckcha.servlets;

import com.chuckcha.entity.Page;
import com.chuckcha.service.*;
import com.chuckcha.util.JspHelper;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/matches")
public class MatchesServlet extends HttpServlet {

    private static final int DEFAULT_PAGE_SIZE = 5;

    private MatchesService matchesService;
    private ValidatorService validatorService;

    @Override
    public void init(ServletConfig config) {
        matchesService = (MatchesService) config.getServletContext().getAttribute("matchesService");
        validatorService = (ValidatorService) config.getServletContext().getAttribute("validatorService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pageParam = req.getParameter("page");
        int pageNumber = validatorService.validatePageParam(pageParam);
        String name = req.getParameter("filter_by_player_name");
        Page currentPage = matchesService.get(name, pageNumber, DEFAULT_PAGE_SIZE);
        req.setAttribute("name", name);
        req.setAttribute("pageNumber", pageNumber);
        req.setAttribute("currentPage", currentPage);
        req.setAttribute("rows", DEFAULT_PAGE_SIZE);
        req.getRequestDispatcher(JspHelper.getPath("matches"))
                .forward(req, resp);
    }
}
