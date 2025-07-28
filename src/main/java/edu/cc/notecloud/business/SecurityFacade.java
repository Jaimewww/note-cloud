package edu.cc.notecloud.business;

import at.favre.lib.crypto.bcrypt.BCrypt;
import edu.cc.notecloud.security.Permission;
import edu.cc.notecloud.entity.Role;
import edu.cc.notecloud.entity.User;
import edu.cc.notecloud.security.ActionType;
import jakarta.ejb.Stateless;
import jakarta.persistence.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

@Stateless
public class SecurityFacade implements Serializable {

    @PersistenceContext(unitName = "NoteCloudPU")
    private EntityManager em;

    public User loginGoogleUser(String nombre, String email) {
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

            if (u == null) {
                u = new User();
                u.setEmail(email);
                u.setEnabled(true);
                Role rol = em.find(Role.class, 1L); // Rol por defecto
                u.setRole(rol);
                em.persist(u);
            }

            u.setNombre(nombre);
            return u;
        } catch (RuntimeException e) {
            throw e;
        }
    }

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

    public User createUser(String nombre, String email, char[] rawPassword) {
        try {
            User u = new User();
            u.setNombre(nombre);
            u.setEmail(email);

            byte[] hash = BCrypt.withDefaults().hash(12, rawPassword);
            u.setPasswordHash(hash);

            Role role = em.find(Role.class, 1L); // USER
            if (role == null) {
                throw new IllegalStateException("No existe el rol con ID 1 (USER). Verifica initial-data.sql");
            }
            u.setRole(role);

            u.setEnabled(true);

            em.persist(u);
            return u;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

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

    public User updateUser(User user) {
        try {
            User existingUser = em.find(User.class, user.getId());
            if (existingUser == null) {
                throw new IllegalArgumentException("Usuario no encontrado con ID: " + user.getId());
            }
            existingUser.setNombre(user.getNombre());
            existingUser.setEmail(user.getEmail());
            em.merge(existingUser);
            return existingUser;
        } catch (Exception ex) {
            throw ex;
        }
    }

    public void revokeGoogleToken(String token) throws IOException {
        if (token == null || token.isBlank()) return;
        var url = new URL("https://oauth2.googleapis.com/revoke?token=" + URLEncoder.encode(token, UTF_8));
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");

        try (InputStream in = conn.getInputStream();
             BufferedReader br = new BufferedReader(new InputStreamReader(in))) {

            StringBuilder json = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                json.append(line);
            }
        } finally {
            conn.disconnect();
        }
    }

    public List<Permission> getPermissionsOf(User user) {
        if (user == null || user.getRole() == null) {
            return Collections.emptyList();
        }

        Role role = em.find(Role.class, user.getRole().getId());
        if (role == null || role.getPermissions() == null) {
            return Collections.emptyList();
        }

        return new ArrayList<>(role.getPermissions());
    }

    public boolean hasPermission(User user, String resource, String action) {
        if (user == null || resource == null || action == null) return false;

        try {
            ActionType actionEnum = ActionType.valueOf(action.toUpperCase());
            return getPermissionsOf(user).stream()
                    .anyMatch(p ->
                            resource.equalsIgnoreCase(p.getResource()) &&
                                    actionEnum == p.getAction());
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public boolean isAdmin(User user) {
        if (user == null || user.getRole() == null) return false;
        return "ADMIN".equalsIgnoreCase(user.getRole().getName());
    }

    public boolean isUser(User user) {
        if (user == null || user.getRole() == null) return false;
        return "USER".equalsIgnoreCase(user.getRole().getName());
    }
}
