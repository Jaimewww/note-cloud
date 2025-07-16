package edu.cc.notecloud.business;

import at.favre.lib.crypto.bcrypt.BCrypt;
import edu.cc.notecloud.entity.Role;
import edu.cc.notecloud.entity.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import static java.nio.charset.StandardCharsets.UTF_8;


@ApplicationScoped
public class SecurityFacade {

    @PersistenceContext(unitName = "NoteCloudPU")
    private EntityManager em;

    @Transactional
    public User loginGoogleUser(String nombre,
                                       String email) {
        try {

            User u;
            try {
                u = em.createQuery("""
                        SELECT u
                        FROM User u
                        WHERE u.email = :mail
                        """, User.class)
                        .setParameter("mail", email)
                        .getSingleResult();
            } catch (NoResultException e) {
                u = null;
            }

            if (u == null) {                 // Nuevo usuario
                u = new User();
                u.setEmail(email);
                u.setEnabled(true);
                Role rol = em.find(Role.class, 1L);
                u.setRole(rol);
                em.persist(u);
            }

            // Datos que pueden cambiar con Google
            //u.setGoogleId(googleId);
            u.setNombre(nombre);
            //u.setImageUrl(fotoUrl);

            return u;                        // entidad administrada
        } catch (RuntimeException e) {
            throw e;                         // propagar o envolver
        }
    }

    @Transactional
    public User authenticate(String email, char[] rawPassword) {
        try {
            User user = em.createQuery(
                            "SELECT u FROM User u WHERE u.email = :email", User.class)
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
        }
    }

    @Transactional
    public User createUser(String nombre, String email, char[] rawPassword) {
        try {

            User u = new User();
            u.setNombre(nombre);
            u.setEmail(email);

            byte[] hash = BCrypt.withDefaults().hash(12, rawPassword);
            u.setPasswordHash(hash);

            Role rol = em.find(Role.class, 1L);
            u.setRole(rol);
            u.setEnabled(true);

            em.persist(u);
            return u;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Transactional
    public User findUserByEmail(String email) {
        try {
            return em.createQuery(
                            "SELECT u FROM User u WHERE u.email = :email", User.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (NonUniqueResultException e) {
            throw new IllegalStateException("Email duplicado en la base de datos: " + email, e);
        }
    }

    @Transactional
    public User updateUser(User user) {

        try {
            User existingUser = em.find(User.class, user.getId());
            if (existingUser == null) {
                throw new IllegalArgumentException("Usuario no encontrado con ID: " + user.getId());
            }
            existingUser.setNombre(user.getNombre());
            existingUser.setEmail(user.getEmail());
            //existingUser.setImageUrl(user.getImageUrl());
            em.merge(existingUser);
            return existingUser;
        } catch (Exception ex) {
            throw ex;
        }
    }

    public void revokeGoogleToken(String token) throws IOException {
        if (token == null || token.isBlank()) return;
        var url  = new URL("https://oauth2.googleapis.com/revoke?token=" + URLEncoder.encode(token, UTF_8));
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");

        try (InputStream in = conn.getInputStream();
             BufferedReader br = new BufferedReader(new InputStreamReader(in))) {

            StringBuilder json = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                json.append(line);
            }
            // procesar json…
        } finally {
            conn.disconnect();   // sigue siendo necesario
        }
    }

}
