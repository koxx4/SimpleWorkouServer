package controllers;

import com.koxx4.simpleworkout.simpleworkoutserver.controllers.RegistrationController;
import com.koxx4.simpleworkout.simpleworkoutserver.repositories.JpaUserRepository;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.HamcrestCondition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.hamcrest.MockitoHamcrest;
import org.mockito.internal.hamcrest.HamcrestArgumentMatcher;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class RegistrationControllerTests {

    @Mock
    private JpaUserRepository userRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private MockMvc mockMvc;

    @BeforeEach
    public void setup(){
        RegistrationController registrationController = new RegistrationController(userRepository, passwordEncoder);
        this.mockMvc = MockMvcBuilders.standaloneSetup(registrationController).build();
    }

    @Test
    public void testRegistrationController_RegisteringDummyUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/register/user")
                .param("username", "test_user")
                .param("email", "simple@email.com")
                .param("password", "pa$$word"))
                .andExpect(status().isCreated());
        Mockito.verify(userRepository, Mockito.atLeastOnce()).save(Mockito.any());
    }

//    @Test
//    public void testRegistrationController_VerifySavedUserData() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.post("/register/user")
//                        .param("username", "test_user")
//                        .param("email", "simple@email.com")
//                        .param("password", "pa$$word"))
//                .andExpect(status().isCreated());
//        var registeredUser = userRepository.findByNickname("test_user");
//
//        Assertions.assertThat(registeredUser.getNickname())
//                .isEqualTo("test_user");
//
//        Assertions.assertThat(registeredUser.getEmail())
//                .isEqualTo("simple@email.com");
//
//        Assertions.assertThat(registeredUser.getJpaPassword().getPassword())
//                .isEqualTo(passwordEncoder.encode("pa$$word"));
//    }

}
