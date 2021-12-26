package com.koxx4.simpleworkout.simpleworkoutserver.security;

import com.koxx4.simpleworkout.simpleworkoutserver.data.JpaUser;
import com.koxx4.simpleworkout.simpleworkoutserver.data.JpaUserPassword;
import com.koxx4.simpleworkout.simpleworkoutserver.repositories.JpaPasswordRepository;
import com.koxx4.simpleworkout.simpleworkoutserver.repositories.JpaUserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.UserDetailsManager;

import java.util.List;


class JpaUserDetailsManagerTests {
    ArgumentCaptor<JpaUser> userArgumentCaptor =
            ArgumentCaptor.forClass(JpaUser.class);

    ArgumentCaptor<JpaUserPassword> passwordArgumentCaptor =
            ArgumentCaptor.forClass(JpaUserPassword.class);

    private final JpaUserRepository userRepositoryMock =
            Mockito.mock(JpaUserRepository.class);

    private final JpaPasswordRepository passwordRepositoryMock =
            Mockito.mock(JpaPasswordRepository.class);

    private final UserDetailsManager userDetailsManager =
            new JpaUserDetailsManager(userRepositoryMock, passwordRepositoryMock);

    @Test
    @DisplayName("Test creating user with JpaUserDetailsManager")
    public void createUserWithJpaUserDetailsManager(){
        String providedNickname = "john123";
        String providedPassword = "pa$$word";
        String[] attachedRoles = {"USER","PREMIUM"};

        userDetailsManager.createUser(User.builder()
                .username(providedNickname)
                .password(providedPassword)
                .roles(attachedRoles)
                .build());

        Mockito.verify(userRepositoryMock).save(userArgumentCaptor.capture());

        Assertions.assertThat(userArgumentCaptor.getValue().getNickname())
                .isEqualTo(providedNickname);

        Assertions.assertThat(userArgumentCaptor.getValue().getRolesArray())
                .containsAll(List.of("ROLE_USER", "ROLE_PREMIUM"));

        Assertions.assertThat(userArgumentCaptor.getValue().getJpaPassword().getPassword())
                .isEqualTo(providedPassword);
    }
}
