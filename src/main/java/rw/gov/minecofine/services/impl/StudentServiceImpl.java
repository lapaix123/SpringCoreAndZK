package rw.gov.minecofine.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rw.gov.minecofine.domain.Student;
import rw.gov.minecofine.repository.StudentRepostory;
import rw.gov.minecofine.services.StudentService;

import java.util.List;
@Service(StudentService.Name)

public class StudentServiceImpl implements StudentService {


    private final StudentRepostory studentRepository;

    @Autowired
    public StudentServiceImpl(StudentRepostory studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    @Transactional
    public String createStudent(Student student) {
        return studentRepository.newStudent(student);
    }

    @Override
    @Transactional
    public String updateStudent(Student student) {
       return studentRepository.updateStudent(student);

    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> getAllStudents() {
        return studentRepository.allStudents();
    }

    @Override
    @Transactional
    public void deleteStudent(Long id) {
        Student student = studentRepository.getStudentById(id);
        if (student != null) {
            studentRepository.deleteStudent(student);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Student getStudentById(Long id) {
        return studentRepository.getStudentById(id);
    }
}
