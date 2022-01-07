package com.koxx4.simpleworkout.simpleworkoutserver.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "nickname", unique = true)
    private String nickname;

    @OneToOne(mappedBy = "appUser", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private AppUserPassword password;

    @OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<UserRole> roles;

    @OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserWorkout> workouts;

    public AppUser() {
    }

    public AppUser(String email, String nickname) {
        this.email = email;
        this.nickname = nickname;
    }

    @JsonIgnore
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

    @JsonIgnore
    public AppUserPassword getPassword() {
        return password;
    }

    public void setPassword(AppUserPassword password) {
        this.password = password;
    }

    @JsonManagedReference
    public List<UserWorkout> getWorkouts() {
        return workouts;
    }

    public void setWorkouts(List<UserWorkout> workouts) {
        this.workouts = workouts;
    }

    public void addWorkout(UserWorkout workout){
        if(this.workouts == null)
            this.workouts = new ArrayList<>();

        workout.setUser(this);
        this.workouts.add(workout);
    }
}
