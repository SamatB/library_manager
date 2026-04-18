package com.beganov.library.service.impl;

import com.beganov.library.model.Author;
import com.beganov.library.model.Book;
import com.beganov.library.service.BookService;
import com.beganov.library.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class BookServiceImpl implements BookService {

    @Override
    public Long save(Book book) {
        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            if (book.getCategories() == null) {
                book.setCategories(new ArrayList<>());
            }

            session.persist(book);

            tx.commit();
            return book.getId();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }

    @Override
    public Book getById(Long id) {
        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            Book book = session.find(Book.class, id);

            tx.commit();
            return book;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<Book> getAll() {
        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            List<Book> books = session.createQuery(
                    "select b from Book b",
                    Book.class
            ).list();

            tx.commit();
            return books;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Book updateTitle(Long id, String title) {
        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            Book book = session.find(Book.class, id);
            if (book != null) {
                book.setTitle(title);
            }

            tx.commit();
            return book;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }

    @Override
    public Book changeAuthor(Long bookId, Long newAuthorId) {
        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            Book book = session.find(Book.class, bookId);
            Author newAuthor = session.find(Author.class, newAuthorId);

            if (book == null) {
                throw new IllegalArgumentException("Книга не найдена: " + bookId);
            }

            if (newAuthor == null) {
                throw new IllegalArgumentException("Автор не найден: " + newAuthorId);
            }

            Author oldAuthor = book.getAuthor();
            if (oldAuthor != null) {
                oldAuthor.getBooks().remove(book);
            }

            newAuthor.addBook(book);

            tx.commit();
            return book;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
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
            return "Книга удалена по id: " + id;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }

    @Override
    public List<Book> findByTitle(String name) {
        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            List<Book> books = session.createQuery(
                            "select b from Book b where b.title = :muha", Book.class)
                    .setParameter("muha", name)
                    .list();

            tx.commit();
            return books;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<Book> findByAuthor(String authorName) {
        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            List<Book> books = session.createQuery(
                            "select b from Book b where b.author.fullName = :authorName", Book.class)
                    .setParameter("authorName", authorName)
                    .list();

            tx.commit();
            return books;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<Book> findByCategory(String category) {
        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            List<Book> books = session.createQuery(
                            "select b from Book b join b.categories c where c.name = :category", Book.class)
                    .setParameter("category", category)
                    .list();

            tx.commit();
            return books;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<Book> findAllWithAuthor() {
        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            List<Book> books = session.createQuery(
                    "select b from Book b join fetch b.author",
                    Book.class
            ).list();

            tx.commit();
            return books;
        } catch (Exception e) {
            throw e;
        }
    }
}
