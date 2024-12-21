package com.institute.service;

import com.institute.model.Student;
import com.institute.repository.StudentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Page<Student> getAllStudents(Pageable pageable) {
        return studentRepository.findAll(pageable);
    }

    public Page<Student> searchStudents(String search, Pageable pageable) {
        return studentRepository.findByNameContaining(search, pageable);
    }

    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    public void saveStudent(Student student) {
        studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

	public Object getStudentCount() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getRecentStudents() {
		// TODO Auto-generated method stub
		return null;
	}
	public List<Student> getRecentStudents(Pageable pageable) {
        return studentRepository.findAll(pageable).getContent();
    }

	public Student findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
}
