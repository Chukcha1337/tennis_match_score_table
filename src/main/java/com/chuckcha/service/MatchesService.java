package com.chuckcha.service;

import com.chuckcha.dto.MatchDto;
import com.chuckcha.entity.Match;
import com.chuckcha.entity.Page;
import com.chuckcha.repository.MatchRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class MatchesService implements Service {

    private final SessionFactory sessionFactory;

    public MatchesService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Page get(String name, int page, int pageSize) {
        Session session = sessionFactory.getCurrentSession();
        MatchRepository matchRepository = new MatchRepository(session);
        int offset = (page - 1) * pageSize;
        session.beginTransaction();
        List<Match> byNamePaginated;
        Long rowsAmount;
        if (name == null || name.isEmpty()) {
            byNamePaginated = matchRepository.findPaginated(offset, pageSize);
            rowsAmount = matchRepository.getRowsAmount();
        } else {
            byNamePaginated = matchRepository.findByNamePaginated(name, offset, pageSize);
            rowsAmount = matchRepository.getRowsAmount(name);
        }
        List<MatchDto> matchDtos = byNamePaginated.stream().map(match -> MatchDto.builder()
                .id(match.getId())
                .firstPlayer(match.getFirstPlayer())
                .secondPlayer(match.getSecondPlayer())
                .winner(match.getWinner())
                .build()).toList();
        int result = (int) Math.ceil((double) rowsAmount / pageSize);
        Page currentPage = new Page(matchDtos, result);
        session.getTransaction().commit();
        return currentPage;
    }
}
