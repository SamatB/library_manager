package com.beganov.library.service.impl;

import com.beganov.library.model.Profile;
import com.beganov.library.service.ProfileService;
import com.beganov.library.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ProfileServiceImpl implements ProfileService {

    @Override
    public Long save(Profile profile) {
        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            session.persist(profile);

            tx.commit();
            return profile.getId();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }

    @Override
    public Profile getById(Long id) {
        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            Profile profile = session.find(Profile.class, id);

            tx.commit();
            return profile;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<Profile> getAllProfiles() {
        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            List<Profile> profiles = session.createQuery(
                    "select p from Profile p",
                    Profile.class
            ).list();

            tx.commit();
            return profiles;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Profile updateEmail(Long id, String newEmail) {
        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            Profile profile = session.find(Profile.class, id);
            if (profile != null) {
                profile.setEmail(newEmail);
            }

            tx.commit();
            return profile;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }

    @Override
    public Profile updateBio(Long id, String newBio) {
        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            Profile profile = session.find(Profile.class, id);
            if (profile != null) {
                profile.setBio(newBio);
            }

            tx.commit();
            return profile;
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

            Profile profile = session.find(Profile.class, id);
            if (profile != null) {
                session.remove(profile);
            }

            tx.commit();
            return "Профиль удален с id: " + id;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }
}
