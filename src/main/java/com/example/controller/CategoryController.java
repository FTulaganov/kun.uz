package com.example.controller;

import com.example.DTO.JwtDTO;
import com.example.DTO.category.CategoryDto;
import com.example.DTO.category.CategoryFullDto;
import com.example.enums.ProfileRole;
import com.example.exps.MethodNotAllowedExeption;
import com.example.service.CategoryService;
import com.example.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    @PostMapping("/privite/create")
    public ResponseEntity<?> create(@RequestBody @Valid CategoryDto dto,
                                    HttpServletRequest request) {
        JwtDTO jwt = JwtUtil.checkForRequiredRole(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(categoryService.create(dto, jwt.getId()));
    }

    @PostMapping("/privite/update/{id}")
    public ResponseEntity<?> updateById(@PathVariable("id") Integer id,
                                        @RequestBody @Valid CategoryDto dto,
                                        HttpServletRequest request) {
        JwtDTO jwt = JwtUtil.checkForRequiredRole(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(categoryService.update(id, dto, jwt.getId()));
    }

    @GetMapping("/privite/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id,
                                    @RequestHeader("Authorization") String authorization) {
        JwtDTO jwt = JwtUtil.getJwtDTO(authorization);
        return ResponseEntity.ok(categoryService.delete(id, jwt.getId()));
    }
    @PutMapping(value = "/privite/paging")
    public ResponseEntity<Page<CategoryFullDto>> paging(HttpServletRequest request,
                                                        @RequestParam(value = "page", defaultValue = "1") int page,
                                                        @RequestParam(value = "size", defaultValue = "2") int size) {
        JwtUtil.checkForRequiredRole(request, ProfileRole.ADMIN);
        Page<CategoryFullDto> response = categoryService.pagingtion(page, size);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/getLang/{lang}")
    public ResponseEntity<?> getLan(@PathVariable("lang") String lang) {
        return ResponseEntity.ok(categoryService.getlang(lang));
    }

}
