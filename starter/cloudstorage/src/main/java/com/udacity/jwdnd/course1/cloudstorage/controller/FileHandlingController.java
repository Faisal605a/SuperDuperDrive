package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.FileInformation;
import com.udacity.jwdnd.course1.cloudstorage.services.FileHandlingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

    @Controller
    @RequestMapping("/file")
    public class FileHandlingController
    {
        @Autowired
        FileHandlingService fileHandlingSgervice;

        @PostMapping(value = "/upload")
        @ResponseStatus(HttpStatus.ACCEPTED)
        public String uploadDoc(Authentication authentication , @RequestParam("fileUpload") MultipartFile file, Model model) throws Exception
        {

            if(file.isEmpty()|| fileHandlingSgervice.fileExist(file, authentication.getName())){
                model.addAttribute("result", "Error");
                return "result";
            }
            try {

                fileHandlingSgervice.uploadDoc(file, authentication.getName());
                model.addAttribute("result", "success");
                model.addAttribute("files", fileHandlingSgervice.getDbFileList(authentication.getName()));
            }catch (Exception e){
                model.addAttribute("result", "Error");
                return "result";
            }

            return "result";

        }
        @GetMapping ("/delete/{fileid}")
        public String delete(Authentication authentication ,Model model,@PathVariable Integer fileid)throws Exception
        {

            try {

                fileHandlingSgervice.DeleteDoc(fileid);
                model.addAttribute("result", "success");
                model.addAttribute("files", fileHandlingSgervice.getDbFileList(authentication.getName()));
            }catch (Exception e){
                model.addAttribute("result", "Error");
                return "result";
            }

            return "result";
        }


        @GetMapping("/download/{fileid}")
        @ResponseBody
        public byte[] download(Authentication authentication ,@PathVariable Integer fileid,Model model){

            try {
                model.addAttribute("result", "success");
                model.addAttribute("files", fileHandlingSgervice.getDbFileList(authentication.getName()));
                return fileHandlingSgervice.getFile(fileid).getFiledata();

            }catch (Exception e){
                model.addAttribute("result", "Error");
                return fileHandlingSgervice.getFile(fileid).getFiledata();
            }

        }
    }

