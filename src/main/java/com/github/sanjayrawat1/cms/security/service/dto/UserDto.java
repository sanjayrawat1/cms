package com.github.sanjayrawat1.cms.security.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Sanjay Singh Rawat
 */
@Getter
@Setter
public class UserDto implements Serializable {

    private String username;

    private String name;

    private String email;

    private String imageUrl;

    private boolean activated;

    private Set<String> roles = new HashSet<>();
}
