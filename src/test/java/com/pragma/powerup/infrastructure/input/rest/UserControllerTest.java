package com.pragma.powerup.infrastructure.input.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pragma.powerup.application.dto.UserRequestDto;
import com.pragma.powerup.application.dto.UserResponseDto;
import com.pragma.powerup.domain.model.UserModel;
import com.pragma.powerup.domain.usecase.UserServicePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServicePort userService;

    @Autowired
    private ObjectMapper objectMapper;

    private UserRequestDto userRequestDto;
    private UserModel createdUser;

    @BeforeEach
    void setUp() {
        userRequestDto = new UserRequestDto();
        userRequestDto.setName("Juan");
        userRequestDto.setLastname("Pérez");
        userRequestDto.setDocument(123456789);
        userRequestDto.setPhone("+1234567890");
        userRequestDto.setBirthdate(LocalDate.of(2000, 1, 1));
        userRequestDto.setEmail("juan@example.com");
        userRequestDto.setPassword("password123");
        userRequestDto.setRole("USER");

        createdUser = new UserModel(
                1L,
                userRequestDto.getName(),
                userRequestDto.getLastname(),
                userRequestDto.getDocument(),
                userRequestDto.getPhone(),
                userRequestDto.getBirthdate(),
                userRequestDto.getEmail(),
                userRequestDto.getPassword(),
                userRequestDto.getRole()
        );
    }

    @Test
    void createUser_ShouldReturnCreatedUser() throws Exception {
        // Arrange
        Mockito.when(userService.createUser(any(UserModel.class))).thenReturn(createdUser);

        // Act & Assert
        mockMvc.perform(post("/api/v1/users/createUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Juan"))
                .andExpect(jsonPath("$.lastname").value("Pérez"))
                .andExpect(jsonPath("$.document").value("123456789"))
                .andExpect(jsonPath("$.phone").value("+1234567890"))
                .andExpect(jsonPath("$.email").value("juan@example.com"))
                .andExpect(jsonPath("$.password").value("password123"))
                .andExpect(jsonPath("$.role").value("USER"));

        Mockito.verify(userService, Mockito.times(1)).createUser(any(UserModel.class));
    }
}