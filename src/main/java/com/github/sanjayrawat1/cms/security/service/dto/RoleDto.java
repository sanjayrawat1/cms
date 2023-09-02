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
public class RoleDto implements Serializable {

    private String name;

    private Set<String> privileges = new HashSet<>();
}
