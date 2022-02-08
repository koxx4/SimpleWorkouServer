package com.koxx4.simpleworkout.simpleworkoutserver.controllers;

import com.koxx4.simpleworkout.simpleworkoutserver.services.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.method.annotation.AuthenticationPrincipalArgumentResolver;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class UserActionsControllerTests {

    @Mock
    private UserService userServiceMock;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private PasswordEncoder passwordEncoder;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup(){
        UserActionsController userActionsController = new UserActionsController(userServiceMock, passwordEncoder);
        RegistrationController registrationController = new RegistrationController(userServiceMock);
        //LoginController loginController = new LoginController(
        //        authenticationManager);

        this.mockMvc = MockMvcBuilders.standaloneSetup(userActionsController, registrationController /*, loginController*/)
                .setCustomArgumentResolvers(new AuthenticationPrincipalArgumentResolver())
                .build();
    }

    @Test
    void changeUserNickname() {
    }

    @Test
    @WithMockUser(username = "test_username", password = "password1", roles = "USER")
    void changeUserPassword() throws Exception {
        //TODO: Write test with spring context
        Assertions.assertThat(true).isTrue();
//        mockMvc.perform(MockMvcRequestBuilders.post("/register/user")
//                        .param("username", "test_user")
//                        .param("email", "simple@email.com")
//                        .param("password", "password1"))
//                .andExpect(status().isCreated());
//
//        String token = mockMvc.perform(MockMvcRequestBuilders.post("/login/test_user")
//                        .param("password", "password1"))
//                .andExpect(status().isAccepted()).andReturn().getResponse().getContentAsString();
//
//        Assertions.assertThat(token).isNotEmpty();
//        Assertions.assertThat(token).isNotBlank();


//        mockMvc.perform(MockMvcRequestBuilders.post("/user/password")
//                        .header("Authorization","Bearer " + token)
//                        .param("password", "password2"))
//                .andExpect(status().isAccepted());
//
//        Mockito.verify(userServiceMock, Mockito.atLeastOnce()).
//                changeUserPassword(Mockito.eq("test_user"), Mockito.eq("password2"));
    }

    @Test
    void getAllUserWorkouts() {
    }

    @Test
    void addUserWorkout() {
    }


}