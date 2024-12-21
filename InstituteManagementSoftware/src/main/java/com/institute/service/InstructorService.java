package com.institute.service;

import com.institute.model.Instructor;
import com.institute.model.Student;
import com.institute.repository.InstructorRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class InstructorService {

    private final InstructorRepository instructorRepository;

    // Get the total count of instructors
    public long getInstructorCount() {
        return instructorRepository.count();
    }

    // Fetch all instructors as a list
    public List<Instructor> getAllInstructors() {
        return instructorRepository.findAll();
    }

    // Fetch all instructors with pagination
    public Page<Instructor> getAllInstructors(Pageable pageable) {
        return instructorRepository.findAll(pageable);
    }

    // Search instructors by name (case-insensitive)
    public List<Instructor> searchInstructors(String search, Pageable pageable) {
        return instructorRepository.findByNameContainingIgnoreCase(search);
    }
    
    public Page<Instructor> searchStudents(String search, Pageable pageable) {
        return InstructorRepository.findByNameContaining(search, pageable);
    }
    
    

    // Get a single instructor by their ID
    public Instructor getInstructorById(Long id) {
        return instructorRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Instructor not found"));
    }

    // Check if an email is already taken by another instructor
    public boolean isEmailTaken(String email) {
        return instructorRepository.existsByEmail(email);
    }

    // Save or update an instructor
    public Instructor saveInstructor(Instructor instructor) {
        if (instructor.getId() == null && isEmailTaken(instructor.getEmail())) {
            throw new IllegalArgumentException("Email is already in use");
        }
        return instructorRepository.save(instructor);
    }

    // Delete an instructor by ID
    public void deleteInstructor(Long id) {
        if (!instructorRepository.existsById(id)) {
            throw new EntityNotFoundException("Instructor not found with ID: " + id);
        }
        instructorRepository.deleteById(id);
    }

    // Get recent instructors with pagination
    public List<Instructor> getRecentInstructors(Pageable pageable) {
        return instructorRepository.findAll(pageable).getContent();
    }

    // Constructor for dependency injection (explicit for clarity)
    @Autowired
    public InstructorService(InstructorRepository instructorRepository) {
        this.instructorRepository = instructorRepository;
    }

	public Object searchInstructors(String trim) {
		// TODO Auto-generated method stub
		return null;
	}
    

}
