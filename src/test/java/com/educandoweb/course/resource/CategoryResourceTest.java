package com.educandoweb.course.resource;

import com.educandoweb.course.entities.Category;
import com.educandoweb.course.services.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class CategoryResourceTest {

    public static final String NAME = "Esportes";
    public static final long ID = 1L;
    public static final int INDEX = 0;
    @InjectMocks
    private CategoryResource categoryResource;
    @Mock
    private CategoryService categoryService;
    private Category category;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startCategory();
    }

    @Test
    void whenFindAllThenReturnAListOfCategory() {
        when(categoryService.findAll()).thenReturn(List.of(category));

        ResponseEntity<List<Category>> response = categoryResource.findAll();

        verify(categoryService).findAll();
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Category.class, response.getBody().get(INDEX).getClass());

        assertEquals(ID, response.getBody().get(INDEX).getId());
        assertEquals(NAME, response.getBody().get(INDEX).getName());
    }

    @Test
    void whenFindByIdThenReturnCategory() {
        when(categoryService.findById(ID)).thenReturn(category);

        ResponseEntity<Category> response = categoryResource.findById(ID);

        verify(categoryService).findById(ID);
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Category.class, response.getBody().getClass());

        assertEquals(ID, response.getBody().getId());
        assertEquals(NAME, response.getBody().getName());
    }

    private void startCategory() {
        category = new Category(ID, NAME);
    }
}