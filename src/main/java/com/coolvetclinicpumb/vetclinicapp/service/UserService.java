package com.coolvetclinicpumb.vetclinicapp.service;

import com.coolvetclinicpumb.vetclinicapp.dto.RoleRequestDto;
import com.coolvetclinicpumb.vetclinicapp.dto.UserRegistrationRequestDto;
import com.coolvetclinicpumb.vetclinicapp.dto.UserResponseDto;
import com.coolvetclinicpumb.vetclinicapp.exception.RegistrationException;

public interface UserService {
    UserResponseDto registerUser(UserRegistrationRequestDto userRegistrationRequestDto)
            throws RegistrationException;

    UserResponseDto updateUserRole(RoleRequestDto roleRequestDto, Long id);
}
