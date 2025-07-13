package edu.cc.notecloud.config;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {
    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("NoteCloudPU");

    public static EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }
}
