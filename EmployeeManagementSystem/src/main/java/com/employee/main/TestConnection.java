package com.employee.main;

import jakarta.persistence.EntityManager;

public class TestConnection {

    public static void main(String[] args) {

        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        System.out.println("JPA Connection successful! EntityManager: " + em);
        em.close();
        JPAUtil.shutdown();
    }
}