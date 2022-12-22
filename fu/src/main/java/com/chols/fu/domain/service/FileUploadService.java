package com.chols.fu.domain.service;

import java.util.List;

import com.chols.fu.domain.model.dto.FileDataDTO;
import com.chols.fu.domain.model.dto.FileInfoDTO;
import com.chols.fu.monitor.FileUploadProgress;

public interface FileUploadService {
    public void saveLines(FileInfoDTO fileInfo, List<FileDataDTO> lines);
    public FileInfoDTO saveFile(FileInfoDTO fileInfo);
    public FileUploadProgress getFileUploadProgress(Long fileId);
}
