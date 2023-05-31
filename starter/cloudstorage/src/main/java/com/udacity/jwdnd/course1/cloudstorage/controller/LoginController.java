package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/login")
public class LoginController {

//    @RequestMapping(value = "/login", method = RequestMethod.GET)
//    public String displayLogin(Login loginModel) {
//        return "login";
//    }
    @GetMapping()
    public String loginView() {
        return "login";
    }
}