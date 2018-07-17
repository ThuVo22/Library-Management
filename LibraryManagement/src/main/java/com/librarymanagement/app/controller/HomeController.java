package com.librarymanagement.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.librarymanagement.app.dao.LibraryManagementDAO;
import com.librarymanagement.app.entities.LibraryManagementEntities;

@Controller
public class HomeController {
    
    
    @Autowired
    private LibraryManagementDAO libraryManagementDAO;
    
    
    @GetMapping("/")
    public String index(Model model) {
//        model.addAttribute("result", libraryManagementDAO.getAll());
        return "index";
    }
    
    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("libraries", libraryManagementDAO.getAll());
        return "fragment/list";
    }
    
    @PostMapping("/search")
    public @ResponseBody List<LibraryManagementEntities> search(Model model, String name) {
        return libraryManagementDAO.search(name);
    }
}
