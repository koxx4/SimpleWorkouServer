package com.koxx4.simpleworkout.simpleworkoutserver.data;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "passwords")
public class JpaUserPassword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String password;

    @OneToOne(fetch = FetchType.EAGER, cascade = {})
    @JoinColumn(name = "user_id")
    private JpaUser jpaUser;

    protected JpaUserPassword() {
    }

    public JpaUserPassword(JpaUser targetUser, String passwordValue){
        this.jpaUser = targetUser;
        this.password = passwordValue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @JsonIgnore
    public JpaUser getUser() {
        return jpaUser;
    }

    public void setUser(JpaUser jpaUser) {
        this.jpaUser = jpaUser;
    }
}
