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

    public Page get(String name, int page, int pageSize) {
        EntityManager entityManager = sessionFactory.getCurrentSession();
        MatchRepository matchRepository = new MatchRepository(entityManager);
        int offset = (page - 1) * pageSize;
        boolean isNameEmpty = (name == null || name.isEmpty());
        entityManager.getTransaction().begin();
        List<Match> byNamePaginated = isNameEmpty
                ? matchRepository.findPaginated(offset, pageSize)
                : matchRepository.findByNamePaginated(name, offset, pageSize);
        Long rowsAmount = isNameEmpty
                ? matchRepository.getRowsAmount()
                : matchRepository.getRowsAmount(name);
        List<MatchDto> matchDtos = byNamePaginated.stream().map(DtoMapper::toDto).toList();
        int result = (int) Math.ceil((double) rowsAmount / pageSize);
        Page currentPage = new Page(matchDtos, result);
        entityManager.getTransaction().commit();
        return currentPage;
    }
}
