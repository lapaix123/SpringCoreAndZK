package rw.gov.minecofine.viewModel;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import rw.gov.minecofine.domain.Student;
import rw.gov.minecofine.services.StudentService;

import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class StudentViewModel {


    @WireVariable(StudentService.Name)
    private StudentService studentService;
    private List<Student> students;
    private Student student;


    @Init
    public void init() {
        this.student = new Student();
        List<Student> studentList = studentService.getAllStudents();
        this.students = new ListModelList<>(studentList);
    }

    public StudentService getStudentService() {
        return studentService;
    }

    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Command
    @NotifyChange({"students", "student"})
    public void addStudent() {
        if(validateStudent()){
            String result = studentService.createStudent(student);
            Clients.showNotification(result);
            student = new Student();
        }


    }


    @Command
    @NotifyChange({"students", "studentListModelList"})
    public void updateStudent() {
        if(validateStudent()){
            String result = studentService.updateStudent(student);
            Clients.showNotification(result);
            if ("Student updated Successfully".equals(result)) {
                List<Student> studentList = studentService.getAllStudents();
                students.clear();
                students.addAll(studentList);
            }
        }

    }

    @Command
    public void confirmDelete() {
        if (student == null) {
            Clients.showNotification("No student selected");
            return;
        }

        Messagebox.show("Are you sure you want to delete this student?",
                "Confirm Delete",
                Messagebox.YES | Messagebox.NO,
                Messagebox.QUESTION,
                event -> {
                    if (Messagebox.ON_YES.equals(event.getName())) {
                        studentService.deleteStudent(student.getId());
                        student = new Student();
                        refreshStudentList();


                    }
                });
    }

    @NotifyChange("students")
    private void refreshStudentList() {
        List<Student> studentList = studentService.getAllStudents();
        students.clear();
        students.addAll(studentList);
    }

    private boolean validateStudent() {
        if (student.getRegistrationNumber() == null || student.getRegistrationNumber().isEmpty()) {
            Clients.showNotification("Registration Number is required", "error", null, "middle_center", 2000);
            return false;
        } else if (!student.getRegistrationNumber().matches("\\d{5}")) {
            Clients.showNotification("Registration Number must be a 5-digit number", "error", null, "middle_center", 2000);
            return false;
        } else if (student.getFirstName() == null || student.getFirstName().isEmpty()) {
            Clients.showNotification("First Name is required", "error", null, "middle_center", 2000);
            return false;
        } else if (student.getLastName() == null || student.getLastName().isEmpty()) {
            Clients.showNotification("Last Name is required", "error", null, "middle_center", 2000);
            return false;
        } else if (student.getGender() == null || student.getGender().isEmpty()) {
            Clients.showNotification("Gender is required", "error", null, "middle_center", 2000);
            return false;
        } else if (student.getDateOfBirth() == null) {
            Clients.showNotification("Date of Birth is required", "error", null, "middle_center", 2000);
            return false;
        } else if (!isAtLeastSixYearsOld(student.getDateOfBirth())) {
            Clients.showNotification("Student must be at least 6 years old", "error", null, "middle_center", 2000);
            return false;
        } else if (student.getAddress() == null || student.getAddress().isEmpty()) {
            Clients.showNotification("Address is required", "error", null, "middle_center", 2000);
            return false;
        } else if (student.getEmail() == null || student.getEmail().isEmpty()) {
            Clients.showNotification("Email is required", "error", null, "middle_center", 2000);
            return false;
        } else if (!isValidEmail(student.getEmail())) {
            Clients.showNotification("Invalid email format", "error", null, "middle_center", 2000);
            return false;
        } else if (student.getPhoneNumber() == null || student.getPhoneNumber().isEmpty()) {
            Clients.showNotification("Phone Number is required", "error", null, "middle_center", 2000);
            return false;
        } else if (student.getPhoneNumber().length() != 10) {
            Clients.showNotification("Phone number must be 10 characters", "error", null, "middle_center", 2000);
            return false;
        } else if (!student.getPhoneNumber().startsWith("078") &&
                !student.getPhoneNumber().startsWith("073") &&
                !student.getPhoneNumber().startsWith("072") &&
                !student.getPhoneNumber().startsWith("079")) {
            Clients.showNotification("Phone number must start with 078, 073, 072, or 079", "error", null, "middle_center", 2000);
            return false;
        } else {
            return true;
        }
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pat = Pattern.compile(emailRegex);
        return pat.matcher(email).matches();
    }

    private boolean isAtLeastSixYearsOld(java.util.Date dateOfBirth) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -6);
        return dateOfBirth.before(calendar.getTime());
    }


    }

