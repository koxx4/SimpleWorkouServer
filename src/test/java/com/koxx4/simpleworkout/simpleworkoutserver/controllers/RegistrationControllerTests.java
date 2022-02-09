package com.koxx4.simpleworkout.simpleworkoutserver.controllers;

import com.koxx4.simpleworkout.simpleworkoutserver.repositories.AppUserPasswordRepository;
import com.koxx4.simpleworkout.simpleworkoutserver.repositories.AppUserRepository;
import com.koxx4.simpleworkout.simpleworkoutserver.repositories.AppUserWorkoutRepository;
import com.koxx4.simpleworkout.simpleworkoutserver.services.UserService;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RegistrationController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles(profiles = "dev")
public class RegistrationControllerTests {

    @MockBean
    private ConfigurableJWTProcessor<SecurityContext> configurableJWTProcessor;

    @MockBean
    private AppUserRepository appUserRepository;

    @MockBean
    private AppUserPasswordRepository appUserPasswordRepository;

    @MockBean
    private AppUserWorkoutRepository appUserWorkoutRepository;


    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void registeringTestUser_WithValidData() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/register/user")
                .param("username", "test_user")
                .param("email", "simple@email.com")
                .param("password", "pa$$word"))
                .andExpect(status().isCreated());

        Mockito.verify(userService, Mockito.atLeastOnce()).
                saveUser(Mockito.eq("test_user"), Mockito.eq("simple@email.com"),
                        Mockito.eq("pa$$word"), Mockito.eq(new String[]{"REGULAR_USER"}));
    }

    @Test
    public void registeringTestUser_WithInvalidUsernameAndEmail() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/register/user")
                        .param("username", " ")
                        .param("email", "simplemail.com")
                        .param("password", "pa$$word"))
                .andExpect(status().isBadRequest());
    }
}
