package com.example.service;

import com.example.DTO.category.CategoryFullDto;
import com.example.DTO.email.EmailDate;
import com.example.DTO.email.EmailHistoryDTO;
import com.example.entity.CategoryEntity;
import com.example.entity.EmailEntity;
import com.example.exps.NotFoundExeption;
import com.example.repository.EmailHistoryReopository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class EmailHistoryService {
    @Autowired
    private EmailHistoryReopository emailHistoryReopository;

    public List<EmailHistoryDTO> getEmail(String email) {
        List<EmailEntity> entity = emailHistoryReopository.findAllByEmail(email);
        if (entity.isEmpty()) {
            throw new NotFoundExeption("Not found Email");
        }
        List<EmailHistoryDTO> dtoList = toDTO(entity);
        return dtoList;
    }

    private List<EmailHistoryDTO> toDTO(List<EmailEntity> entity) {
        List<EmailHistoryDTO> dtos = new LinkedList<>();
        for (EmailEntity emailEntity : entity) {
            EmailHistoryDTO dto = new EmailHistoryDTO();
            dto.setCreated_date(emailEntity.getCreatedDate());
            dto.setEmail(emailEntity.getEmail());
            dto.setMessage(emailEntity.getMessage());
            dtos.add(dto);
        }
        return dtos;
    }

    public List<EmailHistoryDTO> getDateGiven(EmailDate date) {
        List<EmailEntity> entity = emailHistoryReopository.getByCreatedDateBetween(date.getStartDate(), date.getEndDate());
        if (entity.isEmpty()) {
            throw new NotFoundExeption("Not found date");
        }
        List<EmailHistoryDTO> dtoList = toDTO(entity);
        return dtoList;
    }

    public Page<EmailHistoryDTO> pagingtion(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<EmailEntity> pageObj = emailHistoryReopository.findAll(pageable);
        Long totalCount = pageObj.getTotalElements();
        List<EmailEntity> entityList = pageObj.getContent();
        List<EmailHistoryDTO> dtoList = new LinkedList<>();
        for (EmailEntity entity : entityList) {
            EmailHistoryDTO dto = new EmailHistoryDTO();
            dto.setId(entity.getId());
            dto.setCreated_date(entity.getCreatedDate());
            dto.setEmail(entity.getEmail());
            dto.setMessage(entity.getMessage());
            dtoList.add(dto);
        }
        Page<EmailHistoryDTO> response = new PageImpl<EmailHistoryDTO>(dtoList, pageable, totalCount);
        return response;
    }
}
