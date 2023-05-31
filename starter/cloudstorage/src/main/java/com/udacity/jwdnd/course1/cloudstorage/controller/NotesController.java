package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller()
@RequestMapping()
public class NotesController {

    @Autowired
    private  NoteService noteService;


    @PostMapping("/note/save")
    public String save(Authentication authentication ,@ModelAttribute  NoteForm noteForm, Model model){
        try {

            if(noteForm.getId()==null) {
                noteService.saveNotes(noteForm, authentication.getName());

            }else
                noteService.updateNote(noteForm, authentication.getName());

            model.addAttribute("result", "success");
            model.addAttribute("notes", this.noteService.getNotes(authentication.getName()));
        }catch (Exception e){
            model.addAttribute("result", "Error");
            return "result";
        }


        return "result";


    }
    @GetMapping ("/note/delete/{noteId}")
    public String delete(Authentication authentication ,Model model,@PathVariable Integer noteId){

        try {
            noteService.deleteNote(noteId);
            model.addAttribute("result", "success");
            model.addAttribute("notes", noteService.getNotes(authentication.getName()));
        }catch (Exception e){
            model.addAttribute("result", "Error");
            return "result";
        }


        return "result";


    }


//    @PostMapping("/edit")
//    public String edit(Authentication authentication ,Model model) {
//
//        model.addAttribute("notes", noteService.getNotes(authentication.getName()));
//        model.addAttribute("result", "success");
//        return "result";
//    }

}
