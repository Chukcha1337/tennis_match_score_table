package com.chuckcha.service;

import com.chuckcha.dto.MatchDto;
import com.chuckcha.entity.Match;
import com.chuckcha.entity.Page;
import com.chuckcha.mapper.DtoMapper;
import com.chuckcha.repository.MatchRepository;
import jakarta.persistence.EntityManager;
import org.hibernate.SessionFactory;

import java.util.List;

public class MatchesService {

    private final SessionFactory sessionFactory;

    public MatchesService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Page get(String name, int userRequiredPageNumber, int pageSize) {
        EntityManager entityManager = sessionFactory.getCurrentSession();
        MatchRepository matchRepository = new MatchRepository(entityManager);
        boolean isNameEmpty = (name == null || name.isEmpty());
        entityManager.getTransaction().begin();
        Long rowsAmount = isNameEmpty
                ? matchRepository.getRowsAmount()
                : matchRepository.getRowsAmount(name);
        int result = (int) Math.ceil((double) rowsAmount / pageSize);
        int reqPageNumber = Math.min(result, userRequiredPageNumber);
        int offset = (reqPageNumber - 1) * pageSize;
        List<Match> byNamePaginated = isNameEmpty
                ? matchRepository.findPaginated(offset, pageSize)
                : matchRepository.findByNamePaginated(name, offset, pageSize);
        List<MatchDto> matchDtos = byNamePaginated.stream().map(DtoMapper::toDto).toList();
        Page currentPage = new Page(matchDtos, result, reqPageNumber);
        entityManager.getTransaction().commit();
        return currentPage;
    }
}
