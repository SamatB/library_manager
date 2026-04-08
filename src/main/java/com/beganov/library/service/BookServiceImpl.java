package com.beganov.library.service;

import com.beganov.library.model.Book;
import com.beganov.library.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class BookServiceImpl implements BookService {

    @Override
    public Long save(Book book) {

        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.persist(book);//save

            transaction.commit();
            return book.getId();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    @Override
    public Book getById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.find(Book.class, id);//get
        }
    }

    @Override
    public Book update(Long id, String title, String author) {
        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            Book book = session.find(Book.class, id);//find - найди
            if (book != null) {
                book.setTitle(title);
                book.setAuthor(author);
                session.merge(book);//update
            }

            tx.commit();
            return book;
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

            Book book = session.find(Book.class, id);
            if (book != null) {
                session.remove(book);
            }
            tx.commit();
            return "Товарищ session удалил книгу по id: " + id;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }
}
