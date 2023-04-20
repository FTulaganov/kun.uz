package com.example.repository;

import com.example.entity.ArticleTypeEntity;
import com.example.entity.RegionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RegionRepository extends CrudRepository<RegionEntity,Integer>, PagingAndSortingRepository<RegionEntity, Integer> {
}
