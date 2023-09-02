package com.github.sanjayrawat1.cms.security.domain;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
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
public class Role {

    @Id
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "role_privileges",
        joinColumns = { @JoinColumn(name = "role_name", referencedColumnName = "name") },
        inverseJoinColumns = { @JoinColumn(name = "privilege_name", referencedColumnName = "name") }
    )
    private Set<Privilege> privileges = new HashSet<>();
}
