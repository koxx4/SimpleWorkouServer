package com.koxx4.simpleworkout.simpleworkoutserver.data;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class UserRole implements GrantedAuthority {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private JpaUser jpaUser;

    public UserRole() {
    }

    public UserRole(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return this.getName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonBackReference
    public JpaUser getUser() {
        return jpaUser;
    }

    public void setUser(JpaUser jpaUser) {
        this.jpaUser = jpaUser;
    }
}
