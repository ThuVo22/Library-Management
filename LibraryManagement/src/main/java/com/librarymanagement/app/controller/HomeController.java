package com.librarymanagement.app.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.librarymanagement.app.biz.LibraryManagementBiz;
import com.librarymanagement.app.entities.LibraryManagementEntities;
import com.librarymanagement.app.model.PagingInfo;

@Controller
public class HomeController {

    @Autowired
    private LibraryManagementBiz libraryManagementBiz;

    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }

    @PostMapping("/list/{page}")
    public String list(Model model, @PathVariable Long page) {

        PagingInfo pagingInfo = libraryManagementBiz.getPagingInfo(page);
        model.addAttribute("pagingInfo", pagingInfo);

        List<LibraryManagementEntities> libraryEntities = libraryManagementBiz.getEnititiesByPage(page,
                pagingInfo.getPageSize());
        model.addAttribute("libraries", libraryEntities);

        return "fragment/list";
    }

    @GetMapping("/list")
    public String list(Model model) {

        PagingInfo pagingInfo = libraryManagementBiz.getPagingInfo(1);
        model.addAttribute("pagingInfo", pagingInfo);

        List<LibraryManagementEntities> libraryEntities = libraryManagementBiz
                .getEnititiesByPage(pagingInfo.getCurrentPage(), pagingInfo.getPageSize());
        model.addAttribute("libraries", libraryEntities);

        return "fragment/list";
    }

    @GetMapping(value = { "/create", "/edit/{id}" })
    public String getCreateFragment(@PathVariable Optional<Integer> id, Model model) {
        if (id.isPresent()) {
            LibraryManagementEntities entity = libraryManagementBiz.getRepo().findById(id.get()).get();
            model.addAttribute("entity", entity);
            model.addAttribute("action", "/edit");
        } else {
            LibraryManagementEntities entity = new LibraryManagementEntities();
            model.addAttribute("entity", entity);
            model.addAttribute("action", "/create");
        }

        return "fragment/form";
    }

    @PostMapping("/search")
    public @ResponseBody List<LibraryManagementEntities> search(String keyword) {
        return libraryManagementBiz.search(keyword);
    }

    @PostMapping("/edit")
    @ResponseBody
    public Boolean editEntity(LibraryManagementEntities entity) {
        Boolean result = libraryManagementBiz.update(entity);
        return result;
    }

    @PostMapping("/create")
    @ResponseBody
    public Boolean createEntity(LibraryManagementEntities entity) {
        LibraryManagementEntities result = libraryManagementBiz.getRepo().save(entity);
        if (result != null) {
            return true;
        }
        return false;
    }

    @ResponseBody
    @DeleteMapping("/delete/{id}")
    public Boolean delete(@PathVariable(value = "id") Integer id) {
        try {
            libraryManagementBiz.getRepo().deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
