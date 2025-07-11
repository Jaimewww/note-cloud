package edu.cc.notecloud.business;

import at.favre.lib.crypto.bcrypt.BCrypt;
import edu.cc.notecloud.entity.Role;
import edu.cc.notecloud.entity.User;
import edu.cc.notecloud.config.HibernateUtils;
import org.hibernate.NonUniqueResultException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class SecurityFacade {
    public static User authenticate(String email, char[] rawPassword) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {

            User user = session.createQuery(
                            "FROM User WHERE email = :email", User.class)
                    .setParameter("email", email)
                    .uniqueResult();

            if (user == null) return null;

            byte[] storedHash = user.getPasswordHash();   // hash guardado en BD
            boolean ok = BCrypt.verifyer()
                    .verify(rawPassword, storedHash)  // (plano, hash)
                    .verified;
            return ok ? user : null;

        } catch (NonUniqueResultException e) {
            throw new IllegalStateException(
                    "Email duplicado en la base de datos: " + email, e);
        }
    }

    public static void createUser(String nombre, String email, char[] rawPassword) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                User u = new User();
                u.setNombre(nombre);
                u.setEmail(email);

                /* 1. Generar el hash */
                byte[] hash = BCrypt.withDefaults().hash(12, rawPassword);
                u.setPasswordHash(hash);

                /* 2. Datos adicionales */
                Role rol = session.find(Role.class, 0L);
                u.setRole(rol);
                u.setEnabled(true);

                session.persist(u);
                tx.commit();
            } catch (Exception ex) {
                tx.rollback();
                throw ex;
            }
        }
    }
}
