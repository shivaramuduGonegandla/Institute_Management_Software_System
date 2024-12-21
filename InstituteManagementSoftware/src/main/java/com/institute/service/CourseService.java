package com.institute.service;

import com.institute.model.Course;
import com.institute.repository.CourseRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CourseService {

    private final CourseRepository courseRepository; // Remove static and use final for dependency injection

    public long getCourseCount() {
        return courseRepository.count();
    }

    public List<Course> getRecentCourses() {
        return courseRepository.findAll(Pageable.ofSize(5)).getContent(); // Pageable to fetch recent courses
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll(); // Fetch all courses without pagination
    }

    public Page<Course> getAllCourses(Pageable pageable) {
        return courseRepository.findAll(pageable); // Fetch courses with pagination
    }

    public List<Course> searchByInstructor(String instructorName) {
        return courseRepository.findByInstructorNameContaining(instructorName); // Search by instructor name
    }

    public Course getCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with ID: " + id)); // Handle missing course
    }

    public Course saveCourse(Course course) {
        return courseRepository.save(course); // Save a course
    }

    public void deleteCourse(Long id) {
        courseRepository.deleteById(id); // Delete course by ID
    }

	public CourseService(CourseRepository courseRepository) {
		super();
		this.courseRepository = courseRepository;
	}

	 public List<Course> getRecentCourses(Pageable pageable) {
	        return courseRepository.findAll(pageable).getContent();
	    }

	public Object searchCourses(String search) {
		// TODO Auto-generated method stub
		return null;
	}
    
	

    
}
