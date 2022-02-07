package com.wilyfreddie.deemo.student;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public void addNewStudent(Student student) {
        Optional<Student> studentOptional = studentRepository.findStudentByEmail(student.getEmail());
        if (studentOptional.isPresent()) {
            throw new IllegalStateException("Email already exists!");
        }
        studentRepository.save(student);
    }

    public void deleteStudent(UUID id) {
        boolean studentExists = studentRepository.existsById(id);
        if (studentExists) {
            studentRepository.deleteById(id);
        } else {
            throw new IllegalStateException("Student with ID" + id + "ID does not exist!");
        }
    }

    @Transactional
    public void updateStudent(UUID id, String name, String email, String dob){
        Student student = studentRepository.findById(id).orElseThrow(() -> new IllegalStateException("Student with ID " + id + " does not exist!"));

        if(name != null && name.length() > 0 && !Objects.equals(student.getName(), name)){
            student.setName(name);
        }

        if(email != null && email.length() > 0 && !Objects.equals(student.getEmail(), email)){
            Optional<Student> studentWithEmail = studentRepository.findStudentByEmail(email);
            if (studentWithEmail.isPresent()) {
                throw new IllegalStateException("Email is already taken!");
            }
            student.setEmail(email);
        }

        if(dob != null && !Objects.equals(student.getDob(), dob)){
            LocalDate _dob = LocalDate.parse(dob);
            student.setDob(_dob);
            student.setAge(student.getAge());
        }
    }
}
