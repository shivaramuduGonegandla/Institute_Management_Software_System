package com.institute.controller;

import com.institute.model.Instructor;
import com.institute.service.InstructorService;
import com.institute.service.CourseService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/instructors")
public class InstructorController {

    private final InstructorService instructorService;
    private final CourseService courseService;

    public InstructorController(InstructorService instructorService, CourseService courseService) {
        this.instructorService = instructorService;
        this.courseService = courseService;
    }

    @GetMapping
    public String list(@RequestParam(required = false) String search,
                       @PageableDefault(size = 10) Pageable pageable,
                       Model model) {
        if (search != null && !search.trim().isEmpty()) {
            model.addAttribute("instructors", instructorService.searchInstructors(search.trim()));
        } else {
            model.addAttribute("instructors", instructorService.getAllInstructors(pageable));
        }
        model.addAttribute("search", search);
        return "instructors/list";
    }


    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("instructor", new Instructor());
        model.addAttribute("courses", courseService.getAllCourses());
        return "instructors/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            model.addAttribute("instructor", instructorService.getInstructorById(id));
            model.addAttribute("courses", courseService.getAllCourses());
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", "Instructor not found");
            return "redirect:/instructors";
        }
        return "instructors/form";
    }

    @PostMapping
    public String save(@Valid @ModelAttribute Instructor instructor, BindingResult result,
                       RedirectAttributes redirectAttributes, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("courses", courseService.getAllCourses());
            return "instructors/form";
        }

        instructorService.saveInstructor(instructor);
        redirectAttributes.addFlashAttribute("success", "Instructor saved successfully!");
        return "redirect:/instructors";
    }

 // Delete an instructor by ID
    @PostMapping("/{id}/delete")
    public String deleteInstructor(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            Instructor instructor = instructorService.getInstructorById(id);
            instructorService.deleteInstructor(id); // Delete the instructor
            redirectAttributes.addFlashAttribute("success", "Instructor deleted successfully!");
        } catch (EntityNotFoundException ex) {
            redirectAttributes.addFlashAttribute("error", "Instructor not found");
        }
        return "redirect:/instructors"; // Redirect back to the list of instructors
    }
    
}