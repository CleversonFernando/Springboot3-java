package com.educandoweb.course.services;

import com.educandoweb.course.entities.User;
import com.educandoweb.course.repositories.UserRepository;
import com.educandoweb.course.services.exceptions.ResourceNotFoundException;
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
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceTest {

    public static final Long ID = 1L;
    public static final String NAME = "Paulo";
    public static final String EMAIL = "paulo@email.com";
    public static final String PHONE = "999999999";
    public static final String PASSWORD = "123";
    public static final int INDEX = 0;
    public static final String OBJETO_NAO_ENCONTRADO = "Resource not found id. ";

    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    private User user;
    private Optional<User> optionalUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startUser();
    }

    @Test
    void whenFindAllThenReturnAListOfUsers() {
        when(userRepository.findAll()).thenReturn(List.of(user));

        List<User> response = userService.findAll();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(User.class, response.get(INDEX).getClass());
        assertEquals(ID, response.get(INDEX).getId());
        assertEquals(NAME, response.get(INDEX).getName());
        assertEquals(EMAIL, response.get(INDEX).getEmail());
        assertEquals(PHONE, response.get(INDEX).getPhone());
        assertEquals(PASSWORD, response.get(INDEX).getPassword());
    }

    @Test
    void whenFindByIdThenReturnUser() {
        when(userRepository.findById(ID)).thenReturn(optionalUser);

        User response = userService.findById(ID);

        assertNotNull(response);
        assertEquals(User.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getName());
        assertEquals(EMAIL, response.getEmail());
        assertEquals(PHONE, response.getPhone());
        assertEquals(PASSWORD, response.getPassword());
    }

    @Test
    void whenFindByIdThenReturnObjectNotFoundException() {
        when(userRepository.findById(anyLong())).thenThrow(new ResourceNotFoundException(ID));

        try {
            userService.findById(ID);
        } catch (Exception e) {
            assertEquals(OBJETO_NAO_ENCONTRADO + ID, e.getMessage());
        }
    }

    @Test
    void whenInsertThenReturnSuccess() {
        when(userRepository.save(any())).thenReturn(user);

        User response = userService.insert(user);

        assertNotNull(response);
        assertEquals(User.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getName());
        assertEquals(EMAIL, response.getEmail());
        assertEquals(PHONE, response.getPhone());
        assertEquals(PASSWORD, response.getPassword());
    }

    @Test
    void whenUpdateThenReturnSuccess() {
        when(userRepository.getReferenceById(ID)).thenReturn(user);
        when(userRepository.save(any())).thenReturn(user);

        User response = userService.update(ID, user);

        assertNotNull(response);
        assertEquals(User.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getName());
        assertEquals(EMAIL, response.getEmail());
        assertEquals(PHONE, response.getPhone());
        assertEquals(PASSWORD, response.getPassword());
    }

    @Test
    void whenUpdateThenReturnResourceNotFoundException() {
        when(userRepository.getReferenceById(anyLong())).thenThrow(new ResourceNotFoundException(ID));

        try {
            userService.update(ID, user);
        } catch (Exception e) {
            assertEquals(ResourceNotFoundException.class, e.getClass());
            assertEquals(OBJETO_NAO_ENCONTRADO + ID, e.getMessage());
        }
    }

    @Test
    void whenDeleteWithSuccess() {
        when(userRepository.findById(anyLong())).thenReturn(optionalUser);
        doNothing().when(userRepository).deleteById(anyLong());
        userService.delete(ID);
        verify(userRepository, times(1)).deleteById(anyLong());
    }

    private void startUser() {
        user = new User(ID, NAME, EMAIL, PHONE, PASSWORD);
        optionalUser = Optional.of(new User(ID, NAME, EMAIL, PHONE, PASSWORD));
    }
}