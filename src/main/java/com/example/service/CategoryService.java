package com.example.service;

import com.example.DTO.category.CategoryDto;
import com.example.DTO.category.CategoryFullDto;
import com.example.DTO.category.CategoryLangDto;
import com.example.entity.CategoryEntity;
import com.example.entity.ProfileEntity;
import com.example.enums.ProfileRole;
import com.example.exps.ItemNotFoundExeption;
import com.example.exps.NotFoundExeption;
import com.example.repository.CategoryRepository;
import com.example.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProfileRepository profileRepository;

    public CategoryDto create(CategoryDto dto, Integer id) {
        CategoryEntity entity = new CategoryEntity();
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        entity.setPrtId(id);
        categoryRepository.save(entity); // save categoy

        dto.setId(entity.getId());
        return dto;
    }

    public Boolean update(Integer id, CategoryDto dto, Integer adminId) {
        Optional<CategoryEntity> optional = categoryRepository.findById(id);
        if (optional.isEmpty()) {
            throw new NotFoundExeption("Article Type Not Found");
        }
        CategoryEntity entity = optional.get();
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        categoryRepository.save(entity); // update region
        return true;
    }

    public Boolean delete(Integer id, Integer adminId) {
        Optional<CategoryEntity> optional = categoryRepository.findById(id);
        if (optional.isEmpty()) {
            throw new NotFoundExeption("Article Type Not Found");
        }
        categoryRepository.updateVisible(id, adminId);
        return true;
    }

    public Page<CategoryFullDto> pagingtion(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<CategoryEntity> pageObj = categoryRepository.findAll(pageable);
        Long totalCount = pageObj.getTotalElements();
        List<CategoryEntity> entityList = pageObj.getContent();
        List<CategoryFullDto> dtoList = new LinkedList<>();
        for (CategoryEntity entity : entityList) {
            CategoryFullDto dto = new CategoryFullDto();
            dto.setId(entity.getId());
            dto.setNameUz(entity.getNameUz());
            dto.setNameRu(entity.getNameRu());
            dto.setNameEn(entity.getNameEn());
            dto.setVisible(entity.getVisible());
            dto.setCreated_date(entity.getCreatedDate());
            dtoList.add(dto);
        }
        Page<CategoryFullDto> response = new PageImpl<CategoryFullDto>(dtoList, pageable, totalCount);
        return response;
    }

    public List<CategoryLangDto> getlang(String lang) {
        List<CategoryLangDto> list = new LinkedList<>();

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

    private List<CategoryLangDto> getEnlang() {
        List<CategoryLangDto> list = new LinkedList<>();
        Iterable<CategoryEntity> entity = categoryRepository.findAll();
        for (CategoryEntity regionEntity : entity) {
            CategoryLangDto dto = new CategoryLangDto();
            dto.setId(regionEntity.getId());
            dto.setName(regionEntity.getNameEn());
            list.add(dto);
        }
        return list;
    }

    private List<CategoryLangDto> getRulang() {
        List<CategoryLangDto> list = new LinkedList<>();
        Iterable<CategoryEntity> entity = categoryRepository.findAll();
        for (CategoryEntity regionEntity : entity) {
            CategoryLangDto dto = new CategoryLangDto();
            dto.setId(regionEntity.getId());
            dto.setName(regionEntity.getNameRu());
            list.add(dto);
        }
        return list;
    }

    private List<CategoryLangDto> getUzlang() {
        List<CategoryLangDto> list = new LinkedList<>();
        Iterable<CategoryEntity> entity = categoryRepository.findAll();
        for (CategoryEntity regionEntity : entity) {
            CategoryLangDto dto = new CategoryLangDto();
            dto.setId(regionEntity.getId());
            dto.setName(regionEntity.getNameUz());
            list.add(dto);
        }
        return list;
    }

    public CategoryEntity get(Integer id) {
        Optional<CategoryEntity> optional = categoryRepository.findById(id);
        if (optional.isEmpty()) {
            throw new ItemNotFoundExeption("Item not found: " + id);
        }
        return optional.get();
    }
}
