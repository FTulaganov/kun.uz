package com.example.repository;

import com.example.entity.ArticleTypeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ArticleTypeRepository extends CrudRepository<ArticleTypeEntity,Integer>, PagingAndSortingRepository<ArticleTypeEntity, Integer> {
}
