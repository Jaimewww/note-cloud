package edu.cc.notecloud.business;

import at.favre.lib.crypto.bcrypt.BCrypt;
import edu.cc.notecloud.config.JPAUtil;
import edu.cc.notecloud.entity.Role;
import edu.cc.notecloud.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;

public class SecurityFacade {

    public static User authenticate(String email, char[] rawPassword) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            User user = em.createQuery(
                            "FROM User WHERE email = :email", User.class)
                    .setParameter("email", email)
                    .getSingleResult();

            byte[] storedHash = user.getPasswordHash();
            boolean ok = BCrypt.verifyer()
                    .verify(rawPassword, storedHash)
                    .verified;
            return ok ? user : null;

        } catch (NoResultException e) {
            return null;
        } catch (NonUniqueResultException e) {
            throw new IllegalStateException("Email duplicado en la base de datos: " + email, e);
        } finally {
            em.close();
        }
    }

    public static void createUser(String nombre, String email, char[] rawPassword) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            User u = new User();
            u.setNombre(nombre);
            u.setEmail(email);

            byte[] hash = BCrypt.withDefaults().hash(12, rawPassword);
            u.setPasswordHash(hash);

            Role rol = em.find(Role.class, 0L);
            u.setRole(rol);
            u.setEnabled(true);

            em.persist(u);
            tx.commit();
        } catch (Exception ex) {
            if (tx.isActive()) tx.rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    public static User findUserByEmail(String email) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery(
                            "FROM User WHERE email = :email", User.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (NonUniqueResultException e) {
            throw new IllegalStateException("Email duplicado en la base de datos: " + email, e);
        } finally {
            em.close();
        }
    }
}
