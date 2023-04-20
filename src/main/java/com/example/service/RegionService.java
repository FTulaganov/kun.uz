package com.example.service;

import com.example.DTO.*;
import com.example.entity.ArticleTypeEntity;
import com.example.entity.ProfileEntity;
import com.example.entity.RegionEntity;
import com.example.enums.ProfileRole;
import com.example.exps.NotFoundExeption;
import com.example.repository.ProfileRepository;
import com.example.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class RegionService {
    @Autowired
    private RegionRepository regionRepository;
    @Autowired
    private ProfileRepository profileRepository;

    public RegionDto create(RegionDto dto, Integer id) {

        Optional<ProfileEntity> admin = profileRepository.findById(id);
        if (!admin.get().getRole().equals(ProfileRole.ADMIN)) {
            throw new NotFoundExeption("ADMIN Not Found");
        }

        RegionEntity entity = new RegionEntity();
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        regionRepository.save(entity); // save region

        dto.setId(entity.getId());
        return dto;
    }

    public Object update(Integer id, RegionDto dto, Integer adminId) {
        Optional<RegionEntity> optional = regionRepository.findById(id);
        Optional<ProfileEntity> admin = profileRepository.findById(adminId);
        if (!admin.get().getRole().equals(ProfileRole.ADMIN)) {
            throw new NotFoundExeption("ADMIN Not Found");
        }
        if (optional.isEmpty()) {
            throw new NotFoundExeption("Article Type Not Found");
        }
        RegionEntity entity = optional.get();
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        regionRepository.save(entity); // update region
        return true;
    }

    public Boolean delete(Integer id, Integer adminId) {
        Optional<RegionEntity> optional = regionRepository.findById(id);
        Optional<ProfileEntity> admin = profileRepository.findById(adminId);
        if (!admin.get().getRole().equals(ProfileRole.ADMIN)) {
            throw new NotFoundExeption("ADMIN Not Found");
        }
        if (optional.isEmpty()) {
            throw new NotFoundExeption("Article Type Not Found");
        }
        RegionEntity entity = optional.get();
        entity.setVisible(Boolean.FALSE);
        regionRepository.delete(entity);
        return true;
    }

    public Page<RegionFullDto> pagingtion(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<RegionEntity> pageObj = regionRepository.findAll(pageable);
        Long totalCount = pageObj.getTotalElements();
        List<RegionEntity> entityList = pageObj.getContent();
        List<RegionFullDto> dtoList = new LinkedList<>();
        for (RegionEntity entity : entityList) {
            RegionFullDto dto = new RegionFullDto();
            dto.setId(entity.getId());
            dto.setNameUz(entity.getNameUz());
            dto.setNameRu(entity.getNameRu());
            dto.setNameEn(entity.getNameEn());
            dto.setVisible(entity.getVisible());
            dto.setCreated_date(entity.getCreatedDate());
            dtoList.add(dto);
        }
        Page<RegionFullDto> response = new PageImpl<RegionFullDto>(dtoList, pageable, totalCount);
        return response;
    }

    public List<RegionLangDto> getlang(String lang) {
        List<RegionLangDto> list = new LinkedList<>();

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

    private List<RegionLangDto> getEnlang() {
        List<RegionLangDto> list = new LinkedList<>();
        Iterable<RegionEntity> entity = regionRepository.findAll();
        for (RegionEntity regionEntity : entity) {
            RegionLangDto dto = new RegionLangDto();
            dto.setId(regionEntity.getId());
            dto.setName(regionEntity.getNameEn());
            list.add(dto);
        }
        return list;
    }

    private List<RegionLangDto> getRulang() {
        List<RegionLangDto> list = new LinkedList<>();
        Iterable<RegionEntity> entity = regionRepository.findAll();
        for (RegionEntity regionEntity : entity) {
            RegionLangDto dto = new RegionLangDto();
            dto.setId(regionEntity.getId());
            dto.setName(regionEntity.getNameRu());
            list.add(dto);
        }
        return list;
    }

    private List<RegionLangDto> getUzlang() {
        List<RegionLangDto> list = new LinkedList<>();
        Iterable<RegionEntity> entity = regionRepository.findAll();
        for (RegionEntity regionEntity : entity) {
            RegionLangDto dto = new RegionLangDto();
            dto.setId(regionEntity.getId());
            dto.setName(regionEntity.getNameUz());
            list.add(dto);
        }
        return list;
    }
}
