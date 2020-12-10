package com.example.demo.controller;
/*
 * Date: 12/2/20
 * Time: 8:13 PM
 * */

import com.example.demo.entity.User;
import com.example.demo.service.RoleService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    // get all users
    @GetMapping("/user-list")
    public String listUsers(Model model) {
        log.info("> getting 'user-list' page");

        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("users", userService.findAll());

        log.info("return 'user-list' page");
        return "user-list";
    }

    // delete the user by id
    @GetMapping("/delete")
    public String deleteUser(@RequestParam("userId") Long userId) {
        log.info("> deleting the user by id: " + userId);
        userService.deleteById(userId);

        log.info("> redirect to 'user-list' page");
        return "redirect:/admin/user-list";
    }


    @PostMapping("/update")
    public String setUserRoles(@RequestParam("userId") Long id,
                               @RequestParam(name = "formRoles", required = false, defaultValue = "") Set<String> formRoles,
                               Model model) {

        // https://ru.stackoverflow.com/questions/959711/%D0%9A%D0%B0%D0%BA-%D0%BF%D0%BE%D0%B1%D0%BE%D1%80%D0%BE%D1%82%D1%8C-unsupportedoperationexception-null-%D0%B2-spring
        try {
            User user = userService.findById(id);
            boolean candidate = userService.isCandidate(formRoles, user, roleService);

            if (candidate) {
                // true - назначены роли, обновляем данные
                log.info("> updating the user");
                userService.save(user);
            } else {
                // false - все роли сняты, удаляем пользователя
                log.info("> deleting the user by id: " + id);
                userService.deleteById(id);
            }

        } catch (Exception exception) {
            log.warn(">>> ERROR >>> : " + exception);
            model.addAttribute("error", "Something went wrong!");

            log.info("> return 'user-list' page");
            return "user-list";
        }

        log.info("> redirect to 'user-list' page");
        return "redirect:/admin/user-list";
    }

}
