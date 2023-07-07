package com.educandoweb.course.services;

import com.educandoweb.course.entities.Category;
import com.educandoweb.course.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class CategoryServiceTest {

    public static final long ID = 1L;
    public static final String NAME = "Esportes";
    public static final int INDEX = 0;
    @InjectMocks
    private CategoryService categoryService;
    @Mock
   private CategoryRepository categoryRepository;
    private Category category;
    private Optional<Category> optionalCategory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startCategory();
    }

    @Test
    void whenFindAllThenReturnAListOfCategory() {
        when(categoryRepository.findAll()).thenReturn(List.of(category));

        List<Category> response = categoryService.findAll();

        verify(categoryRepository).findAll();
        assertNotNull(response);
        assertEquals(Category.class, response.get(INDEX).getClass());
        assertEquals(ID, response.get(INDEX).getId());
        assertEquals(NAME, response.get(INDEX).getName());
    }

    @Test
    void whenFindByIdThenReturnSuccess() {
        when(categoryRepository.findById(ID)).thenReturn(optionalCategory);

        Category response = categoryService.findById(ID);

        verify(categoryRepository).findById(ID);
        assertNotNull(response);
        assertEquals(Category.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getName());
    }

    private void startCategory() {
        category = new Category(ID, NAME);
        optionalCategory = Optional.of(new Category(ID, NAME));
    }
}