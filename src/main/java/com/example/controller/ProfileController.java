package com.example.controller;

import com.example.DTO.JwtDTO;
import com.example.DTO.profile.ProfileDto;
import com.example.enums.ProfileRole;
import com.example.service.ProfileService;
import com.example.util.JwtUtil;
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

    @PostMapping("/create")
    public ResponseEntity<ProfileDto> create(@RequestBody @Valid ProfileDto dto,
                                             @RequestHeader("Authorization") String authorization) {
        JwtDTO jwt = JwtUtil.getJwtDTO(authorization, ProfileRole.ADMIN);
        return ResponseEntity.ok(profileService.create(dto, jwt.getId()));
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id,
                                    @RequestHeader("Authorization") String authorization) {
        String[] str = authorization.split(" ");
        String jwt = str[1];
        JwtDTO jwtDTO = JwtUtil.decode(jwt);
        return ResponseEntity.ok(profileService.delete(id, jwtDTO.getId()));
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<?> updateById(@PathVariable("id") Integer id,
                                        @RequestBody @Valid ProfileDto dto,
                                        @RequestHeader("Authorization") String authorization) {
        JwtDTO jwt = JwtUtil.getJwtDTO(authorization, ProfileRole.ADMIN);
        return ResponseEntity.ok(profileService.update(id, dto, jwt.getId()));
    }

    @PutMapping(value = "/paging")
    public ResponseEntity<Page<ProfileDto>> paging(@RequestHeader("Authorization") String authorization,
                                                   @RequestParam(value = "page", defaultValue = "1") int page,
                                                   @RequestParam(value = "size", defaultValue = "2") int size) {
        JwtUtil.getJwtDTO(authorization, ProfileRole.ADMIN);
        Page<ProfileDto> response = profileService.pagingtion(page, size);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestBody @Valid ProfileDto dto,
                                    @RequestHeader("Authorization") String authorization) {
        String[] str = authorization.split(" ");
        String jwt = str[1];
        JwtDTO jwtDTO = JwtUtil.decode(jwt);
        return ResponseEntity.ok(profileService.updateProfile(dto, jwtDTO.getId()));
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
