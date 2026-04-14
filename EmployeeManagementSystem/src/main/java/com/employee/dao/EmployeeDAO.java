package com.employee.dao;

import com.employee.entity.Department;
import com.employee.entity.Employee;
import com.employee.main.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;

public class EmployeeDAO {

    public void addEmployee(Employee employee) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(employee);
            tx.commit();
            System.out.println("Employee added: " + employee.getFirstName());
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            System.err.println("Failed to add employee: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public Employee getEmployeeById(int id) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            Employee emp = em.find(Employee.class, id);
            if (emp == null) System.out.println("No employee found with id: " + id);
            return emp;
        } finally {
            em.close();
        }
    }

    public List<Employee> getAllEmployees() {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery("SELECT e FROM Employee e", Employee.class)
                     .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Employee> getEmployeesByDepartment(int departmentId) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery(
                "SELECT e FROM Employee e WHERE e.department.id = :deptId", Employee.class)
                .setParameter("deptId", departmentId)
                .getResultList();
        } finally {
            em.close();
        }
    }

    public void updateEmployee(int id, String firstName, String lastName, String email, double salary, int departmentId) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Employee emp = em.find(Employee.class, id);
            if (emp != null) {
                Department dept = em.find(Department.class, departmentId);
                if (dept == null) {
                    System.out.println("Department not found.");
                    tx.rollback();
                    return;
                }
                emp.setFirstName(firstName);
                emp.setLastName(lastName);
                emp.setEmail(email);
                emp.setSalary(salary);
                emp.setDepartment(dept);
                tx.commit();
                System.out.println("Employee updated successfully.");
            } else {
                System.out.println("Employee not found.");
                tx.rollback();
            }
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            System.err.println("Failed to update employee: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public void deleteEmployee(int id) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Employee emp = em.find(Employee.class, id);
            if (emp != null) {
                em.remove(emp);
                tx.commit();
                System.out.println("Employee deleted successfully.");
            } else {
                System.out.println("Employee not found.");
                tx.rollback();
            }
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            System.err.println("Failed to delete employee: " + e.getMessage());
        } finally {
            em.close();
        }
    }
}