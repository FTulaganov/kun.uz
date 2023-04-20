package com.example.controller;

import com.example.DTO.ArticleTypeDto;
import com.example.DTO.ArticleTypeFullDto;
import com.example.DTO.JwtDTO;
import com.example.DTO.ProfileDto;
import com.example.enums.ProfileRole;
import com.example.exps.MethodNotAllowedExeption;
import com.example.service.ArticleTypeService;
import com.example.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/articletype")
public class ArticleTypeController {

    @Autowired
    private ArticleTypeService articleTypeService;
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody ArticleTypeDto dto,
                                             @RequestHeader("Authorization") String authorization) {
        String[] str = authorization.split(" ");
        String jwt = str[1];
        JwtDTO jwtDTO = JwtUtil.decode(jwt);
        return ResponseEntity.ok(articleTypeService.create(dto, jwtDTO.getId()));
    }
    @PostMapping("/update/{id}")
    public ResponseEntity<?> updateById(@PathVariable("id") Integer id,
                                        @RequestBody ArticleTypeDto dto,
                                        @RequestHeader("Authorization") String authorization) {
        String[] str = authorization.split(" ");
        String jwt = str[1];
        JwtDTO jwtDTO = JwtUtil.decode(jwt);
        return ResponseEntity.ok(articleTypeService.update(id, dto, jwtDTO.getId()));
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id,
                                    @RequestHeader("Authorization") String authorization) {
        String[] str = authorization.split(" ");
        String jwt = str[1];
        JwtDTO jwtDTO = JwtUtil.decode(jwt);
        return ResponseEntity.ok(articleTypeService.delete(id, jwtDTO.getId()));
    }
    @PutMapping(value = "/paging")
    public ResponseEntity<Page<ArticleTypeFullDto>> paging(@RequestHeader("Authorization") String authorization,
                                                           @RequestParam(value = "page", defaultValue = "1") int page,
                                                           @RequestParam(value = "size", defaultValue = "2") int size) {
        String[] str = authorization.split(" ");
        String jwt = str[1];
        JwtDTO jwtDTO = JwtUtil.decode(jwt);
        if (!jwtDTO.getRole().equals(ProfileRole.ADMIN)) {
            throw new MethodNotAllowedExeption("Method not allowed");
        }
        Page<ArticleTypeFullDto> response = articleTypeService.pagingtion(page, size);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/getLang/{lang}")
    public ResponseEntity<?> getLan(@PathVariable("lang") String lang) {
        return ResponseEntity.ok(articleTypeService.getlang(lang));
    }

}
