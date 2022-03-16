package com.example.javatest.test;

import com.example.javatest.domain.Study;
import com.example.javatest.study.StudyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class StudyRepositoryTest {

    @Autowired
    StudyRepository repository;

    @Test
    void save() {
        repository.deleteAll();
        Study study = new Study(10, "Java");
        repository.save(study);
        List<Study> all = repository.findAll();
        assertEquals(1, all.size());
    }

}