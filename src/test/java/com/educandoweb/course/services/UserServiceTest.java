package com.educandoweb.course.services;

import com.educandoweb.course.entities.User;
import com.educandoweb.course.repositories.UserRepository;
import com.educandoweb.course.services.exceptions.ResourceNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
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
    private static final Long ID_TO_UPDATE = 2L;
    private static final String NAME_TO_UPDATE = "Fernando";
    private static final String EMAIL_TO_UPDATE = "fernando@gmail.com";
    private static final String PHONE_TO_UPDATE = "88888888";
    private static final String PASSWORD_TO_UPDATE = "5432";

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
        User entity = new User(NAME_TO_UPDATE, EMAIL_TO_UPDATE, PHONE_TO_UPDATE);

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

//    @Test
//    void whenUpdateThenReturnResourceNotFoundException() {
//        when(userRepository.getReferenceById(anyLong())).thenThrow(new ResourceNotFoundException(ID));
//
//        try {
//            userService.update(ID, user);
//        } catch (Exception e) {
//            assertEquals(ResourceNotFoundException.class, e.getClass());
//            assertEquals(OBJETO_NAO_ENCONTRADO + ID, e.getMessage());
//        }
//    }

    @Test
    void whenDeleteWithSuccess() {
        mock(UserRepository.class, CALLS_REAL_METHODS);
        doAnswer(Answers.CALLS_REAL_METHODS).when(userRepository).deleteById(anyLong());

        assertThat(userService.delete(ID)).isEqualTo("Success");
    }

    private void startUser() {
        user = new User(ID, NAME, EMAIL, PHONE, PASSWORD);
        optionalUser = Optional.of(new User(ID, NAME, EMAIL, PHONE, PASSWORD));
    }
}