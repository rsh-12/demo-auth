package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Set;

public interface UserService {

    List<User> getUsers();

    User getUserById(Long id);

    void deleteUserById(Long id);

    void updateUser(User user);

    User getUserByUsername(String username);

    List<UserRepository.NameAndLastLoginAt> recentUsers();

    void setUserRoles(Set<String> roles, Long id);

    boolean selfUpdate(Principal principal, Long id);

    String updateProfile(@Valid User user, Long userId, BindingResult bindingResult, Model model, HttpSession session);

    boolean checkFieldErrors(User user, BindingResult bindingResult, Model model);

    void registerUser(User user, String siteUrl);

    void sendVerificationEmail(User user, String siteUrl);

    boolean verifyUser(String verificationCode);
}
