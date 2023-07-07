package com.educandoweb.course;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CourseApplicationTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void main() {
        CourseApplication.main(new String[]{});

    }
}