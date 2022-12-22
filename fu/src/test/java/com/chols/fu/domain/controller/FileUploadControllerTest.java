package com.chols.fu.domain.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.chols.fu.domain.model.dto.FileInfoDTO;
import com.chols.fu.domain.service.FileUploadService;
import com.chols.fu.error.CustomException;
import com.chols.fu.error.ErrorCode;
import com.chols.fu.monitor.FileUploadProgress;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.BDDMockito.*;

@WebMvcTest(FileUploadController.class)
@DisplayName("[CONTROLLER] FileUploadController Test")
public class FileUploadControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    FileUploadService fileUploadService;

   // FileUploadProgress progress;
    
    @DisplayName("[GET] Get file upload progress with valid file id --> return progress")
    @Test
    void getFileUploadProgressWithValidParamTest() throws Exception {
        FileUploadProgress progress = new FileUploadProgress(100);

        // given
        given(fileUploadService.getFileUploadProgress(eq(1L))).willReturn(progress);

        // when
        ResultActions action = mockMvc.perform(get("/file/1/upload/progress"));

        // then
        action
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.total", Matchers.is(100)));
    }

    @DisplayName("[GET] Get file upload progress with invalid file id --> 404 not found")
    @Test
    void getFileUploadProgressWithInvalidParamTest() throws Exception {
        // given
        given(fileUploadService.getFileUploadProgress(eq(1L))).willThrow(new CustomException(ErrorCode.PROGRESS_NOT_FOUND));

        // when
        ResultActions action = mockMvc.perform(get("/file/1/upload/progress"));

        // then
        action
            .andExpect(status().isNotFound());
    }

    @DisplayName("[POST] file upload --> return file info")
    @Test
    void uploadFileTest() throws Exception {
        
        FileInfoDTO fileInfoDTO = FileInfoDTO.builder()
                                                .id(1L)
                                                .fileName("test_file")
                                                .fileType("test_type")
                                                .size(100L)
                                                .build();
        MockMultipartFile file = new MockMultipartFile("file", "test.csv", MediaType.TEXT_PLAIN_VALUE, "csv data".getBytes());

        // given
        given(fileUploadService.saveFile(any(FileInfoDTO.class))).willReturn(fileInfoDTO);
        doNothing().when(fileUploadService).saveLines(any(FileInfoDTO.class), any());

        // when
        ResultActions action = mockMvc.perform(multipart("/file/upload")
                                                .file(file));

        // then
        action
            .andExpect(status().isOk());
    }
}
