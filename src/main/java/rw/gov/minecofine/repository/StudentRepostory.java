package rw.gov.minecofine.repository;

import rw.gov.minecofine.domain.Student;

import java.util.List;

public interface StudentRepostory {
    String newStudent(Student student);
    String updateStudent(Student student);
    List<Student> allStudents();
    void deleteStudent(Student student);
    Student getStudentById(Long id);
}
