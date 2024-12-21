package com.institute.exception;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Create a Logger instance manually
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Handle EntityNotFoundException, typically when a resource is not found
    @ExceptionHandler(EntityNotFoundException.class)
    public String handleEntityNotFound(EntityNotFoundException ex, RedirectAttributes redirectAttributes) {
        // Log the exception details for debugging purposes
        log.error("Entity not found: {}", ex.getMessage(), ex);
        
        // Add a flash attribute with the error message to display it in the redirected view
        redirectAttributes.addFlashAttribute("error", ex.getMessage());
        
        // Redirect to the homepage or another error handling page
        return "redirect:/";
    }
    
    // Handle general exceptions that might occur in the application
    @ExceptionHandler(Exception.class)
    public String handleGenericError(Exception ex, Model model) {
        // Log the stack trace for debugging and troubleshooting
        log.error("Unexpected error occurred: {}", ex.getMessage(), ex);
        
        // Add a generic error message to the model for rendering in the error page
        model.addAttribute("error", "An unexpected error occurred. Please try again later.");
        
        // Optionally, you can render a custom error page instead of just "error"
        return "error";
    }
}
