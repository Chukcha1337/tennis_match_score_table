package com.chuckcha.config;

//import com.chuckcha.util.ScriptReader;
import com.chuckcha.service.*;
import com.chuckcha.util.ScriptReader;
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
        ServletContext context = sce.getServletContext();
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        ScriptReader scriptReader = new ScriptReader(sessionFactory);
        scriptReader.executeInitScripts();
        PlayerService playerService = new PlayerService(sessionFactory);
        MatchesService matchesService = new MatchesService(sessionFactory);
        ValidatorService validatorService = new ValidatorService();
        FinishedMatchesPersistenceService finishedMatchesPersistenceService = new FinishedMatchesPersistenceService(sessionFactory);
        OngoingMatchesService ongoingMatchesService = new OngoingMatchesService();
        MatchScoreCalculationService matchScoreCalculationService = new MatchScoreCalculationService();

        context.setAttribute("sessionFactory", sessionFactory);
        context.setAttribute("ongoingMatchesService", ongoingMatchesService);
        context.setAttribute("finishedMatchesPersistenceService", finishedMatchesPersistenceService);
        context.setAttribute("matchScoreCalculationService", matchScoreCalculationService);
        context.setAttribute("matchesService", matchesService);
        context.setAttribute("validatorService", validatorService);
        context.setAttribute("playerService", playerService);
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
