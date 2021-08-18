package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.StorageService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {

    private final UserService userService;
    private final NoteService noteService;
    private final CredentialService credentialService;
    private final StorageService storageService;

    public HomeController(UserService userService,
                          NoteService noteService,
                          CredentialService credentialService,
                          StorageService storageService) {
        this.userService = userService;
        this.noteService = noteService;
        this.credentialService = credentialService;
        this.storageService = storageService;
    }

    @GetMapping
    public String getHome(Authentication auth, Model model) {
        // TODO: verify assumption that user is always logged in when getting here
        String username = auth.getName();
        User currentUser = userService.getUser(username);
        List<Note> notes = noteService.getNotesByUserid(currentUser.getUserid());
        // System.out.println("notes" + notes);
        List<Credential> credentials = credentialService.getCredentialsByUserid(currentUser.getUserid());
        model.addAttribute("files", storageService.getFilesByUserid(currentUser.getUserid()));
        model.addAttribute("notes", notes);
        model.addAttribute("credentials", credentials);
        return "home";
    }

    public static void resultModel(Model model, int checking, String success, String failure) {
        if (checking <= 0) {
            model.addAttribute("failed", success);
        } else {
            model.addAttribute("success", failure);
        }
    }
}
