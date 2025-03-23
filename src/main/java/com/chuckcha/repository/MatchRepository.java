package com.chuckcha.repository;

import com.chuckcha.entity.Match;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Map;

public class MatchRepository extends BaseRepository<Match> {

    public MatchRepository(EntityManager entityManager) {
        super(entityManager);
    }

    public List<Match> findPaginated(int offset, int pageSize) {
        return findResultList(QueryBuilder.<Match>builder()
                .query("select m from Match m order by m.id desc")
                .clazz(Match.class)
                .offset(offset)
                .pageSize(pageSize)
                .graphName("WithPlayers")
                .build());
    }

    public List<Match> findByNamePaginated(String name, int offset, int pageSize) {
        return findResultList(QueryBuilder.<Match>builder()
                .query("select m from Match m where lower(m.firstPlayer.name) like lower(:name) " +
                       "or lower(m.secondPlayer.name) like lower(:name) order by m.id desc")
                .clazz(Match.class)
                .parameters(Map.of("name", "%" + name + "%"))
                .offset(offset)
                .pageSize(pageSize)
                .graphName("WithPlayers")
                .build());
    }

    public Long getRowsAmount() {
        return getCount(QueryBuilder.<Long>builder()
                .query("select count(m) from Match m")
                .build()
        );
    }

    public Long getRowsAmount(String name) {
        return getCount(QueryBuilder.<Long>builder()
                .query("select count(m) from Match m where lower(m.firstPlayer.name) like lower(:name) " +
                       "or lower(m.secondPlayer.name) like lower(:name)")
                .parameters(Map.of("name", "%" + name + "%"))
                .build());

    }
}

