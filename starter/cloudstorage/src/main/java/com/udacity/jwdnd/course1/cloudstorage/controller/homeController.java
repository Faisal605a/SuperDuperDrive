package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileHandlingService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class homeController {
    @Autowired
    private CredentialsService credentialsService;
    @Autowired
    private NoteService noteService;
    @Autowired
    private FileHandlingService fileHandlingService;

    @GetMapping
    public String getHome(Authentication authentication, Model model) {
        try {
            String userName = authentication.getName();
            model.addAttribute("notes", noteService.getNotes(userName));
            model.addAttribute("files", fileHandlingService.getDbFileList(userName));
            model.addAttribute("credentials", credentialsService.getAllCredentials(userName));
        }catch (Exception e){
            model.addAttribute("result", "Error");
            return "result";
        }
        return "home";}

}
