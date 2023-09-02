package com.github.sanjayrawat1.cms.security.domain;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Sanjay Singh Rawat
 */
@Getter
@Setter
@Entity
@Table(name = "role")
public class Privilege {

    @Id
    private String name;

    @ManyToMany(mappedBy = "privileges")
    private Set<Role> roles = new HashSet<>();
}
