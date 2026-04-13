package com.beganov.library.service.impl;

import com.beganov.library.model.Author;
import com.beganov.library.model.Profile;
import com.beganov.library.service.ProfileService;
import com.beganov.library.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ProfileServiceImpl implements ProfileService {
    @Override
    public Long save(Profile profile) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.persist(profile);//save

            transaction.commit();
            return profile.getId();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    @Override
    public Profile getById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.find(Profile.class, id);//get
        }
    }

    @Override
    public List<Profile> getAllCategories() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("select p from Profile p", Profile.class).list();//get
        }
    }

    @Override
    public Profile updateEmail(Long id, String newEmail) {
        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            Profile profile = session.find(Profile.class, id);//find - найди
            if (profile != null) {
                profile.setEmail(newEmail);
            }
            tx.commit();
            return profile;

        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    @Override
    public Profile updateBio(Long id, String newBio) {
        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            Profile profile = session.find(Profile.class, id);//find - найди
            if (profile != null) {
                profile.setBio(newBio);
            }
            tx.commit();
            return profile;

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

            Profile profile = session.find(Profile.class, id);
            if (profile != null) {
                session.remove(profile);
            }
            tx.commit();
            return "Товарищ session удалил автора с id: " + id;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }
}
