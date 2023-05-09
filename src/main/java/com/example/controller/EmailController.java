package com.example.controller;

import com.example.DTO.category.CategoryFullDto;
import com.example.DTO.email.EmailDate;
import com.example.DTO.email.EmailHistoryDTO;
import com.example.DTO.JwtDTO;
import com.example.enums.ProfileRole;
import com.example.service.EmailHistoryService;
import com.example.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/email")
public class EmailController {

    @Autowired
    private EmailHistoryService emailHistoryService;
    @GetMapping("/private/getEmail/{email}")
    public ResponseEntity<List<EmailHistoryDTO>> delete(@PathVariable String email,
                                                        HttpServletRequest request) {
        JwtUtil.checkForRequiredRole(request, ProfileRole.ADMIN);
        return ResponseEntity.ok( emailHistoryService.getEmail(email));
    }

    @PostMapping("/getEmail/GivenDate")
    public ResponseEntity<List<EmailHistoryDTO>> givenDate(@RequestBody EmailDate date,
                                                        @RequestHeader("Authorization") String authorization) {
       JwtUtil.getJwtDTO(authorization);
        return ResponseEntity.ok( emailHistoryService.getDateGiven(date));
    }
    @PutMapping(value = "/private/paging")
    public ResponseEntity<Page<EmailHistoryDTO>> paging(HttpServletRequest request,
                                                        @RequestParam(value = "page", defaultValue = "2") int page,
                                                        @RequestParam(value = "size", defaultValue = "2") int size) {
        JwtDTO jwt = JwtUtil.checkForRequiredRole(request, ProfileRole.ADMIN);
        Page<EmailHistoryDTO> response = emailHistoryService.pagingtion(page, size);
        return ResponseEntity.ok(response);
    }
}
