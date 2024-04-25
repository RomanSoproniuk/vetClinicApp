package com.coolvetclinicpumb.vetclinicapp.controller;

import com.coolvetclinicpumb.vetclinicapp.dto.RoleRequestDto;
import com.coolvetclinicpumb.vetclinicapp.dto.UserResponseDto;
import com.coolvetclinicpumb.vetclinicapp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Tag(name = "User API", description = """
With the help of this controller, you can perform certain actions 
with the user, you can read more about it below
        """)
public class UserController {
    private final UserService userService;

    @Operation(summary = "Update users roles", description = """ 
        When using this API, you can change the access level for this user, 
        it is worth noting that only users with the MANAGER access level 
        can use this endpoint
            """)
    @PutMapping("/{id}/role")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public UserResponseDto updateUserRole(@RequestBody RoleRequestDto roleRequestDto,
                                          @PathVariable Long id) {
        return userService.updateUserRole(roleRequestDto, id);
    }
}
