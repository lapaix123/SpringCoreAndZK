package rw.gov.minecofine.repository.implementation;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import rw.gov.minecofine.domain.Student;
import rw.gov.minecofine.repository.StudentRepostory;

import java.util.Date;
import java.util.List;

@Repository
public class StudentImpl implements StudentRepostory {

    private  SessionFactory sessionFactory;

    @Autowired
    public StudentImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public String newStudent(Student student) {
        try {
            if (student.getId() == null) {
                student.setRegistrationDate(new Date());
            }
            Long id = (Long) sessionFactory.getCurrentSession().save(student);
            return id != null ? "Student Saved" : "Failed to save student";
        } catch (Exception e) {
            // Log the exception
            e.printStackTrace();
            return "Error saving student: " + e.getMessage();
        }
    }

    @Override
    @Transactional
    public String updateStudent(Student student) {
        try {
            if (student.getId() == null) {
                student.setRegistrationDate(new Date());
            }
            sessionFactory.getCurrentSession().update(student);
            return "Student Updated";
        } catch (Exception e) {
            // Log the exception
            e.printStackTrace();
            // Handle the error appropriately
            return null;
        }
    }

    @Override
    @Transactional
    public List<Student> allStudents() {
        try {
            return sessionFactory.getCurrentSession().createQuery("from Student", Student.class).list();
        } catch (Exception e) {
            // Log the exception
            e.printStackTrace();
            // Handle the error appropriately
            return null;
        }
    }

    @Override
    @Transactional
    public void deleteStudent(Student student) {
        try {
            sessionFactory.getCurrentSession().delete(student);
        } catch (Exception e) {
            // Log the exception
            e.printStackTrace();
            // Handle the error appropriately
        }
    }

    @Override
    @Transactional
    public Student getStudentById(Long id) {
        try {
            return sessionFactory.getCurrentSession().get(Student.class, id);
        } catch (Exception e) {
            // Log the exception
            e.printStackTrace();
            // Handle the error appropriately
            return null;
        }
    }
}
