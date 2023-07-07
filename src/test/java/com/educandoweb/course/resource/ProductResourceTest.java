package com.educandoweb.course.resource;

import com.educandoweb.course.entities.Product;
import com.educandoweb.course.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProductResourceTest {

    public static final long ID = 1L;
    public static final String NAME = "CD";
    public static final String DESCRIPTION = "CD de jogos";
    public static final double PRICE = 20.00;
    public static final String IMG_URL = "";
    public static final int INDEX = 0;

    @InjectMocks
    ProductResource productResource;
    @Mock
    ProductService productService;
    private Product product;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startProduct();
    }

    @Test
    void whenFindAllThenReturnAListOfProducts() {
        when(productService.findAll()).thenReturn(List.of(product));

        ResponseEntity<List<Product>> response = productResource.findAll();

        verify(productService).findAll();
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(Product.class, response.getBody().get(INDEX).getClass());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals(ID, response.getBody().get(INDEX).getId());
        assertEquals(NAME, response.getBody().get(INDEX).getName());
        assertEquals(DESCRIPTION, response.getBody().get(INDEX).getDescription());
        assertEquals(PRICE, response.getBody().get(INDEX).getPrice());
        assertEquals(IMG_URL, response.getBody().get(INDEX).getImgUrl());
    }

    @Test
    void whenFindByIdThenReturnSuccess() {
        when(productService.findById(ID)).thenReturn(product);

        ResponseEntity<Product> response = productResource.findById(ID);

        verify(productService).findById(ID);
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(Product.class, response.getBody().getClass());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals(ID, response.getBody().getId());
        assertEquals(NAME, response.getBody().getName());
        assertEquals(DESCRIPTION, response.getBody().getDescription());
        assertEquals(PRICE, response.getBody().getPrice());
        assertEquals(IMG_URL, response.getBody().getImgUrl());
    }

    private void startProduct() {
        product = new Product(ID, NAME, DESCRIPTION, PRICE, IMG_URL);
    }
}