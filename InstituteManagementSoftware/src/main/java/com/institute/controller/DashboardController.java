package com.institute.controller;

import com.institute.service.CourseService;
import com.institute.service.InstructorService;
import com.institute.service.StudentService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    private final StudentService studentService;
    private final CourseService courseService;
    private final InstructorService instructorService;

    @GetMapping("/")
    public String dashboard(Model model) {
        model.addAttribute("studentCount", studentService.getStudentCount());
        model.addAttribute("courseCount", courseService.getCourseCount());
        model.addAttribute("instructorCount", instructorService.getInstructorCount());
        model.addAttribute("recentStudents", studentService.getRecentStudents(Pageable.ofSize(5)));
        model.addAttribute("recentCourses", courseService.getRecentCourses(Pageable.ofSize(5)));
        return "index";
    }

	public DashboardController(StudentService studentService, CourseService courseService,
			InstructorService instructorService) {
		super();
		this.studentService = studentService;
		this.courseService = courseService;
		this.instructorService = instructorService;
	}
    
	// Method for handling the "Read More" page
    @GetMapping("/readmore")
    public String readMore(Model model) {
        model.addAttribute("title", "Read More");
        model.addAttribute("content", "This is where you can add detailed content for the Read More page.");
        return "readmore";  // Render the readmore.html page
    }
    
    
 // Method for handling the "Read More" page
    @GetMapping("/getstartnow")
    public String getStartNow(Model model) {
        model.addAttribute("title", "Get Start Now");
        model.addAttribute("content", "This is where you can add detailed content for the Read More page.");
        return "getstartnow";  // Render the readmore.html page
    }
    
    
}