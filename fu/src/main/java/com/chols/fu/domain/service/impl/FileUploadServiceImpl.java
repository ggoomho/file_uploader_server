package com.chols.fu.domain.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.chols.fu.aop.annotation.ServiceLog;
import com.chols.fu.domain.model.dto.FileDataDTO;
import com.chols.fu.domain.model.dto.FileInfoDTO;
import com.chols.fu.domain.model.entity.FileDataEntity;
import com.chols.fu.domain.model.entity.FileInfoEntity;
import com.chols.fu.domain.repository.FileDataRepository;
import com.chols.fu.domain.repository.FileInfoRepository;
import com.chols.fu.domain.service.FileUploadService;
import com.chols.fu.monitor.FileUploadProgress;
import com.chols.fu.monitor.ProgressMonitor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileUploadServiceImpl implements FileUploadService {

    private final FileDataRepository fileDataRepository;
    private final FileInfoRepository fileInfoRepository;
    private final ProgressMonitor progressMonitor;
    private final ModelMapper modelMapper;
    private static final int BATCH_SIZE = 1000;

    @Override
    @Async
    @Transactional
    @ServiceLog
    public void saveLines(FileInfoDTO fileInfo, List<FileDataDTO> lines) {
        int numOfLines = lines.size();

        // monitor
        FileUploadProgress progress = progressMonitor.addProgress(fileInfo.getId(), numOfLines);

        // batch insert
        // size: 1000
        int loop = numOfLines / BATCH_SIZE;
        for(int i=0; i<=loop; i++) {
            // write progress (processing)
            progress.process(BATCH_SIZE);

            List<FileDataEntity> rowList = new ArrayList<>();
            for(int j=0; j<BATCH_SIZE; j++) {
                int index = (i * BATCH_SIZE) + j;
                if(numOfLines == index) {
                    break;
                }
                FileDataEntity row = modelMapper.map(lines.get((i * BATCH_SIZE) + j), FileDataEntity.class);
                rowList.add(row);
            }
            fileDataRepository.saveAll(rowList);

            // write progress (done)
            progress.completeProccessing();
        }
        // write progress (complete)
        progress.setCompleted(true);
        log.info("saveLines finished");
    }    

    @Override
    @Transactional
    public FileInfoDTO saveFile(FileInfoDTO fileInfo) {
        FileInfoEntity inputEntity = FileInfoEntity.builder()
                                                        .fileName(fileInfo.getFileName())
                                                        .fileType(fileInfo.getFileType())
                                                        .size(fileInfo.getSize())
                                                        .build();
        FileInfoEntity savedEntity = fileInfoRepository.save(inputEntity);
        return FileInfoDTO.builder()
                            .id(savedEntity.getId())
                            .fileName(savedEntity.getFileName())
                            .fileType(savedEntity.getFileType())
                            .size(savedEntity.getSize())
                            .build();
            
    }

    @Override
    public FileUploadProgress getFileUploadProgress(Long fileId) {
        return progressMonitor.getProgress(fileId);
    }
}
