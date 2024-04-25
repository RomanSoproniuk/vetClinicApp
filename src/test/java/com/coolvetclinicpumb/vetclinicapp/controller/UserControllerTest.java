package com.coolvetclinicpumb.vetclinicapp.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.coolvetclinicpumb.vetclinicapp.dto.RoleRequestDto;
import com.coolvetclinicpumb.vetclinicapp.dto.UserResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.EqualsBuilder;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {
    protected static MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private RoleRequestDto roleRequestDto;
    private UserResponseDto userResponseDto;

    @BeforeAll
    static void beforeAll(
            @Autowired WebApplicationContext applicationContext
    ) {
        mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
    }

    @BeforeEach
    void setUp() {
        roleRequestDto = new RoleRequestDto("MANAGER");
        userResponseDto = new UserResponseDto(2L, "alice@gmail.com");
    }

    @Test
    @DisplayName("""
            Correct update user role for user
            """)
    @WithMockUser(username = "admin@gmail.com", roles = {"MANAGER"})
    @Sql(scripts = {"classpath:database/users/add-alice-user-to-db.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"classpath:database/users/delete-alice-from-db.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void updateUserRole_CorrectUpdateUserRole_Success() throws Exception {
        //given
        UserResponseDto expected = userResponseDto;
        Long userId = 1L;
        String jsonRequest = objectMapper.writeValueAsString(roleRequestDto);
        //when
        MvcResult result = mockMvc.perform(put("/users/{id}/role", userId)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        //then
        UserResponseDto actual
                = objectMapper.readValue(result.getResponse()
                .getContentAsString(), UserResponseDto.class);
        EqualsBuilder.reflectionEquals(expected, actual);
    }
}
