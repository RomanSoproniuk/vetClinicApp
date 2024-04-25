package com.coolvetclinicpumb.vetclinicapp.service;

import com.coolvetclinicpumb.vetclinicapp.dto.UserRegistrationRequestDto;
import com.coolvetclinicpumb.vetclinicapp.dto.UserResponseDto;
import com.coolvetclinicpumb.vetclinicapp.exception.RegistrationException;
import com.coolvetclinicpumb.vetclinicapp.mapper.impl.UserMapperImpl;
import com.coolvetclinicpumb.vetclinicapp.model.Role;
import com.coolvetclinicpumb.vetclinicapp.model.User;
import com.coolvetclinicpumb.vetclinicapp.repository.RoleRepository;
import com.coolvetclinicpumb.vetclinicapp.repository.UserRepository;
import com.coolvetclinicpumb.vetclinicapp.service.implementations.UserServiceImpl;
import java.util.Optional;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    private static Long userId;
    private static Role userRole;
    private static String encodedPassword;
    private static String rawPassword;
    private static UserRegistrationRequestDto userRegistrationRequestDto;
    private static UserResponseDto userResponseDto;
    private static User user;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapperImpl userMapper;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;
    @InjectMocks
    private UserServiceImpl userService;

    @BeforeAll
    static void setUp() {
        rawPassword = "12345678";
        encodedPassword = "$2a$10$aSRC5P15RuyGCkIUQSvV7espH9sESixV/jDpsQomHruZlzNRck0Fy";
        userId = 2L;
        userRegistrationRequestDto = new UserRegistrationRequestDto("bob@gmail.com",
                "Bob", "Alison", "12345678", "12345678");
        userRole = new Role(2L, Role.RoleName.CUSTOMER);
        user = new User(2L, "bob@gmail.com", "Bob", "Alison",
                "$2a$10$aSRC5P15RuyGCkIUQSvV7espH9sESixV/jDpsQomHruZlzNRck0Fy", false);
        user.getRoles().add(userRole);
        userResponseDto = new UserResponseDto(2L, "bob@gmail.com");
    }

    @Test
    @DisplayName("""
            Validate how work register user method            
            """)
    public void registerUser_ReturnRegisteredUser_Success()
            throws RegistrationException {
        //given
        UserResponseDto expectedResponseDto = userResponseDto;
        Mockito.when(roleRepository.findById(userId)).thenReturn(Optional.of(userRole));
        Mockito.when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);
        Mockito.when(userMapper.toUserResponse(user)).thenReturn(expectedResponseDto);
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);
        //when
        UserResponseDto actualResponseDto
                = userService.registerUser(userRegistrationRequestDto);
        //then
        EqualsBuilder.reflectionEquals(expectedResponseDto, actualResponseDto);
    }
}
