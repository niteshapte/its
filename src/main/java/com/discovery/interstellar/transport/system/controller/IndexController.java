package com.discovery.interstellar.transport.system.controller;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * The Class IndexController.
 */
@Controller
public class IndexController implements ErrorController {
    
    /** The Constant PATH. */
    private static final String PATH = "/error";

    /**
     * Index.
     *
     * @return the string
     */
    @RequestMapping("/")
    String index() {
        return "index";
    }

    /**
     * Error.
     *
     * @param model the model
     * @return the string
     */
    @RequestMapping(value = PATH)
    public String error(Model model) {
        String message = "Failed to load the page. Please restart again.";
        model.addAttribute("validationMessage", message);
        return "validation";
    }

    /* (non-Javadoc)
     * @see org.springframework.boot.autoconfigure.web.ErrorController#getErrorPath()
     */
    @Override
    public String getErrorPath() {
        return PATH;
    }
}
