package com.chuckcha.util;


import com.chuckcha.entity.Player;
import lombok.experimental.UtilityClass;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@UtilityClass
public class HibernateUtil {

    public static SessionFactory buildSessionFactory() {
        Configuration configuration = buildConfiguration();
        configuration.configure();

        SessionFactory sessionFactory = configuration.buildSessionFactory();
//        registerListeners(sessionFactory);

        return sessionFactory;
    }

//    private static void registerListeners(SessionFactory sessionFactory) {
//        SessionFactoryImpl sessionFactoryImpl = sessionFactory.unwrap(SessionFactoryImpl.class);
//        EventListenerRegistry listenerRegistry = sessionFactoryImpl.getServiceRegistry().getService(EventListenerRegistry.class);
//        listenerRegistry.appendListeners(EventType.PRE_INSERT, new AuditTableListener());
//        listenerRegistry.appendListeners(EventType.PRE_DELETE, new AuditTableListener());
//    }

    public static Configuration buildConfiguration() {
        Configuration configuration = new Configuration();
        return configuration;
    }
}
