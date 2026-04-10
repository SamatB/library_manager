package com.beganov.library.util;

import com.beganov.library.model.Author;
import com.beganov.library.model.Book;
import lombok.NoArgsConstructor;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@NoArgsConstructor
public class HibernateUtil {

    private static final SessionFactory SESSION_FACTORY = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            return new Configuration()
                    .addAnnotatedClass(Book.class)
                    .addAnnotatedClass(Author.class)
                    .buildSessionFactory();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка создания SessionFactory", e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return SESSION_FACTORY;
    }

    public static void shutdown() {
        SESSION_FACTORY.close();
    }
}
