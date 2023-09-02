package com.github.sanjayrawat1.cms.security.service.dto;

import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Sanjay Singh Rawat
 */
@Getter
@Setter
public class ManagedUserDto extends UserDto {

    @Size(min = 4, max = 50)
    private String password;

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" + super.toString() + ")";
    }
}
