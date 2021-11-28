package com.koxx4.simpleworkout.simpleworkoutserver.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class JpaUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "nickname", unique = true)
    private String nickname;

    @OneToOne(mappedBy = "jpaUser", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private JpaUserPassword password;

    @OneToMany(mappedBy = "jpaUser", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<UserRole> roles;

    @OneToMany(mappedBy = "jpaUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserWorkout> workouts;

    public JpaUser() {
    }

    public JpaUser(String email, String nickname) {
        this.email = email;
        this.nickname = nickname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @JsonManagedReference
    public List<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(List<UserRole> roles) {
        this.roles = roles;
    }

    public void addRole(UserRole role){
        if(this.roles == null)
            this.roles = new ArrayList<>();

        role.setUser(this);
        this.roles.add(role);
    }

    @JsonIgnore
    public String[] getRolesArray(){
        return this.roles.stream().map(UserRole::getName).toArray(String[]::new);
    }

    public JpaUserPassword getJpaPassword() {
        return password;
    }

    public void setJpaPassword(JpaUserPassword password) {
        this.password = password;
    }

    public List<UserWorkout> getWorkouts() {
        return workouts;
    }

    public void setWorkouts(List<UserWorkout> workouts) {
        this.workouts = workouts;
    }
}
