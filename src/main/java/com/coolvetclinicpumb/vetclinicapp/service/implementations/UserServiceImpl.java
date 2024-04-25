package com.coolvetclinicpumb.vetclinicapp.service.implementations;

import com.coolvetclinicpumb.vetclinicapp.dto.UserRegistrationRequestDto;
import com.coolvetclinicpumb.vetclinicapp.dto.UserResponseDto;
import com.coolvetclinicpumb.vetclinicapp.exception.RegistrationException;
import com.coolvetclinicpumb.vetclinicapp.mapper.UserMapper;
import com.coolvetclinicpumb.vetclinicapp.model.Role;
import com.coolvetclinicpumb.vetclinicapp.model.User;
import com.coolvetclinicpumb.vetclinicapp.repository.RoleRepository;
import com.coolvetclinicpumb.vetclinicapp.repository.UserRepository;
import com.coolvetclinicpumb.vetclinicapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final Long CUSTOMER_ROLE_ID = 2L;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public UserResponseDto registerUser(UserRegistrationRequestDto userRegistrationRequestDto)
            throws RegistrationException {
        if (userRepository.findByEmail(userRegistrationRequestDto.getEmail()).isPresent()) {
            throw new RegistrationException("You can not register by email: "
                    + userRegistrationRequestDto.getEmail()
                    + " since user with same email is exist");
        }
        User user = new User();
        user.setEmail(userRegistrationRequestDto.getEmail());
        user.setFirstName(userRegistrationRequestDto.getFirstName());
        user.setLastName(userRegistrationRequestDto.getLastName());
        user.setPassword(passwordEncoder.encode(userRegistrationRequestDto.getPassword()));
        Role userRole = roleRepository.findById(CUSTOMER_ROLE_ID).get();
        user.getRoles().add(userRole);
        User savedUser = userRepository.save(user);
        return userMapper.toUserResponse(savedUser);
    }
}
