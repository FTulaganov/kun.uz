package com.example.service;

import com.example.DTO.registration.RegistrationDTO;
import com.example.DTO.registration.RegistrationResponseDTO;
import com.example.DTO.auth.AuthDTO;
import com.example.DTO.auth.AuthResponseDTO;
import com.example.entity.EmailEntity;
import com.example.entity.ProfileEntity;
import com.example.enums.GeneralStatus;
import com.example.enums.ProfileRole;
import com.example.exps.AppBadRequestExeption;
import com.example.repository.EmailHistoryReopository;
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

    @Autowired
    private MailSenderervice mailSenderervice;
    @Autowired
    private EmailHistoryReopository emailHistoryReopository;

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

    public RegistrationResponseDTO registration(RegistrationDTO dto) {
        // check -?
        Optional<ProfileEntity> optional = profileRepository.findByEmail(dto.getEmail());
        if (optional.isPresent()) {
            throw new IndexOutOfBoundsException("Email already exists mazgi.");
        }
        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setRole(ProfileRole.USER);
        entity.setPhone(dto.getPhone());
        entity.setEmail(dto.getEmail());
        entity.setPassword(MD5Util.getMd5Hash(dto.getPassword()));
        entity.setStatus(GeneralStatus.REGISTER);
        mailSenderervice.sendRegistrationEmailMime(dto.getEmail());
        emailHistory(dto.getEmail());
        String s = "Verification link was send to email: " + dto.getEmail();
        profileRepository.save(entity);
        return new RegistrationResponseDTO(s);
    }

    private void emailHistory(String email) {
        EmailEntity entity = new EmailEntity();
        entity.setEmail(email);
        entity.setMessage("Message send your email");
        emailHistoryReopository.save(entity);
    }

    public RegistrationResponseDTO emailVerification(String jwt) {
        // asjkdhaksdh.daskhdkashkdja
        String email = JwtUtil.decodeEmailVerification(jwt);
        Optional<ProfileEntity> optional = profileRepository.findByEmail(email);
        if (optional.isEmpty()) {
            throw new IndexOutOfBoundsException("Email not found.");
        }
        ProfileEntity entity = optional.get();
        if (!entity.getStatus().equals(GeneralStatus.REGISTER)) {
            throw new AppBadRequestExeption("Wrong status");
        }
        entity.setStatus(GeneralStatus.ACTIVE);
        profileRepository.save(entity);
        return new RegistrationResponseDTO("Registration Done");
    }

}
