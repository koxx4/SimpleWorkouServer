package com.koxx4.simpleworkout.simpleworkoutserver.controllers;

import com.koxx4.simpleworkout.simpleworkoutserver.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class RegistrationControllerTests {

    @Mock
    private UserService userService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup(){
        RegistrationController registrationController = new RegistrationController(userService);
        this.mockMvc = MockMvcBuilders.standaloneSetup(registrationController).build();
    }

    @Test
    public void testRegistrationController_RegisteringDummyUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/register/user")
                .param("username", "test_user")
                .param("email", "simple@email.com")
                .param("password", "pa$$word"))
                .andExpect(status().isCreated());

        Mockito.verify(userService, Mockito.atLeastOnce()).
                saveUser(Mockito.eq("test_user"), Mockito.eq("simple@email.com"),
                        Mockito.eq("pa$$word"), Mockito.eq(new String[]{"REGULAR_USER"}));
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
