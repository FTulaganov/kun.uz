package com.example.repository;

import com.example.entity.CategoryEntity;
import com.example.entity.EmailEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface EmailHistoryReopository extends CrudRepository<EmailEntity, Integer>, PagingAndSortingRepository<EmailEntity, Integer> {

    List<EmailEntity> findAllByEmail(String email);
    List<EmailEntity> getByCreatedDateBetween(LocalDateTime start,LocalDateTime end);
}
