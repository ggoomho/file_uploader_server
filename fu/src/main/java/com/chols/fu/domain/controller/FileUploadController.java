package com.chols.fu.domain.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.chols.fu.domain.model.dto.FileDataDTO;
import com.chols.fu.domain.model.dto.FileInfoDTO;
import com.chols.fu.domain.service.FileUploadService;
import com.chols.fu.monitor.FileUploadProgress;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = "*")
public class FileUploadController {
    
    private final FileUploadService fileUploadService;

    @PostMapping("/upload")
    public ResponseEntity<FileInfoDTO> uploadFile(@RequestParam("file") MultipartFile file) {

        // save file info
        FileInfoDTO fileInfo = new FileInfoDTO();
        fileInfo.setFileName(file.getOriginalFilename());
        fileInfo.setFileType(file.getContentType());
        fileInfo.setSize(file.getSize());
        FileInfoDTO savedFileInfo = fileUploadService.saveFile(fileInfo);

        // save file data
        try {
            Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream())); 
            CsvToBean<FileDataDTO> csvToBean = new CsvToBeanBuilder(reader).withType(FileDataDTO.class).withIgnoreEmptyLine(true).build();
            List<FileDataDTO> lines = csvToBean.parse();
            fileUploadService.saveLines(savedFileInfo, lines);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.OK).body(savedFileInfo);
    }

    @GetMapping("/{id}/upload/progress")
    public ResponseEntity<FileUploadProgress> checkProgress(@PathVariable("id") Long fileId) {
        return ResponseEntity.status(HttpStatus.OK).body(fileUploadService.getFileUploadProgress(fileId));
    }

}
