package com.beganov.library.service.impl;

import com.beganov.library.model.Author;
import com.beganov.library.model.Book;
import com.beganov.library.service.AuthorService;
import com.beganov.library.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class AuthorServiceImpl implements AuthorService {

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
    public List<Author> getAllAuthors() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("select a from Author a", Author.class).list();//get
        }
    }

    @Override
    public Author updateAuthor(Long id, String newFullName) {
        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            Author author = session.find(Author.class, id);//find - найди
            if (author != null) {
                author.setFullName(newFullName);
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

    @Override
    public Author assignBook(Long authorId, Long bookId) {
        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            Author newAuthor = session.find(Author.class, authorId);
            Book book = session.find(Book.class, bookId);

            if (newAuthor == null) {
                throw new IllegalArgumentException("Автор не найден: " + authorId);
            }

            if (book == null) {
                throw new IllegalArgumentException("Книга не найдена: " + bookId);
            }

            Author oldAuthor = book.getAuthor();
            if (oldAuthor != null && oldAuthor != newAuthor) {
                oldAuthor.getBooks().remove(book);
            }

            if (!newAuthor.getBooks().contains(book)) {
                newAuthor.getBooks().add(book);
            }

            book.setAuthor(newAuthor);

            tx.commit();
            return newAuthor;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }
}
