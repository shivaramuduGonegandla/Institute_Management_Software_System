package com.institute.controller;

import com.institute.model.Student;
import com.institute.service.CourseService;
import com.institute.service.StudentService;
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
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;
    private final CourseService courseService;

    public StudentController(StudentService studentService, CourseService courseService) {
        this.studentService = studentService;
        this.courseService = courseService;
    }

    @GetMapping
    public String list(@RequestParam(required = false) String search,
                       @PageableDefault(size = 10) Pageable pageable,
                       Model model) {
        model.addAttribute("students",
                search != null ? studentService.searchStudents(search, pageable)
                        : studentService.getAllStudents(pageable));
        return "students/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("courses", courseService.getAllCourses());
        return "students/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Student> student = studentService.getStudentById(id);
        if (student.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Student not found");
            return "redirect:/students";
        }
        model.addAttribute("student", student.get());
        model.addAttribute("courses", courseService.getAllCourses());
        return "students/form";
    }

    @PostMapping
    public String save(@Valid @ModelAttribute("student") Student student, BindingResult result,
                       RedirectAttributes redirectAttributes, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("courses", courseService.getAllCourses());
            return "students/form";
        }
        studentService.saveStudent(student);
        redirectAttributes.addFlashAttribute("success", "Student saved successfully!");
        return "redirect:/students";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Optional<Student> student = studentService.getStudentById(id);
        if (student.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Student not found");
            return "redirect:/students";
        }
        studentService.deleteStudent(id);
        redirectAttributes.addFlashAttribute("success", "Student deleted successfully!");
        return "redirect:/students";
    }
    
    @GetMapping("/students/{id}")
    public String getStudent(@PathVariable Long id, Model model) {
        Student student = studentService.findById(id);
        model.addAttribute("student", student);
        return "redirect:/students"; // Thymeleaf template for individual student details
    }

 // Get the student by ID and show details
    @GetMapping("/{id}")
    public String getStudentDetails(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Student> student = studentService.getStudentById(id);
        if (student.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Student not found");
            return "redirect:/students";
        }
        model.addAttribute("student", student.get());
        return "redirect:/students"; // Name of the template that will display student details
    }

}
