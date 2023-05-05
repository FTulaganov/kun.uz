package com.example.controller;



import com.example.DTO.Article.ArticleRequestDTO;
import com.example.DTO.JwtDTO;
import com.example.enums.ArticleStatus;
import com.example.enums.ProfileRole;

import com.example.service.ArticleService;
import com.example.util.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @PostMapping("")
    public ResponseEntity<ArticleRequestDTO> create(@RequestBody @Valid ArticleRequestDTO dto,
                                                    @RequestHeader("Authorization") String authorization) {
//        JwtDTO jwt = JwtUtil.getJwtDTO(authorization, ProfileRole.MODERATOR);
//        return ResponseEntity.ok(articleService.create(dto, jwt.getId()));
        return ResponseEntity.ok(articleService.create(dto, 1));
    }

    @PostMapping("/{id}")
    public ResponseEntity<ArticleRequestDTO> update(@RequestBody @Valid ArticleRequestDTO dto,
                                                    @RequestHeader("Authorization") String authorization,
                                                    @PathVariable("id") String articleId) {
        JwtDTO jwt = JwtUtil.getJwtDTO(authorization, ProfileRole.MODERATOR);
        return ResponseEntity.ok(articleService.update(dto, articleId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id,
                                    @RequestHeader("Authorization") String authorization) {
        JwtDTO jwt = JwtUtil.getJwtDTO(authorization, ProfileRole.MODERATOR, ProfileRole.ADMIN);
        return ResponseEntity.ok(articleService.delete(id));
    }

    @PostMapping("/change-status/{id}")
    public ResponseEntity<?> changeStatus(@PathVariable("id") Integer id,
                                          @RequestParam String status,
                                          @RequestHeader("Authorization") String authorization) {
        JwtDTO jwt = JwtUtil.getJwtDTO(authorization, ProfileRole.PUBLISHER);
        return ResponseEntity.ok(articleService.changeStatus(ArticleStatus.valueOf(status), id));
    }
}
