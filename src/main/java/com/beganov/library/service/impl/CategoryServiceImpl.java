package com.beganov.library.service.impl;

import com.beganov.library.model.Category;
import com.beganov.library.service.CategoryService;
import com.beganov.library.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class CategoryServiceImpl implements CategoryService {

    @Override
    public Long save(Category category) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.persist(category);//save

            transaction.commit();
            return category.getId();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    @Override
    public Category getById(Long id) {
        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            Category category = session.find(Category.class, id);

            tx.commit();
            return category;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<Category> getAllCategories() {
        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            List<Category> categories = session.createQuery(
                    "select c from Category c",
                    Category.class
            ).list();

            tx.commit();
            return categories;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Category update(Long id, String newName) {
        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            Category category = session.find(Category.class, id);//find - найди
            if (category != null) {
                category.setName(newName);
            }
            tx.commit();
            return category;

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

            Category category = session.find(Category.class, id);
            if (category != null) {
                session.remove(category);
            }
            tx.commit();
            return "Товарищ session удалил категорию с id: " + id;

        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }
}
