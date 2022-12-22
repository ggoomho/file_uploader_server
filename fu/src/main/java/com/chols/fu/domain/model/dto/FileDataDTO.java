package com.chols.fu.domain.model.dto;

import javax.validation.constraints.Email;

import com.opencsv.bean.CsvBindByName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileDataDTO {
    @NonNull
    @CsvBindByName
    private Integer id;

    @CsvBindByName
    private String firstName;

    @CsvBindByName
    private String lastName;
    
    @Email
    @CsvBindByName
    private String email;
}
