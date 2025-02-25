package com.git.Nog022.MeetHub;

import com.git.Nog022.MeetHub.controller.AuthorizationController;
import com.git.Nog022.MeetHub.dto.RegisterDTO;
import com.git.Nog022.MeetHub.entity.User;
import com.git.Nog022.MeetHub.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class AuthorizationControllerTest {
    @Mock
    private UserRepository usuarioRepository;

    @InjectMocks
    private AuthorizationController authorizationController;

    public AuthorizationControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegister() {
        RegisterDTO registerDTO = new RegisterDTO(
                "Rodrigo USER",
                "rodrigoUser@gmail.com",
                "74072424021",
                "123",
                "SulAmerica"
        );

        when(usuarioRepository.findByEmail(registerDTO.email())).thenReturn(Optional.empty());

        ResponseEntity<User> response = authorizationController.register(registerDTO);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        //assertNotNull(response.getBody());
       // assertEquals("Rodrigo USER", response.getBody().getName());

        verify(usuarioRepository, times(1)).save(any(User.class));

    }
}
