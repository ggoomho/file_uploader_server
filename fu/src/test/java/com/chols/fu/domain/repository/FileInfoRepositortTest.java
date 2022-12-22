package com.chols.fu.domain.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import com.chols.fu.domain.model.entity.FileInfoEntity;

import static org.junit.jupiter.api.Assertions.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

@DataJpaTest
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties={"spring.config.location=classpath:application-test.yml"})
@DisplayName("[REPOSITORY] FileInfoRepository Test")
public class FileInfoRepositortTest {
    @Autowired
    FileInfoRepository fileInfoRepository;

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

    @DisplayName("Save FileInfo test")
    @Test
    void saveFileInfoTest() {
        // Entity
        FileInfoEntity entity = FileInfoEntity.builder()
                                                .fileName("test_file")
                                                .fileType("test_file_type")
                                                .size(100L)
                                                .build();
        FileInfoEntity result = fileInfoRepository.save(entity);
        em.clear();

        // assert
        assertNotNull(result);
        assertEquals("test_file", result.getFileName());
    }
}
