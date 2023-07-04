package com.educandoweb.course.resource;

import com.educandoweb.course.entities.User;
import com.educandoweb.course.services.UserService;
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
import static org.mockito.Mockito.*;

@SpringBootTest
class UserResourceTest {

    public static final Long ID = 1L;
    public static final String NAME = "Paulo";
    public static final String EMAIL = "paulo@email.com";
    public static final String PHONE = "999999999";
    public static final String PASSWORD = "123";
    public static final int INDEX = 0;
    private User user = new User();
    @InjectMocks
    private UserResource userResource;
    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        starUser();
    }

    @Test
    void whenFindAllThenReturnListOfUsers() {
        when(userService.findAll()).thenReturn(List.of(user));

        ResponseEntity<List<User>> response = userResource.findAll();

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(User.class, response.getBody().get(INDEX).getClass());

        assertEquals(ID, response.getBody().get(INDEX).getId());
        assertEquals(NAME, response.getBody().get(INDEX).getName());
        assertEquals(EMAIL, response.getBody().get(INDEX).getEmail());
        assertEquals(PHONE, response.getBody().get(INDEX).getPhone());
        assertEquals(PASSWORD, response.getBody().get(INDEX).getPassword());
    }

    @Test
    void whenFindByIdThenReturnSuccess() {
        when(userService.findById(anyLong())).thenReturn(user);

        ResponseEntity<User> response = userResource.findById(ID);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(User.class, response.getBody().getClass());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals(ID, response.getBody().getId());
        assertEquals(NAME, response.getBody().getName());
        assertEquals(EMAIL, response.getBody().getEmail());
        assertEquals(PHONE, response.getBody().getPhone());
        assertEquals(PASSWORD, response.getBody().getPassword());
    }

    @Test
    void whenInsertThenReturnCreated() {
        when(userService.insert(any())).thenReturn(user);

        ResponseEntity<User> response = userResource.insert(user);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getHeaders().get("Location"));
    }

    @Test
    void whenDeleteThenReturnSuccess() {
        doNothing().when(userService).delete(anyLong());

        ResponseEntity<Void> response = userResource.delete(ID);

        assertNotNull(response);
        assertEquals(ResponseEntity.class, response.getClass());
        verify(userService, times(1)).delete(ID);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void whenUpdateThenReturnSuccess() {
        when(userService.update(any(), any())).thenReturn(user);

        ResponseEntity<User> response = userResource.update(ID, user);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(User.class, response.getBody().getClass());

        assertEquals(ID, response.getBody().getId());
        assertEquals(NAME, response.getBody().getName());
        assertEquals(EMAIL, response.getBody().getEmail());
        assertEquals(PHONE, response.getBody().getPhone());
        assertEquals(PASSWORD, response.getBody().getPassword());
    }

    private void starUser() {
        user = new User(ID, NAME, EMAIL, PHONE, PASSWORD);
    }
}