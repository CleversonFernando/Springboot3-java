package com.educandoweb.course.services;

import com.educandoweb.course.entities.Product;
import com.educandoweb.course.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProductServiceTest {

    public static final long ID = 1L;
    public static final String NAME = "CD";
    public static final String DESCRIPTION = "CD de jogos";
    public static final double PRICE = 20.00;
    public static final String IMG_URL = "";
    public static final int INDEX = 0;
    @InjectMocks
    private ProductService productService;
    @Mock
    private ProductRepository productRepository;
    private Product product;
    private Optional<Product> optionalProduct;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startProduct();
    }

    @Test
    void whenFindAllThenReturnSuccess() {
        when(productRepository.findAll()).thenReturn(List.of(product));

        List<Product> response = productService.findAll();

        verify(productRepository).findAll();
        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(Product.class, response.get(INDEX).getClass());
        assertEquals(ID, response.get(INDEX).getId());
        assertEquals(NAME, response.get(INDEX).getName());
        assertEquals(DESCRIPTION, response.get(INDEX).getDescription());
        assertEquals(PRICE, response.get(INDEX).getPrice());
        assertEquals(IMG_URL, response.get(INDEX).getImgUrl());
    }

    @Test
    void whenFindByIdThenReturnProduct() {
        when(productRepository.findById(ID)).thenReturn(optionalProduct);

        Product response = productService.findById(ID);

        verify(productRepository).findById(ID);
        assertNotNull(response);
        assertEquals(Product.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getName());
        assertEquals(DESCRIPTION, response.getDescription());
        assertEquals(PRICE, response.getPrice());
        assertEquals(IMG_URL, response.getImgUrl());
    }
    private void startProduct(){
        product = new Product(ID, NAME, DESCRIPTION, PRICE, IMG_URL);
        optionalProduct = Optional.of(new Product(ID, NAME, DESCRIPTION, PRICE, IMG_URL));
    }
}