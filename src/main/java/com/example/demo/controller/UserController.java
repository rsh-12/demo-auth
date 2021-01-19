package com.example.demo.controller;
/*
 * Date: 12/2/20
 * Time: 9:39 PM
 * */

import com.example.demo.entity.User;
import com.example.demo.service.UserQuestionService;
import com.example.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;

@Controller
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    private final UserQuestionService userQuestionService;

    @Autowired
    public UserController(UserService userService, UserQuestionService userQuestionService) {
        this.userService = userService;
        this.userQuestionService = userQuestionService;
    }

    @GetMapping("/")
    public String home(Principal principal, Model model) {

        if (principal != null) {
            var authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = (User) authentication.getPrincipal();
            model.addAttribute("userQuestions", userQuestionService.findAllByUserId(user.getId()));
        }

        return "home";
    }

    @GetMapping("/profile")
    public String getProfilePage(Principal principal, Model model) {

        // get user from the service
        model.addAttribute("user", userService.findByUsername(principal.getName()));

        log.info(">>> GET profile.html");
        return "user/profile";
    }

    @PostMapping("/save")
    public String updateUser(@Valid @ModelAttribute("user") User user,
                             BindingResult bindingResult,
                             Model model,
                             HttpSession session,
                             @RequestParam("id") Long id) {

        return userService.updateProfile(user, id, bindingResult, model, session);
    }
}

