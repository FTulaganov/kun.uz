package com.example.service;

import com.example.DTO.profile.ProfileDto;
import com.example.entity.AttachEntity;
import com.example.entity.ProfileEntity;
import com.example.exps.NotFoundExeption;
import com.example.repository.AttachRepository;
import com.example.repository.ProfileRepository;
import com.example.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private AttachRepository attachRepository;

    public ProfileDto create(ProfileDto dto, Integer id) {
        // check - homework
        isValidProfile(dto);
        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setPhone(dto.getPhone());
        entity.setEmail(dto.getEmail());
        entity.setRole(dto.getRole());
        entity.setPrt_id(id);
        entity.setPassword(MD5Util.getMd5Hash(dto.getPassword())); // MD5 ?
        profileRepository.save(entity); // save profile

        dto.setPassword(null);// hide password
        dto.setId(entity.getId());
        return dto;
    }

    public void isValidProfile(ProfileDto dto) {
        Optional<ProfileEntity> profileEntity = profileRepository.findByEmailAndPasswordAndVisible(dto.getEmail(), dto.getPassword(), true);
        if (!profileEntity.isEmpty()) {
            throw new IndexOutOfBoundsException("Email or password incorrect");
        }

    }

    public Boolean update(Integer id, ProfileDto dto, Integer adminId) {
        Optional<ProfileEntity> optional = profileRepository.findById(id);
        ProfileEntity entity = optional.get();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setPhone(dto.getPhone());
        entity.setPassword(MD5Util.getMd5Hash(dto.getPassword()));
        entity.setPrt_id(adminId);
        profileRepository.save(entity); // update profile

        return true;
    }

    public Boolean updateProfile(ProfileDto dto, Integer id) {
        Optional<ProfileEntity> optional = profileRepository.findById(id);
        if (optional.isEmpty()) {
            throw new NotFoundExeption("Profile Not Found");
        }
        ProfileEntity entity = optional.get();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setPhone(dto.getPhone());
        entity.setPassword(MD5Util.getMd5Hash(dto.getPassword()));
        profileRepository.save(entity); // update profile

        return true;
    }

    public boolean delete(Integer id, Integer adminId) {
        Optional<ProfileEntity> optional = profileRepository.findById(id);
        if (optional.isEmpty()) {
            throw new NotFoundExeption("Profile Not Found");
        }
        ProfileEntity entity = optional.get();
        entity.setVisible(Boolean.FALSE);
        entity.setPrt_id(adminId);
        profileRepository.save(entity); // update profile

        return true;
    }

    public Page<ProfileDto> pagingtion(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<ProfileEntity> pageObj = profileRepository.findAll(pageable);
        Long totalCount = pageObj.getTotalElements();
        List<ProfileEntity> entityList = pageObj.getContent();
        List<ProfileDto> dtoList = new LinkedList<>();
        for (ProfileEntity entity : entityList) {
            ProfileDto dto = new ProfileDto();
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            dto.setPhone(entity.getPhone());
            dto.setPassword(entity.getPassword());
            dto.setRole(entity.getRole());
            dto.setSurname(entity.getSurname());
            dto.setEmail(entity.getEmail());
            dtoList.add(dto);
        }
        Page<ProfileDto> response = new PageImpl<ProfileDto>(dtoList, pageable, totalCount);
        return response;
    }

    public boolean updateProfilePhoto(String photoId, Integer id) {
        Optional<ProfileEntity> optional = profileRepository.findById(id);
        Optional<AttachEntity> photo = attachRepository.findById(photoId);
        if (optional.isEmpty()) {
            throw new NotFoundExeption("Profile Not Found");
        }
        if (photo.isEmpty()) {
            throw new NotFoundExeption("Photo Not Found");
        }
        ProfileEntity entity = optional.get();
        entity.setAttachId(photo.get().getId());
        profileRepository.save(entity); // update profile photo

        return true;
    }
}
