package com.beganov.library.service;

import com.beganov.library.model.Author;
import com.beganov.library.model.Book;
import com.beganov.library.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class AuthorServiceImpl implements AuthorService{

    @Override
    public Long saveAuthor(Author author) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.persist(author);//save

            transaction.commit();
            return author.getId();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    @Override
    public Author getById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.find(Author.class, id);//get
        }
    }

    @Override
    public Author updateAuthor(Long id, Long newId, String name) {
        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            Author author = session.find(Author.class, id);//find - найди
            if (author != null) {
                author.setId(newId);
                author.setName(name);
                session.merge(author);//update
            }

            tx.commit();
            return author;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    @Override
    public String delete(Long id) {
        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            Author author = session.find(Author.class, id);
            if (author != null) {
                session.remove(author);
            }
            tx.commit();
            return "Товарищ session удалил автора с id: " + id;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }
}
