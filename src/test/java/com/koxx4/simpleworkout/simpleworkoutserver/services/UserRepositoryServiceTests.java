package com.koxx4.simpleworkout.simpleworkoutserver.services;

import com.koxx4.simpleworkout.simpleworkoutserver.data.AppUser;
import com.koxx4.simpleworkout.simpleworkoutserver.repositories.AppUserPasswordRepository;
import com.koxx4.simpleworkout.simpleworkoutserver.repositories.AppUserRepository;
import com.koxx4.simpleworkout.simpleworkoutserver.repositories.AppUserWorkoutRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class UserRepositoryServiceTests {
    ArgumentCaptor<AppUser> userArgumentCaptor = ArgumentCaptor.forClass(AppUser.class);

    private final AppUserRepository userRepositoryMock = mock(AppUserRepository.class);
    private final AppUserPasswordRepository passwordRepositoryMock = mock(AppUserPasswordRepository.class);
    private final AppUserWorkoutRepository workoutRepositoryMock = mock(AppUserWorkoutRepository.class);
    private final PasswordEncoder passwordEncoder = NoOpPasswordEncoder.getInstance();
    private final UserRepositoryService userRepositoryService = new UserRepositoryService(
            userRepositoryMock, workoutRepositoryMock, passwordRepositoryMock, passwordEncoder);

    @Test
    @DisplayName("Test creating user with JpaUserDetailsManager")
    void createUserWithJpaUserDetailsManager() throws SQLException {

        String providedNickname = "john123";
        String providedEmail = "email@example.com";
        CharSequence providedPassword = "pa$$word";
        String[] attachedRoles = {"USER","PREMIUM"};

        userRepositoryService.saveUser(providedNickname, providedEmail, providedPassword, attachedRoles);

        verify(userRepositoryMock).save(userArgumentCaptor.capture());

        assertThat(userArgumentCaptor.getValue().getNickname())
                .isEqualTo(providedNickname);

        assertThat(userArgumentCaptor.getValue().getRolesArray())
                .containsAll(List.of("ROLE_USER", "ROLE_PREMIUM"));

        assertThat(userArgumentCaptor.getValue().getPassword().getPassword())
                .isEqualTo(providedPassword);
    }
}
