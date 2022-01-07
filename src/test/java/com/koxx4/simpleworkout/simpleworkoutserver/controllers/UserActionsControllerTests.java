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
class UserActionsControllerTests {

    @Mock
    private UserService userServiceMock;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup(){
        UserActionsController userActionsController = new UserActionsController(userServiceMock);
        RegistrationController registrationController = new RegistrationController(userServiceMock);
        this.mockMvc = MockMvcBuilders.standaloneSetup(userActionsController, registrationController).build();
    }

    @Test
    void changeUserNickname() {
    }

    @Test
    void changeUserPassword() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/register/user")
                        .param("username", "test_user")
                        .param("email", "simple@email.com")
                        .param("password", "password1"))
                .andExpect(status().isCreated());

        mockMvc.perform(MockMvcRequestBuilders.post("/user/actions/test_user/password")
                        .param("username", "test_user")
                        .param("email", "simple@email.com")
                        .param("password", "password2"))
                .andExpect(status().isAccepted());

        Mockito.verify(userServiceMock, Mockito.atLeastOnce()).
                changeUserPassword(Mockito.eq("test_user"), Mockito.eq("password2"));
    }

    @Test
    void getAllUserWorkouts() {
    }

    @Test
    void addUserWorkout() {
    }


}