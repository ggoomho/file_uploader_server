package com.chols.fu.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chols.fu.domain.model.entity.FileInfoEntity;

public interface FileInfoRepository extends JpaRepository<FileInfoEntity, Long> {
    
}
