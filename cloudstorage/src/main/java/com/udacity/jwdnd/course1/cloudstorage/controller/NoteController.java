package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/notes")
public class NoteController {
    private final UserService userService;
    private final NoteService noteService;
    private final HomeController homeController;

    public NoteController(UserService userService, NoteService noteService, HomeController homeController) {
        this.userService = userService;
        this.noteService = noteService;
        this.homeController = homeController;
    }

    // Treat all add or update as post request at controller level, then disginguish the difference in NoteService
    @PostMapping
    public String addOrUpdateNote(Authentication auth, Note noteForm, Model model) {
        Integer userid = userService.getUser(auth.getName()).getUserid();
        // System.out.println("noteForm" + noteForm);
        noteForm.setUserid(userid);
        int isAdding = noteService.add(noteForm);
        // System.out.println("adding Note" + isAdding);
        if (isAdding <=0 ) {
            model.addAttribute("failed", true);
        } else {
            model.addAttribute("success", true);
        }
        // TODO handle error? should not have to add another note tho, Maybe add an try catch?
        return "result";
    }


    // The user may go to /notes?id={id} to get a json format of a note, this is for debugging
    @GetMapping("/{id}")
    @ResponseBody
    public Note getNote(@PathVariable Integer id) {
        // System.out.println("getting Note here");
        return noteService.getNoteById(id);
    }

    @GetMapping("/del/{id}")
    public String deleteNote(@PathVariable Integer id, Model model) {
        // System.out.println("deleting Note here");
        Integer check = noteService.delete(id);
        if (check <= 0 ) {
            model.addAttribute("failed", true);
        } else {
            model.addAttribute("success", true);
        }
        return "result";
    }
    // TODO add a result model builder
}
