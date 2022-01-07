package services;

import com.koxx4.simpleworkout.simpleworkoutserver.data.AppUser;
import com.koxx4.simpleworkout.simpleworkoutserver.data.AppUserPassword;
import com.koxx4.simpleworkout.simpleworkoutserver.repositories.AppUserPasswordRepository;
import com.koxx4.simpleworkout.simpleworkoutserver.repositories.AppUserRepository;
import com.koxx4.simpleworkout.simpleworkoutserver.repositories.AppUserWorkoutRepository;
import com.koxx4.simpleworkout.simpleworkoutserver.security.AppUserDetailsService;
import com.koxx4.simpleworkout.simpleworkoutserver.services.UserRepositoryService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;

import java.sql.SQLException;
import java.util.List;

public class UserRepositoryServiceTests {
    ArgumentCaptor<AppUser> userArgumentCaptor =
            ArgumentCaptor.forClass(AppUser.class);

    ArgumentCaptor<AppUserPassword> passwordArgumentCaptor =
            ArgumentCaptor.forClass(AppUserPassword.class);

    private final AppUserRepository userRepositoryMock =
            Mockito.mock(AppUserRepository.class);

    private final AppUserPasswordRepository passwordRepositoryMock =
            Mockito.mock(AppUserPasswordRepository.class);

    private final AppUserWorkoutRepository workoutRepositoryMock =
            Mockito.mock(AppUserWorkoutRepository.class);

    private final PasswordEncoder passwordEncoder = NoOpPasswordEncoder.getInstance();

    private final UserRepositoryService userRepositoryService =
            new UserRepositoryService(userRepositoryMock, workoutRepositoryMock,
                    passwordRepositoryMock, passwordEncoder);

    @Test
    @DisplayName("Test creating user with JpaUserDetailsManager")
    public void createUserWithJpaUserDetailsManager() throws SQLException {
        String providedNickname = "john123";
        String providedEmail = "email@example.com";
        CharSequence providedPassword = "pa$$word";
        String[] attachedRoles = {"USER","PREMIUM"};

        userRepositoryService.saveUser(providedNickname, providedEmail, providedPassword, attachedRoles);

        Mockito.verify(userRepositoryMock).save(userArgumentCaptor.capture());

        Assertions.assertThat(userArgumentCaptor.getValue().getNickname())
                .isEqualTo(providedNickname);

        Assertions.assertThat(userArgumentCaptor.getValue().getRolesArray())
                .containsAll(List.of("ROLE_USER", "ROLE_PREMIUM"));

        Assertions.assertThat(userArgumentCaptor.getValue().getPassword().getPassword())
                .isEqualTo(providedPassword);
    }
}
