package com.chols.fu.domain.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import com.chols.fu.domain.model.entity.FileDataEntity;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties={"spring.config.location = classpath:application-test.yml"})
@DisplayName("[REPOSITORY] FileDataRepository Test")
public class FileDataRepositoryTest {
    @Autowired
    FileDataRepository fileDataRepository;
    
    @Autowired
    EntityManagerFactory emf;
    EntityManager em;

    @BeforeEach
    void setUp() {
        em = emf.createEntityManager();
    }

    @AfterEach
    void tearDown() {
        em.close();
    }

    @DisplayName("Save file dataset test")
    @Test
    void saveDataSetTest() {
        List<FileDataEntity> dataRowList = new ArrayList<>();
        for(int i=0; i<10; i++) {
            FileDataEntity temp = FileDataEntity.builder()
                                                    .id(Long.valueOf(i))
                                                    .firstName("fn_" + String.valueOf(i))
                                                    .lastName("ln_" + String.valueOf(i))
                                                    .email("em_" + String.valueOf(i))
                                                    .build();
            dataRowList.add(temp);
        }
        List<FileDataEntity> result = fileDataRepository.saveAll(dataRowList);
        em.clear();

        // assert
        assertNotNull(result);
        assertEquals(10, result.size());
    }
}
