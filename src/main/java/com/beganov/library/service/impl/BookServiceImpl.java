package com.beganov.library.service.impl;

import com.beganov.library.model.Author;
import com.beganov.library.model.Book;
import com.beganov.library.model.Category;
import com.beganov.library.service.BookService;
import com.beganov.library.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class BookServiceImpl implements BookService {

    @Override
    public Long save(Book book) {

        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            if (book.getCategories() == null) {
                book.setCategories(new ArrayList<>());
            }

            session.persist(book);//save
            transaction.commit();
            return book.getId();

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    @Override
    public Long save(Long authorId, String title, List<Long> categoryIds) {
        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            Author author = session.find(Author.class, authorId);
            if (author == null) {
                throw new IllegalArgumentException("Автор не найден: " + authorId);
            }

            Book book = new Book();
            book.setTitle(title);

            author.add(book);

            if (categoryIds != null) {
                for (Long categoryId : categoryIds) {
                    Category category = session.find(Category.class, categoryId);
                    if (category == null) {
                        throw new IllegalArgumentException("Категория не найдена: " + categoryId);
                    }
                    book.addCategory(category);
                }
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
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Book book = session.find(Book.class, id);//get
            return book;
        }
    }

    @Override
    public List<Book> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Book> books = session.createQuery("select b from Book b", Book.class).list();
            return books;
        }
    }

    @Override
    public Book update(Long id, String title) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Book book = session.find(Book.class, id);
            if (book != null) {
                book.setTitle(title);
            }
            transaction.commit();
            return book;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    @Override
    public Book updateCategories(Long bookId, List<Long> categoryIds) {
        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            Book book = session.find(Book.class, bookId);
            if (book == null) {
                tx.commit();
                return null;
            }

            List<Category> oldCategories = new ArrayList<>(book.getCategories());
            for (Category category : oldCategories) {
                book.removeCategory(category);
            }

            if (categoryIds != null) {
                for (Long categoryId : categoryIds) {
                    Category category = session.find(Category.class, categoryId);
                    if (category == null) {
                        throw new IllegalArgumentException("Категория не найдена: " + categoryId);
                    }
                    book.addCategory(category);
                }
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
