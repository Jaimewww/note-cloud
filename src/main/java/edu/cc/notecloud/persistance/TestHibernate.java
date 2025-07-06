package edu.cc.notecloud.persistance;

import edu.cc.notecloud.persistance.util.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class TestHibernate {
    public static void main(String[] args) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        User u = new User();
        u.setNombre("Jaime");
        u.setEmail("jaimejhonle@gmail.com");
        u.setPassword("123456");

        session.save(u);

        tx.commit();
        session.close();
        System.out.println("Usuario guardado correctamente");
    }
}
