package com.example.controller;

import com.example.DTO.JwtDTO;
import com.example.DTO.category.CategoryDto;
import com.example.DTO.category.CategoryFullDto;
import com.example.enums.ProfileRole;
import com.example.exps.MethodNotAllowedExeption;
import com.example.service.CategoryService;
import com.example.util.JwtUtil;
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
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody @Valid CategoryDto dto,
                                    @RequestHeader("Authorization") String authorization) {
        JwtDTO jwt = JwtUtil.getJwtDTO(authorization, ProfileRole.MODERATOR);
        return ResponseEntity.ok(categoryService.create(dto, jwt.getId()));
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<?> updateById(@PathVariable("id") Integer id,
                                        @RequestBody @Valid CategoryDto dto,
                                        @RequestHeader("Authorization") String authorization) {
        JwtDTO jwt = JwtUtil.getJwtDTO(authorization, ProfileRole.MODERATOR);
        return ResponseEntity.ok(categoryService.update(id, dto, jwt.getId()));
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id,
                                    @RequestHeader("Authorization") String authorization) {
        String[] str = authorization.split(" ");
        String jwt = str[1];
        JwtDTO jwtDTO = JwtUtil.decode(jwt);
        return ResponseEntity.ok(categoryService.delete(id, jwtDTO.getId()));
    }
    @PutMapping(value = "/paging")
    public ResponseEntity<Page<CategoryFullDto>> paging(@RequestHeader("Authorization") String authorization,
                                                        @RequestParam(value = "page", defaultValue = "1") int page,
                                                        @RequestParam(value = "size", defaultValue = "2") int size) {
        JwtDTO jwt = JwtUtil.getJwtDTO(authorization, ProfileRole.ADMIN);
        Page<CategoryFullDto> response = categoryService.pagingtion(page, size);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/getLang/{lang}")
    public ResponseEntity<?> getLan(@PathVariable("lang") String lang) {
        return ResponseEntity.ok(categoryService.getlang(lang));
    }

}
