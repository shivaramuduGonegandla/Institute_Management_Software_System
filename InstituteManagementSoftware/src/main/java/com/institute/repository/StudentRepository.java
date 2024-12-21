package com.institute.repository;

import com.institute.model.Student;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByNameContainingIgnoreCase(String name);
    
    @Query("SELECT s FROM Student s JOIN s.course c WHERE c.name LIKE %:courseName%")
    List<Student> findByCourseNameContaining(@Param("courseName") String courseName);
    
    @Query("SELECT s FROM Student s JOIN s.course c WHERE " +
           "LOWER(s.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(c.name) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<Student> searchByNameOrCourse(@Param("search") String search);

	Page<Student> findByNameContaining(String search, Pageable pageable);
}