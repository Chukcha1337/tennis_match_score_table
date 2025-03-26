package com.chuckcha.servlets;

import com.chuckcha.dto.PlayerDto;
import com.chuckcha.exceptions.ValidationException;
import com.chuckcha.service.OngoingMatchesService;
import com.chuckcha.service.PlayerService;
import com.chuckcha.service.ValidatorService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/new-match")
public class NewMatchServlet extends HttpServlet {

    private PlayerService playerService;
    private OngoingMatchesService ongoingMatchesService;
    private ValidatorService validatorService;

    @Override
    public void init(ServletConfig config) {
        playerService = (PlayerService) config.getServletContext().getAttribute("playerService");
        ongoingMatchesService = (OngoingMatchesService) config.getServletContext().getAttribute("ongoingMatchesService");
        validatorService = (ValidatorService) config.getServletContext().getAttribute("validatorService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("new-match.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String firstPlayerName = req.getParameter("player1");
        String secondPlayerName = req.getParameter("player2");
        try {
            validatorService.validatePlayersNames(List.of(firstPlayerName, secondPlayerName));
            List<PlayerDto> playerDtoes = playerService.checkOrCreatePlayers(firstPlayerName, secondPlayerName);
            PlayerDto firstPlayerDto = playerDtoes.getFirst();
            PlayerDto secondPlayerDto = playerDtoes.getLast();
            String uuid = ongoingMatchesService.createNewMatch(firstPlayerDto, secondPlayerDto);
            resp.sendRedirect(req.getContextPath() + "/match-score?uuid=" + uuid);
        } catch (ValidationException e) {
            req.setAttribute("errors", e.getErrors());
            req.getRequestDispatcher("new-match.jsp").forward(req, resp);
        }
    }
}