package com.employee.dao;

import com.employee.entity.Department;
import com.employee.main.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;

public class DepartmentDAO {

    public void addDepartment(Department department) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(department);
            tx.commit();
            System.out.println("Department added: " + department.getName());
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            System.err.println("Failed to add department: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public Department getDepartmentById(int id) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            Department dept = em.find(Department.class, id);
            if (dept == null) System.out.println("No department found with id: " + id);
            return dept;
        } finally {
            em.close();
        }
    }

    public List<Department> getAllDepartments() {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery("SELECT d FROM Department d", Department.class)
                     .getResultList();
        } finally {
            em.close();
        }
    }

    public void updateDepartment(int id, String newName, String newLocation) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Department dept = em.find(Department.class, id);
            if (dept != null) {
                dept.setName(newName);
                dept.setLocation(newLocation);
                tx.commit();
                System.out.println("Department updated successfully.");
            } else {
                System.out.println("Department not found.");
                tx.rollback();
            }
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            System.err.println("Failed to update department: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public void deleteDepartment(int id) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Department dept = em.find(Department.class, id);
            if (dept != null) {
                em.remove(dept);
                tx.commit();
                System.out.println("Department deleted successfully.");
            } else {
                System.out.println("Department not found.");
                tx.rollback();
            }
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            System.err.println("Failed to delete department: " + e.getMessage());
        } finally {
            em.close();
        }
    }
}