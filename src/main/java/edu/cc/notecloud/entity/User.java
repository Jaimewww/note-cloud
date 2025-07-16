package edu.cc.notecloud.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name="email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", columnDefinition = "VARBINARY(60)")
    private byte[] passwordHash;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
    private Role role;

    @Column(name = "enabled",nullable = false)
    private boolean enabled;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String name) {
        this.nombre = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getPasswordHash() {
        return passwordHash != null ? passwordHash.clone() : null;
    }

    public void setPasswordHash(byte[] hash) {
        if (hash == null || hash.length == 0) {
            throw new IllegalArgumentException("Hash no puede ser nulo / vacío");
        }
        this.passwordHash = hash.clone();
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Role getRole() { return role; }

    public void setRole(Role role) { this.role = role; }
}