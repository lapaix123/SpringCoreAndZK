package rw.gov.minecofine.services;

import rw.gov.minecofine.domain.Student;

import java.util.List;

public interface StudentService {

    public static String Name = "StudentServiceImpl";
    String createStudent(Student student);
    String updateStudent(Student student);
    List<Student> getAllStudents();
    void deleteStudent(Long id);
    Student getStudentById(Long id);

}
