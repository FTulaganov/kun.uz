package com.example.controller;

import com.example.DTO.attach.AttachDTO;
import com.example.DTO.JwtDTO;
import com.example.enums.ProfileRole;
import com.example.service.AttachService;
import com.example.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/attach")
public class AttachController {
    @Autowired
    private AttachService attachService;

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) {
        String fileName = attachService.saveToSystem3(file);
        return ResponseEntity.ok().body(fileName);
    }

    @GetMapping(value = "/open/{fileName}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] open(@PathVariable("fileName") String fileName) {
        if (fileName != null && fileName.length() > 0) {
            try {
                return this.attachService.loadImage(fileName);
            } catch (Exception e) {
                e.printStackTrace();
                return new byte[0];
            }
        }
        return null;
    }

    @GetMapping(value = "/open_general/{fileName}", produces = MediaType.ALL_VALUE)
    public byte[] open_general(@PathVariable("fileName") String fileName) {
        // ahshdasd.jpg
        return attachService.open_general(fileName);
    }

    @GetMapping("/download/{fineName}")
    public ResponseEntity<Resource> download(@PathVariable("fineName") String fileName) {
        Resource file = attachService.download(fileName);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PutMapping(value = "/paging")
    public ResponseEntity<Page<AttachDTO>> paging(@RequestHeader("Authorization") String authorization,
                                                  @RequestParam(value = "page", defaultValue = "1") int page,
                                                  @RequestParam(value = "size", defaultValue = "2") int size) {
        JwtDTO jwt = JwtUtil.getJwtDTO(authorization, ProfileRole.ADMIN);
        Page<AttachDTO> response = attachService.pagingtion(page, size);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/delete/{fineName}")
    public ResponseEntity<Boolean> delete(@PathVariable("fineName") String fileName,
                                          @RequestHeader("Authorization") String authorization) {
        JwtDTO jwt = JwtUtil.getJwtDTO(authorization, ProfileRole.ADMIN);
        return ResponseEntity.ok(attachService.delete(fileName));
    }
}
