package com.chols.fu.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chols.fu.domain.model.entity.FileDataEntity;

public interface FileDataRepository extends JpaRepository<FileDataEntity, Integer> {
    
}
