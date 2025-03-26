package com.chuckcha.util;

import com.chuckcha.exceptions.DatabaseException;
import jakarta.persistence.EntityManager;
import lombok.Cleanup;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public final class ScriptReader {

    private final SessionFactory sessionFactory;

    public ScriptReader(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void executeInitScripts() {
        executeScript("data.sql");
    }

    private void executeScript(String script) {
        try {
            String sql = readScriptFromFile(script);
            EntityManager entityManager = sessionFactory.getCurrentSession();
            entityManager.getTransaction().begin();
            entityManager.createNativeQuery(sql, void.class).executeUpdate();
            entityManager.getTransaction().commit();
        } catch (IOException | URISyntaxException | HibernateException e) {
            throw new DatabaseException("Database error");
        }
    }

    static String readScriptFromFile(String fileName) throws IOException, URISyntaxException {
        URL resource = ScriptReader.class.getClassLoader().getResource("scripts/" + fileName);
        if (resource == null) {
            throw new FileNotFoundException("Script file not found: " + fileName);
        }
        Path path = Paths.get(resource.toURI()).toAbsolutePath();
        @Cleanup BufferedReader reader = Files.newBufferedReader(path);
        return reader.lines()
                .filter(line -> !line.trim().isEmpty() && !line.trim().startsWith("--"))
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
