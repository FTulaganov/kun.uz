package com.example.controller;

import com.example.DTO.JwtDTO;
import com.example.DTO.region.RegionDto;
import com.example.DTO.region.RegionFullDto;
import com.example.enums.ProfileRole;
import com.example.service.RegionService;
import com.example.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
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

    @PostMapping("/private/create")
    public ResponseEntity<?> create(@RequestBody @Valid RegionDto dto,
                                    HttpServletRequest request) {
      JwtDTO jwtDTO =JwtUtil.checkForRequiredRole(request,ProfileRole.ADMIN);
        return ResponseEntity.ok(regionService.create(dto, jwtDTO.getId()));
    }

    @PostMapping("/private/update/{id}")
    public ResponseEntity<?> updateById(@PathVariable("id") Integer id,
                                        @RequestBody @Valid RegionDto dto,
                                        HttpServletRequest request) {
        JwtDTO jwtDTO =JwtUtil.checkForRequiredRole(request,ProfileRole.ADMIN);
        return ResponseEntity.ok(regionService.update(id, dto, jwtDTO.getId()));
    }

    @GetMapping("/private/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id,
                                    HttpServletRequest request) {
        JwtDTO jwtDTO =JwtUtil.checkForRequiredRole(request,ProfileRole.ADMIN);
        return ResponseEntity.ok(regionService.delete(id, jwtDTO.getId()));
    }
    @PutMapping(value = "/private/paging")
    public ResponseEntity<Page<RegionFullDto>> paging(HttpServletRequest request,
                                                      @RequestParam(value = "page", defaultValue = "1") int page,
                                                      @RequestParam(value = "size", defaultValue = "2") int size) {
        JwtDTO jwtDTO =JwtUtil.checkForRequiredRole(request,ProfileRole.ADMIN);
        Page<RegionFullDto> response = regionService.pagingtion(page, size);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/getLang/{lang}")
    public ResponseEntity<?> getLan(@PathVariable("lang") String lang) {
        return ResponseEntity.ok(regionService.getlang(lang));
    }



}
