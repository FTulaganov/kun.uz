package com.example.service;

import com.example.DTO.ArticleTypeDto;
import com.example.DTO.ArticleTypeFullDto;
import com.example.DTO.GetLangAticleTypeDto;
import com.example.entity.ArticleTypeEntity;
import com.example.entity.ProfileEntity;
import com.example.enums.ProfileRole;
import com.example.exps.NotFoundExeption;
import com.example.repository.ArticleTypeRepository;
import com.example.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleTypeService {
    @Autowired
    private ArticleTypeRepository articleTypeRepository;
    @Autowired
    private ProfileRepository profileRepository;

    public ArticleTypeDto create(ArticleTypeDto dto, Integer id) {
        Optional<ProfileEntity> admin = profileRepository.findById(id);
        if (!admin.get().getRole().equals(ProfileRole.ADMIN)) {
            throw new NotFoundExeption("ADMIN Not Found");
        }

        ArticleTypeEntity entity = new ArticleTypeEntity();
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        articleTypeRepository.save(entity); // save articleType

        dto.setId(entity.getId());
        return dto;
    }


    public Boolean update(Integer id, ArticleTypeDto dto, Integer adminId) {
        Optional<ArticleTypeEntity> optional = articleTypeRepository.findById(id);
        Optional<ProfileEntity> admin = profileRepository.findById(adminId);
        if (!admin.get().getRole().equals(ProfileRole.ADMIN)) {
            throw new NotFoundExeption("ADMIN Not Found");
        }
        if (optional.isEmpty()) {
            throw new NotFoundExeption("Article Type Not Found");
        }
        ArticleTypeEntity entity = optional.get();
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        articleTypeRepository.save(entity); // update Article Type

        return true;
    }

    public Boolean delete(Integer id, Integer adminId) {
        Optional<ArticleTypeEntity> optional = articleTypeRepository.findById(id);
        Optional<ProfileEntity> admin = profileRepository.findById(adminId);
        if (!admin.get().getRole().equals(ProfileRole.ADMIN)) {
            throw new NotFoundExeption("ADMIN Not Found");
        }
        if (optional.isEmpty()) {
            throw new NotFoundExeption("Article Type Not Found");
        }
        ArticleTypeEntity entity = optional.get();
        entity.setVisible(Boolean.FALSE);
        articleTypeRepository.delete(entity);
        return true;
    }

    public Page<ArticleTypeFullDto> pagingtion(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<ArticleTypeEntity> pageObj = articleTypeRepository.findAll(pageable);
        Long totalCount = pageObj.getTotalElements();
        List<ArticleTypeEntity> entityList = pageObj.getContent();
        List<ArticleTypeFullDto> dtoList = new LinkedList<>();
        for (ArticleTypeEntity entity : entityList) {
            ArticleTypeFullDto dto = new ArticleTypeFullDto();
            dto.setId(entity.getId());
            dto.setNameUz(entity.getNameUz());
            dto.setNameRu(entity.getNameRu());
            dto.setNameEn(entity.getNameEn());
            dto.setVisible(entity.getVisible());
            dto.setCreated_date(entity.getCreatedDate());
            dtoList.add(dto);
        }
        Page<ArticleTypeFullDto> response = new PageImpl<ArticleTypeFullDto>(dtoList, pageable, totalCount);
        return response;
    }

    public List<GetLangAticleTypeDto> getlang(String lang) {
        List<GetLangAticleTypeDto> list = new LinkedList<>();

        switch (lang) {
            case "uz":
                list.addAll(getUzlang());
                break;
            case "ru":
                list.addAll(getRulang());
                break;
            case "en":
                list.addAll(getEnlang());
                break;
        }
        return list;
    }

    private List<GetLangAticleTypeDto> getEnlang() {
        List<GetLangAticleTypeDto> list = new LinkedList<>();
        Iterable<ArticleTypeEntity> entity = articleTypeRepository.findAll();
        for (ArticleTypeEntity articleTypeEntity : entity) {
            GetLangAticleTypeDto dto = new GetLangAticleTypeDto();
            dto.setId(articleTypeEntity.getId());
            dto.setName(articleTypeEntity.getNameEn());
            list.add(dto);
        }
        return list;
    }

    private List<GetLangAticleTypeDto> getRulang() {
        List<GetLangAticleTypeDto> list = new LinkedList<>();
        Iterable<ArticleTypeEntity> entity = articleTypeRepository.findAll();
        for (ArticleTypeEntity articleTypeEntity : entity) {
            GetLangAticleTypeDto dto = new GetLangAticleTypeDto();
            dto.setId(articleTypeEntity.getId());
            dto.setName(articleTypeEntity.getNameRu());
            list.add(dto);
        }
        return list;
    }

    private List<GetLangAticleTypeDto> getUzlang() {
        List<GetLangAticleTypeDto> list = new LinkedList<>();
        Iterable<ArticleTypeEntity> entity = articleTypeRepository.findAll();
        for (ArticleTypeEntity articleTypeEntity : entity) {
            GetLangAticleTypeDto dto = new GetLangAticleTypeDto();
            dto.setId(articleTypeEntity.getId());
            dto.setName(articleTypeEntity.getNameUz());
            list.add(dto);
        }
        return list;
    }
}
