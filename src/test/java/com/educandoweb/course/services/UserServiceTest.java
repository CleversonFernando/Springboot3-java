package com.educandoweb.course.services;

import com.educandoweb.course.entities.User;
import com.educandoweb.course.repositories.UserRepository;
import com.educandoweb.course.services.exceptions.DatabaseException;
import com.educandoweb.course.services.exceptions.ResourceNotFoundException;
import org.hibernate.action.internal.EntityActionVetoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.persistence.EntityNotFoundException;
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
    private static final String NAME_TO_UPDATE = "Fernando";
    private static final String EMAIL_TO_UPDATE = "fernando@gmail.com";
    private static final String PHONE_TO_UPDATE = "88888888";
    private static final Long ID_NOT_FOUND = 0L;

    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    private User user;
    private User entity;
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
        when(userRepository.getReferenceById(user.getId())).thenReturn((user));
        when(userRepository.save(any())).thenReturn(user);

        User response = userService.update(ID, entity);

        assertNotNull(response);
        assertEquals(User.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NAME_TO_UPDATE, response.getName());
        assertEquals(EMAIL_TO_UPDATE, response.getEmail());
        assertEquals(PHONE_TO_UPDATE, response.getPhone());
        assertEquals(PASSWORD, response.getPassword());
    }

    @Test
    void whenUpdateThenReturnResourceNotFoundException() {
        doThrow(EntityNotFoundException.class).when(userRepository).getReferenceById(ID_NOT_FOUND);

        try {
            userService.update(ID_NOT_FOUND, user);
        } catch (Exception e) {
            assertEquals(ResourceNotFoundException.class, e.getClass());
            assertEquals(OBJETO_NAO_ENCONTRADO + ID_NOT_FOUND, e.getMessage());
            verify(userRepository).getReferenceById(ID_NOT_FOUND);
        }
    }

    @Test
    void whenDeleteWithSuccess() {
        userService.delete(ID);
        verify(userRepository).deleteById(ID);
    }

    @Test
    void whenDeleteThenThrowResourceNotFoundException() {
        doThrow(EmptyResultDataAccessException.class).when(userRepository).deleteById(ID_NOT_FOUND);

        try {
            userService.delete(ID_NOT_FOUND);
        } catch (Exception e) {
            assertEquals(ResourceNotFoundException.class, e.getClass());
            assertEquals(OBJETO_NAO_ENCONTRADO + ID_NOT_FOUND, e.getMessage());
        }
    }

    @Test
    void whenDeleteThenReturnDatabaseException() {
        doThrow(DataIntegrityViolationException.class).when(userRepository).deleteById(ID);
        try {
            userService.delete(ID);
        } catch (Exception e) {
            assertEquals(DatabaseException.class, e.getClass());
        }
    }

    private void startUser() {
        user = new User(ID, NAME, EMAIL, PHONE, PASSWORD);
        entity = new User(NAME_TO_UPDATE, EMAIL_TO_UPDATE, PHONE_TO_UPDATE);
        optionalUser = Optional.of(new User(ID, NAME, EMAIL, PHONE, PASSWORD));
    }
}