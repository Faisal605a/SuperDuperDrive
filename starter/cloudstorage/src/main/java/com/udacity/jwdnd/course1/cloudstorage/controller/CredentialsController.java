package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller()
@RequestMapping()
public class CredentialsController {
    @Autowired
    private CredentialsService credentialsService;
    @PostMapping("/credentials/save")
    public String insertCredentials(Authentication authentication , @ModelAttribute Credentials credentials, Model model){


        try {

            if(credentials.getCredentialid()==null) {
                credentialsService.InsertCredentials(credentials, authentication.getName());

            }else
                credentialsService.updateCredentials(credentials, authentication.getName());

        }catch (Exception e){
            model.addAttribute("result", "Error");
            return "result";
        }
            model.addAttribute("result", "success");
            model.addAttribute("credentials", credentialsService.getAllCredentials(authentication.getName()));
            return "result";


        }
    @GetMapping("/credentials/delete/{credentialId}")
    public String delete(Authentication authentication ,Model model,@PathVariable Integer credentialId){
        try {

            credentialsService.deleteCredential(credentialId);

        }catch (Exception e){
            model.addAttribute("result", "Error");
            return "result";
        }
        model.addAttribute("result", "success");
        model.addAttribute("credentials", credentialsService.getAllCredentials(authentication.getName()));

        return "result";


    }

}
