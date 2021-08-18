package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/credentials")
public class CredentialController {
    private final UserService userService;
    private final CredentialService credentialService;

    public CredentialController(UserService userService,
                             CredentialService credentialService) {
        this.userService = userService;
        this.credentialService = credentialService;
    }

    @PostMapping
    public String addOrUpdateCredential(Authentication auth, Credential credentialForm, Model model) {
        String username = auth.getName();
        User user = userService.getUser(username);
        Integer result;
        if (credentialForm.getCredentialid() != null) {
            result = credentialService.updateCredential(credentialForm);
        } else {
            credentialForm.setUserid(user.getUserid());
            result = credentialService.addCredential(credentialForm);
        }
        if (result <= 0 ) {
            model.addAttribute("failed", true);
        } else {
            model.addAttribute("success", true);
        }
        return "result";
    }

    @GetMapping("/del/{id}")
    public String deleteCredential(@PathVariable Integer id, Model model) {
        Integer result = credentialService.deleteCredential(id);
        if (result.intValue() <= 0 ) {
            model.addAttribute("failed", true);
        } else {
            model.addAttribute("success", true);
        }
        return "result";
    }

}
