package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.StorageService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/files")
public class StorageController {
    private final StorageService storageService;
    private final UserService userService;

    public StorageController(StorageService storageService, UserService userService) {
        this.storageService = storageService;
        this.userService = userService;
    }

    @PostMapping
    public String uploadView(Authentication auth, @RequestParam("file") MultipartFile file, Model model) {
        // TODO finish the upload functionality
        System.out.println("File: " + file);
        User user = userService.getUser(auth.getName());
        //File(Integer fileId, String filename, String contenttype, String filesize, Integer userid, byte[] filedata)
        File toUpload = new File(
                null,
                file.getOriginalFilename(),
                file.getContentType(),
                file.getSize(),
                user.getUserid(),
                null // basically bytes but will be added in inputstream
        );
        if (storageService.fileExists(toUpload)) {
            model.addAttribute("failed", "file already exists, sorry");
            return "result";
        }
        try {
            toUpload.setFiledata(file.getBytes());
            storageService.fileStore(toUpload);
            model.addAttribute("success", "your file {" + toUpload.getFilename() + "} has been added");
        } catch (IOException e) {
            model.addAttribute("failed", "file cant be stored ");
            // e.printStackTrace();
        }
        return "result";
    }

    @GetMapping("/{fileId}")
    public ResponseEntity<ByteArrayResource> downloadView(Authentication auth, @PathVariable Integer fileId) {
        File downloadFile = storageService.getFileById(fileId);
        if (downloadFile == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(downloadFile.getContenttype()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +downloadFile.getFilename() + "\"")
                .body(new ByteArrayResource(downloadFile.getFiledata()));
    }

    @GetMapping("/del/{fileId}")
    public String deleteMapping(@PathVariable Integer fileId, Model model) {
        int res = storageService.removeFile(fileId);
        if (res <= 0){
            model.addAttribute("failed", "Your file id={" + fileId + "} cannot be deleted");
        } else {
            model.addAttribute("success", "Your file id={" + fileId + "} has been deleted");
        }
        return "result";
    }
}
