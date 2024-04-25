package com.coolvetclinicpumb.vetclinicapp.dto;

import com.coolvetclinicpumb.vetclinicapp.validation.FieldMatch;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@FieldMatch(password = "password", repeatPassword = "repeatPassword")
public class UserRegistrationRequestDto {
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @Size(min = 2, max = 20)
    private String firstName;
    @NotBlank
    @Size(min = 2, max = 20)
    private String lastName;
    @NotBlank
    @Size(min = 8, max = 20)
    private String password;
    @NotBlank
    @Size(min = 8, max = 20)
    private String repeatPassword;

    public UserRegistrationRequestDto() {
    }

    public UserRegistrationRequestDto(
            String email,
            String firstName,
            String lastName,
            String password,
            String repeatPassword) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.repeatPassword = repeatPassword;
    }
}
