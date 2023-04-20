package com.example.service;

import com.example.DTO.AuthDTO;
import com.example.DTO.AuthResponseDTO;
import com.example.DTO.ProfileDto;
import com.example.entity.ProfileEntity;
import com.example.enums.GeneralStatus;
import com.example.exps.AppBadRequestExeption;
import com.example.repository.ProfileRepository;
import com.example.util.JwtUtil;
import com.example.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private ProfileRepository profileRepository;

    public AuthResponseDTO login(AuthDTO dto) {
        Optional<ProfileEntity> optional = profileRepository.findByEmailAndPasswordAndVisible(
                dto.getEmail(),
                MD5Util.getMd5Hash(dto.getPassword()),
                true);
        if (optional.isEmpty()) {
            throw new IndexOutOfBoundsException("Email or password incorrect");
        }
        ProfileEntity entity = optional.get();
        if (!entity.getStatus().equals(GeneralStatus.ACTIVE)) {
            throw new AppBadRequestExeption("Wrong status");
        }
        AuthResponseDTO responseDTO = new AuthResponseDTO();
        responseDTO.setName(entity.getName());
        responseDTO.setSurname(entity.getSurname());
        responseDTO.setRole(entity.getRole());
        responseDTO.setJwt(JwtUtil.encode(entity.getId(), entity.getRole()));
        return responseDTO;
    }

    public ProfileDto registration(ProfileDto dto) {
        Optional<ProfileEntity> optional = profileRepository.findByPhoneAndPassword(dto.getPhone(), dto.getPassword());
        if (!optional.isEmpty()){
            throw  new AppBadRequestExeption("such a user exists");
        }
        ProfileEntity profileEntity = new ProfileEntity();
        profileEntity.setName(dto.getName());
        profileEntity.setSurname(dto.getSurname());
        profileEntity.setPhone(dto.getPhone());
        profileEntity.setEmail(dto.getEmail());
        profileEntity.setPassword(dto.getPassword());
        profileRepository.save(profileEntity);

        dto.setPassword(null);
        dto.setId(profileEntity.getId());
        return dto;
    }
}
