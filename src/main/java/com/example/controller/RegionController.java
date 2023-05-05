package com.example.controller;

import com.example.DTO.*;
import com.example.DTO.region.RegionDto;
import com.example.DTO.region.RegionFullDto;
import com.example.enums.ProfileRole;
import com.example.exps.MethodNotAllowedExeption;
import com.example.service.RegionService;
import com.example.util.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/region")
public class RegionController {
    @Autowired
    private RegionService regionService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody @Valid RegionDto dto,
                                    @RequestHeader("Authorization") String authorization) {
        String[] str = authorization.split(" ");
        String jwt = str[1];
        JwtDTO jwtDTO = JwtUtil.decode(jwt);
        return ResponseEntity.ok(regionService.create(dto, jwtDTO.getId()));
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<?> updateById(@PathVariable("id") Integer id,
                                        @RequestBody @Valid RegionDto dto,
                                        @RequestHeader("Authorization") String authorization) {
        String[] str = authorization.split(" ");
        String jwt = str[1];
        JwtDTO jwtDTO = JwtUtil.decode(jwt);
        return ResponseEntity.ok(regionService.update(id, dto, jwtDTO.getId()));
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id,
                                    @RequestHeader("Authorization") String authorization) {
        String[] str = authorization.split(" ");
        String jwt = str[1];
        JwtDTO jwtDTO = JwtUtil.decode(jwt);
        return ResponseEntity.ok(regionService.delete(id, jwtDTO.getId()));
    }
    @PutMapping(value = "/paging")
    public ResponseEntity<Page<RegionFullDto>> paging(@RequestHeader("Authorization") String authorization,
                                                      @RequestParam(value = "page", defaultValue = "1") int page,
                                                      @RequestParam(value = "size", defaultValue = "2") int size) {
        String[] str = authorization.split(" ");
        String jwt = str[1];
        JwtDTO jwtDTO = JwtUtil.decode(jwt);
        if (!jwtDTO.getRole().equals(ProfileRole.ADMIN)) {
            throw new MethodNotAllowedExeption("Method not allowed");
        }
        Page<RegionFullDto> response = regionService.pagingtion(page, size);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/getLang/{lang}")
    public ResponseEntity<?> getLan(@PathVariable("lang") String lang) {
        return ResponseEntity.ok(regionService.getlang(lang));
    }



}
