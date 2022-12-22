package com.chols.fu.domain.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.chols.fu.domain.model.dto.FileInfoDTO;
import com.chols.fu.domain.model.entity.FileInfoEntity;
import com.chols.fu.domain.repository.FileInfoRepository;
import com.chols.fu.domain.service.impl.FileUploadServiceImpl;
import com.chols.fu.error.CustomException;
import com.chols.fu.error.ErrorCode;
import com.chols.fu.monitor.FileUploadProgress;
import com.chols.fu.monitor.ProgressMonitor;

import static org.mockito.BDDMockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("[SERVICE] FileUploadService Test")
public class FileUploadServiceTest {
    
    @InjectMocks
    FileUploadServiceImpl fileUploadService;
    @Mock
    FileInfoRepository fileInfoRepository;
    @Mock
    ModelMapper modelMapper;
    @Mock
    ProgressMonitor progressMonitor;

    private FileInfoDTO fileInfoInputDTO;
    private FileInfoDTO fileInfoOutputDTO;
    private FileInfoEntity fileInfoEntity;

    @BeforeEach
    void setUp() {
        fileInfoInputDTO = new FileInfoDTO();
        fileInfoInputDTO.setFileName("test_file");
        fileInfoInputDTO.setFileType("test_type");

        fileInfoOutputDTO = new FileInfoDTO();
        fileInfoOutputDTO.setId(1L);
        fileInfoOutputDTO.setFileName("test_file");
        fileInfoOutputDTO.setFileType("test_type");
        fileInfoOutputDTO.setSize(100L);

        fileInfoEntity = new FileInfoEntity();
        fileInfoEntity.setId(1L);
        fileInfoEntity.setFileName("test_file");
        fileInfoEntity.setFileType("test_type");
        fileInfoEntity.setSize(100L);
    }

    @DisplayName("Save file Info --> return FileInfoDTO")
    @Test
    void saveFileTest() {
        // given
        given(fileInfoRepository.save(any(FileInfoEntity.class))).willReturn(fileInfoEntity);

        // when
        FileInfoDTO actual = fileUploadService.saveFile(fileInfoInputDTO);

        // then
        then(fileInfoRepository).should(times(1)).save(any(FileInfoEntity.class));
        assertNotNull(actual);
        assertEquals(1, actual.getId());
    }

    @DisplayName("Get file upload progress with valid fileId --> return progress")
    @Test
    void getProgressWithValidFileIdTest() {
        // setUp
        FileUploadProgress progress = new FileUploadProgress(100);

        // given
        given(progressMonitor.getProgress(eq(1L))).willReturn(progress);

        // when
        FileUploadProgress actual = fileUploadService.getFileUploadProgress(1L);

        // then
        then(progressMonitor).should(times(1)).getProgress(any());
        assertNotNull(actual);
        assertEquals(100, actual.getTotal());
    }

    @DisplayName("Get file upload progress with invalid fileId --> 404 not found")
    @Test
    void getProgressWithInvalidFiledIdTest() {
        // given
        given(progressMonitor.getProgress(eq(1L))).willThrow(new CustomException(ErrorCode.PROGRESS_NOT_FOUND));

        // then
        assertThrows(CustomException.class, () -> {
            // when
            FileUploadProgress actual = fileUploadService.getFileUploadProgress(1L);
        });
    }
}
