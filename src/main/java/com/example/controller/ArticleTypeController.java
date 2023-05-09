package com.example.controller;

import com.example.DTO.articleType.ArticleTypeDto;
import com.example.DTO.articleType.ArticleTypeFullDto;
import com.example.DTO.JwtDTO;
import com.example.enums.ProfileRole;
import com.example.exps.MethodNotAllowedExeption;
import com.example.service.ArticleTypeService;
import com.example.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/articletype")
public class ArticleTypeController {

    @Autowired
    private ArticleTypeService articleTypeService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody @Valid ArticleTypeDto dto,
                                    HttpServletRequest request) {
       Integer id = (Integer) request.getAttribute("id");
        return ResponseEntity.ok(articleTypeService.create(dto,id));
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<?> updateById(@PathVariable("id") Integer id,
                                        @RequestBody @Valid ArticleTypeDto dto,
                                       HttpServletRequest request) {
        Integer idAdmin = (Integer) request.getAttribute("id");
        return ResponseEntity.ok(articleTypeService.update(id, dto, idAdmin));
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id,
                                    @RequestHeader("Authorization") String authorization) {
        JwtDTO jwt = JwtUtil.getJwtDTO(authorization, ProfileRole.ADMIN);
        return ResponseEntity.ok(articleTypeService.delete(id, jwt.getId()));
    }

    @PutMapping(value = "/paging")
    public ResponseEntity<Page<ArticleTypeFullDto>> paging(@RequestHeader("Authorization") String authorization,
                                                           @RequestParam(value = "page", defaultValue = "1") int page,
                                                           @RequestParam(value = "size", defaultValue = "2") int size) {
        JwtDTO jwt = JwtUtil.getJwtDTO(authorization, ProfileRole.ADMIN);
        Page<ArticleTypeFullDto> response = articleTypeService.pagingtion(page, size);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getLang/{lang}")
    public ResponseEntity<?> getLan(@PathVariable("lang") String lang) {
        return ResponseEntity.ok(articleTypeService.getlang(lang));
    }

}
