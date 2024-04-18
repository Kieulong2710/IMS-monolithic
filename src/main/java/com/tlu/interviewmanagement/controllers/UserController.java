package com.tlu.interviewmanagement.controllers;

import com.tlu.interviewmanagement.entities.Department;
import com.tlu.interviewmanagement.entities.Users;
import com.tlu.interviewmanagement.service.AccountService;
import com.tlu.interviewmanagement.service.DepartmentService;
import com.tlu.interviewmanagement.service.UserService;
import com.tlu.interviewmanagement.utils.SearchUtil;
import com.tlu.interviewmanagement.web.request.PasswordRequest;
import com.tlu.interviewmanagement.web.request.SearchRequest;
import com.tlu.interviewmanagement.web.request.UserRequest;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/admin/user")
@RequiredArgsConstructor
public class UserController {
    private final DepartmentService departmentService;
    private final UserService userService;
    private final SearchUtil searchUtil;
    private final AccountService accountService;
    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    @GetMapping({"", "/"})
    public String user(Model model,
                       @ModelAttribute SearchRequest searchRequest) {
        Page<Users> users = userService.findAllUser(searchUtil.getSearchRequest(searchRequest));
        SearchRequest searchResponse = searchUtil.setPageMax(users.getTotalPages(), searchRequest);
        logger.info("FindAllUser:::::::::::::::" + users);
        model.addAttribute("users", users);
        model.addAttribute("searchResponse", searchResponse);
        return "ui/user/list";
    }

    @GetMapping("/create")
    public String getUserCreate(@ModelAttribute UserRequest userRequest, Model model) {
        List<Department> departments = departmentService.findAllDepartment();
        model.addAttribute("departments", departments);
        model.addAttribute("user", userRequest);
        return "ui/user/add";
    }

    @PostMapping("/create")
    public String postUserCreate(@ModelAttribute UserRequest userRequest,
                                 RedirectAttributes ra, Model model) throws MessagingException {
        if (accountService.existedAccountByEmail(userRequest.getEmail())) {
            ra.addFlashAttribute("user", userRequest);
            ra.addFlashAttribute("message", "Email da ton tai");
            ra.addFlashAttribute("alert", "Fail");
        } else {

            userService.saveUser(userRequest);
            ra.addFlashAttribute("alert", "success");
        }
        List<Department> departments = departmentService.findAllDepartment();
        model.addAttribute("departments", departments);
        return "redirect:/admin/user/create";
    }
    @GetMapping("/{id}")
    public String userDetail(@PathVariable Long id, Model model) {
        Users user = userService.findUserById(id);
        model.addAttribute("user", user);
        return "ui/user/detail";
    }
    @GetMapping("/edit/{id}")
    public String getUserEdit(@PathVariable Long id, Model model) {
        Users user = userService.findUserById(id);
        List<Department> departments = departmentService.findAllDepartment();
        model.addAttribute("departments", departments);
        model.addAttribute("user", user);
        return "ui/user/edit";
    }

    @PostMapping("/edit/{id}")
    public String postUserEdit(@PathVariable Long id,
                               @ModelAttribute UserRequest userRequest,
                               Model model) {
        if (Objects.equals(id, userRequest.getId())) {
            model.addAttribute("alert","success");
            userService.updateUser(userRequest);
        }else {
            model.addAttribute("alert", "fail");
        }
        return getUserEdit(id, model);
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id,
                             RedirectAttributes ra) {
        userService.deleteUserById(id);
        ra.addFlashAttribute("alert", "Success");
        return "redirect:/user";
    }
    @GetMapping("/changePassword")
    public String password(){
        return "ui/change-password";
    }
    @PostMapping("/changePassword")
    public String changePasswordForm(@ModelAttribute PasswordRequest passwordRequest,
                                     HttpServletRequest req,
                                     RedirectAttributes ra,
                                     Model model) {
        if (!Objects.equals(passwordRequest.getNewPassword(), passwordRequest.getNewPasswordC())) {
            model.addAttribute("message", "The entered passwords are not the same !!!");
            model.addAttribute("password", passwordRequest);
            return "ui/change-password";
        } else {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Users user = userService.findUserByAccount_Email(authentication.getName());
            userService.changePassword(passwordRequest, user.getAccount().getEmail());
            HttpSession httpSession = req.getSession();
            httpSession.setAttribute("user", user);
            ra.addFlashAttribute("alert", "Password changed successfully !!!");
            return "redirect:/admin/job/";
        }
    }
}
