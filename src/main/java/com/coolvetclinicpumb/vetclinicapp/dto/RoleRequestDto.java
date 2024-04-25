package com.coolvetclinicpumb.vetclinicapp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleRequestDto {
    private String roleName;

    public RoleRequestDto() {
    }

    public RoleRequestDto(String roleName) {
        this.roleName = roleName;
    }
}
