package com.beganov.library.service.impl;

import com.beganov.library.model.Author;
import com.beganov.library.model.Book;
import com.beganov.library.model.Category;
import com.beganov.library.model.Profile;
import com.beganov.library.service.AuthorService;
import com.beganov.library.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class AuthorServiceImpl implements AuthorService {

    @Override
    public Long saveAuthor(Author author) {
        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            session.persist(author);

            tx.commit();
            return author.getId();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }

    @Override
    public Long saveAuthorWithBooks(
            String fullName,
            String email,
            String bio,
            List<BookDraft> books
    ) {
        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            Author author = new Author();
            author.setFullName(fullName);

            if (email != null) {
                Profile profile = new Profile();
                profile.setEmail(email);
                profile.setBio(bio);
                author.setProfile(profile);
            }

            if (books != null) {
                for (BookDraft draft : books) {
                    Book book = new Book();
                    book.setTitle(draft.title());

                    if (draft.categoryIds() != null) {
                        for (Long categoryId : draft.categoryIds()) {
                            Category category = session.find(Category.class, categoryId);
                            if (category == null) {
                                throw new IllegalArgumentException("Категория не найдена: " + categoryId);
                            }
                            book.addCategory(category);
                        }
                    }

                    author.addBook(book);
                }
            }

            session.persist(author);

            tx.commit();
            return author.getId();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }

    @Override
    public Author getById(Long id) {
        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            Author author = session.find(Author.class, id);

            tx.commit();
            return author;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<Author> getAllAuthors() {
        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            List<Author> authors = session.createQuery(
                    "select a from Author a",
                    Author.class
            ).list();

            tx.commit();
            return authors;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Author updateAuthor(Long id, String fullName) {
        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            Author author = session.find(Author.class, id);
            if (author != null) {
                author.setFullName(fullName);
            }

            tx.commit();
            return author;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }

    @Override
    public Long addBookToAuthor(Long authorId, String title, List<Long> categoryIds) {
        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            Author author = session.find(Author.class, authorId);
            if (author == null) {
                throw new IllegalArgumentException("Автор не найден: " + authorId);
            }

            Book book = new Book();
            book.setTitle(title);

            if (categoryIds != null) {
                for (Long categoryId : categoryIds) {
                    Category category = session.find(Category.class, categoryId);
                    if (category == null) {
                        throw new IllegalArgumentException("Категория не найдена: " + categoryId);
                    }
                    book.addCategory(category);
                }
            }

            author.addBook(book);

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
    public String delete(Long id) {
        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            Author author = session.find(Author.class, id);
            if (author != null) {
                session.remove(author);
            }

            tx.commit();
            return "Автор удален с id: " + id;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }
}
