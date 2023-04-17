package com.example.controller;

import com.example.DTO.ProfileDto;
import com.example.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @PostMapping("/")
    public ResponseEntity<ProfileDto> create(@RequestBody ProfileDto dto) {
        return ResponseEntity.ok(profileService.create(dto));
    }

    @GetMapping("")
    public ResponseEntity<List<ProfileDto>> getAll() {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileDto> getById(@PathVariable("id") Integer id) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProfileDto> deleteById(@PathVariable("id") Integer id) {
        return null;
    }

    @GetMapping("/pagination")
    public ResponseEntity<List<ProfileDto>> pagination(@RequestParam("page") int page,
                                                       @RequestParam("size") int size) {
        return null;
    }
}
