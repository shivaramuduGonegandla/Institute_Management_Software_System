package com.institute.controller;

import com.institute.model.Course;
import com.institute.service.CourseService;
import com.institute.service.InstructorService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;
    private final InstructorService instructorService;

    public CourseController(CourseService courseService, InstructorService instructorService) {
        this.courseService = courseService;
        this.instructorService = instructorService;
    }

    // List all courses or search by course name with pagination
    @GetMapping
    public String listCourses(@RequestParam(required = false) String search,
                              @PageableDefault(size = 10) Pageable pageable,
                              Model model) {
        if (search != null && !search.trim().isEmpty()) {
            model.addAttribute("courses", courseService.searchCourses(search.trim()));
        } else {
            model.addAttribute("courses", courseService.getAllCourses(pageable));
        }
        model.addAttribute("search", search);
        return "courses/list";
    }

    // Display form to create a new course
    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("course", new Course());
        model.addAttribute("instructors", instructorService.getAllInstructors());  // List of instructors for the course
        return "courses/form";  // The form view for creating a course
    }

    // Display form to edit an existing course
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Course> course = Optional.ofNullable(courseService.getCourseById(id));
        if (course.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Course not found");
            return "redirect:/courses";
        }
        model.addAttribute("course", course.get());
        model.addAttribute("instructors", instructorService.getAllInstructors());  // List of instructors for the course
        return "courses/form";  // The form view for editing a course
    }

    // Save the new or edited course
    @PostMapping
    public String save(@Valid Course course, BindingResult result,
                       RedirectAttributes redirectAttributes, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("instructors", instructorService.getAllInstructors());
            return "courses/form";  // Return to the form view if there are validation errors
        }

        courseService.saveCourse(course);  // Save the course
        redirectAttributes.addFlashAttribute("success", "Course saved successfully!");
        return "redirect:/courses";  // Redirect back to the list of courses
    }

    // Delete a course by ID
    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Optional<Course> course = Optional.ofNullable(courseService.getCourseById(id));
        if (course.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Course not found");
            return "redirect:/courses";
        }

        courseService.deleteCourse(id);  // Delete the course
        redirectAttributes.addFlashAttribute("success", "Course deleted successfully!");
        return "redirect:/courses";  // Redirect back to the list of courses
    }
    
 // Show the details of a course by its ID
    @GetMapping("/{id}")
    public String showCourse(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Course> course = Optional.ofNullable(courseService.getCourseById(id));
        if (course.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Course not found");
            return "redirect:/courses";
        }

        model.addAttribute("course", course.get());
        return "redirect:/courses"; // A new Thymeleaf template to show course details
    }
}
