package com.chuckcha.config;

//import com.chuckcha.util.ScriptReader;
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
        DatabaseConfig.loadProperties();
//        ScriptReader.executeInitScripts();
        ServletContext context = sce.getServletContext();
        ObjectMapper objectMapper = new ObjectMapper();

        context.setAttribute("objectMapper", objectMapper);
//        context.setAttribute("currencyService", currencyService);
//        context.setAttribute("exchangeService", exchangeService);
    }

    public static SessionFactory buildSessionFactory() {
        Configuration configuration = buildConfiguration();

        SessionFactory sessionFactory = configuration.buildSessionFactory();
//        registerListeners(sessionFactory);

        return sessionFactory;
    }

    public static Configuration buildConfiguration() {
        Configuration configuration = new Configuration();
        return configuration;
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
//        ScriptReader.executeDeleteScripts();
        DatabaseConfig.closeDataSource();
        try {
            Driver driver = DriverManager.getDriver("jdbc:postgresql://");
            DriverManager.deregisterDriver(driver);
        } catch (SQLException e) {
            System.err.println("Ошибка при удалении драйвера H2: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
