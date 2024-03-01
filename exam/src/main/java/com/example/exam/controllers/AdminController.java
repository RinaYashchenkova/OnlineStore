package com.example.exam.controllers;

import com.example.exam.models.Person;
import com.example.exam.models.Role;
import com.example.exam.services.AdminService;
import com.example.exam.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


@Controller
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;


    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/listUsers")
    public String listUsers(Model model){
        model.addAttribute("users", adminService.allUsers());
        return "people/list_users";
    }

    @PostMapping("/{id}/banAccount")
    public String banAccount(@PathVariable("id") long id){
        adminService.banAccount(id);
        return "redirect:/admin/listUsers";
    }

    @PostMapping("/{id}/unbanAccount")
    public String unbanAccount(@PathVariable("id") long id){
        adminService.unbanAccount(id);
        return "redirect:/admin/listUsers";
    }
}
