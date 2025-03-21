package com.chuckcha.config;

//import com.chuckcha.util.ScriptReader;
import com.chuckcha.entity.Match;
import com.chuckcha.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import lombok.SneakyThrows;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;


@WebListener
public final class ApplicationInitializer implements ServletContextListener {

    @SneakyThrows
    @Override
    public void contextInitialized(ServletContextEvent sce) {
//        DatabaseConfig.loadProperties();
//        ScriptReader.executeInitScripts();
        ServletContext context = sce.getServletContext();
        ObjectMapper objectMapper = new ObjectMapper();
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        PlayerService playerService = new PlayerService(sessionFactory);
        OngoingMatchesService ongoingMatchesService = new OngoingMatchesService(sessionFactory);
        NewMatchService newMatchService = new NewMatchService(sessionFactory, ongoingMatchesService, playerService);
        FinishedMatchesPersistenceService finishedMatchesPersistenceService = new FinishedMatchesPersistenceService(sessionFactory);
        MatchScoreCalculationService matchScoreCalculationService = new MatchScoreCalculationService();

        context.setAttribute("objectMapper", objectMapper);
        context.setAttribute("sessionFactory", sessionFactory);
        context.setAttribute("ongoingMatchesService", ongoingMatchesService);
        context.setAttribute("newMatchService", newMatchService);
        context.setAttribute("finishedMatchesPersistenceService", finishedMatchesPersistenceService);
        context.setAttribute("matchScoreCalculationService", matchScoreCalculationService);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
//        ScriptReader.executeDeleteScripts();
//        DatabaseConfig.closeDataSource();
        try {
            Driver driver = DriverManager.getDriver("jdbc:postgresql://");
            DriverManager.deregisterDriver(driver);
        } catch (SQLException e) {
            System.err.println("Ошибка при удалении драйвера H2: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
