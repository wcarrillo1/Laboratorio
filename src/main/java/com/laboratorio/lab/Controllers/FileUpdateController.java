package com.laboratorio.lab.Controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/files")
public class FileUpdateController {

    @PostMapping("/upload")
    public ResponseEntity<List<String>> handleFileUpload(@RequestParam("file") MultipartFile file) {
        List<String> emails = new ArrayList<>();

        Pattern emailPattern = Pattern.compile("\\S+@\\S+");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Matcher matcher = emailPattern.matcher(line);
                while (matcher.find()) {
                    emails.add(matcher.group());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build(); 
        }

        return ResponseEntity.ok(emails);
    }
}
