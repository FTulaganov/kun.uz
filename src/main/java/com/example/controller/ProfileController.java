package com.example.controller;

import com.example.DTO.JwtDTO;
import com.example.DTO.profile.ProfileDto;
import com.example.enums.ProfileRole;
import com.example.service.ProfileService;
import com.example.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @PostMapping("/private/create")
    public ResponseEntity<ProfileDto> create(@RequestBody @Valid ProfileDto dto,
                                             HttpServletRequest request) {
        JwtDTO jwt = JwtUtil.checkForRequiredRole(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(profileService.create(dto, jwt.getId()));
    }

    @GetMapping("/private/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id,
                                   HttpServletRequest request) {
       Integer profilId = (Integer) request.getAttribute("id");
        return ResponseEntity.ok(profileService.delete(id, profilId));
    }

    @PostMapping("/private/update/{id}")
    public ResponseEntity<?> updateById(@PathVariable("id") Integer id,
                                        @RequestBody @Valid ProfileDto dto,
                                       HttpServletRequest request) {
        JwtDTO jwt = JwtUtil.checkForRequiredRole(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(profileService.update(id, dto, jwt.getId()));
    }

    @PutMapping(value = "/private/paging")
    public ResponseEntity<Page<ProfileDto>> paging(HttpServletRequest request,
                                                   @RequestParam(value = "page", defaultValue = "1") int page,
                                                   @RequestParam(value = "size", defaultValue = "2") int size) {
        JwtUtil.checkForRequiredRole(request, ProfileRole.ADMIN);
        Page<ProfileDto> response = profileService.pagingtion(page, size);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/private/update")
    public ResponseEntity<?> update(@RequestBody @Valid ProfileDto dto,
                                    HttpServletRequest request) {
       Integer id = (Integer) request.getAttribute("id");
        return ResponseEntity.ok(profileService.updateProfile(dto, id));
    }

    @PostMapping("/updatePhoto/{id}")
    public ResponseEntity<?> updatePhoto(@PathVariable("id") String id,
                                         @RequestHeader("Authorization") String authorization) {
        String[] str = authorization.split(" ");
        String jwt = str[1];
        JwtDTO jwtDTO = JwtUtil.decode(jwt);
        return ResponseEntity.ok(profileService.updateProfilePhoto(id, jwtDTO.getId()));
    }
}
