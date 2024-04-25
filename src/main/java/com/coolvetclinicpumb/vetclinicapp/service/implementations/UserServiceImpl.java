package com.coolvetclinicpumb.vetclinicapp.service.implementations;

import com.coolvetclinicpumb.vetclinicapp.dto.RoleRequestDto;
import com.coolvetclinicpumb.vetclinicapp.dto.UserRegistrationRequestDto;
import com.coolvetclinicpumb.vetclinicapp.dto.UserResponseDto;
import com.coolvetclinicpumb.vetclinicapp.exception.RegistrationException;
import com.coolvetclinicpumb.vetclinicapp.exception.RoleException;
import com.coolvetclinicpumb.vetclinicapp.mapper.RoleMapper;
import com.coolvetclinicpumb.vetclinicapp.mapper.UserMapper;
import com.coolvetclinicpumb.vetclinicapp.model.Role;
import com.coolvetclinicpumb.vetclinicapp.model.User;
import com.coolvetclinicpumb.vetclinicapp.repository.RoleRepository;
import com.coolvetclinicpumb.vetclinicapp.repository.UserRepository;
import com.coolvetclinicpumb.vetclinicapp.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import java.security.Principal;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final Long CUSTOMER_ROLE_ID = 2L;
    private static final String CHANGE_EMAIL_MESSAGE
            = "If you changed your email, please log in with new email";
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleMapper roleMapper;
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

    @Override
    public UserResponseDto updateUserRole(RoleRequestDto roleRequestDto, Long id) {
        Role roleForUpdate = roleMapper.toEntity(roleRequestDto);
        User userFromDbById = userRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("User by id: " + id
                        + " does not exist in DB"));
        Role roleFromDbByName
                = roleRepository.findByName(roleForUpdate.getName()).get();
        Set<Role> userRoles = userFromDbById.getRoles();
        if (userRoles.contains(roleFromDbByName)) {
            throw new RoleException("User by id: " + id
                    + " already has role " + roleRequestDto.getRoleName());
        }
        userFromDbById.getRoles().add(roleFromDbByName);
        User updatedUser = userRepository.save(userFromDbById);
        return userMapper.toUserResponse(updatedUser);
    }

    private User getUserFromPrincipal(Principal principal) {
        String userEmail = principal.getName();
        return userRepository.findByEmail(userEmail).get();
    }
}
