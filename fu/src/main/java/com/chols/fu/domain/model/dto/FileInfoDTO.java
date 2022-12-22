package com.chols.fu.domain.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileInfoDTO {
    private Long id;
    private String fileName;
    private String fileType;
    private Long size;
}
