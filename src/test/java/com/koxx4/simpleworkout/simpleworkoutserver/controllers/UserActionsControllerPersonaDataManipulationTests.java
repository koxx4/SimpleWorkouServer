package com.koxx4.simpleworkout.simpleworkoutserver.controllers;

import com.koxx4.simpleworkout.simpleworkoutserver.repositories.AppUserPasswordRepository;
import com.koxx4.simpleworkout.simpleworkoutserver.repositories.AppUserRepository;
import com.koxx4.simpleworkout.simpleworkoutserver.repositories.AppUserWorkoutRepository;
import com.koxx4.simpleworkout.simpleworkoutserver.security.JwtUserPrivateAccessFilter;
import com.koxx4.simpleworkout.simpleworkoutserver.services.UserService;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = UserActionsController.class)
@ActiveProfiles(profiles = "dev")
class UserActionsControllerPersonaDataManipulationTests {

    @MockBean
    private AppUserRepository appUserRepository;

    @MockBean
    private AppUserPasswordRepository appUserPasswordRepository;

    @MockBean
    private AppUserWorkoutRepository appUserWorkoutRepository;

    @MockBean
    private ConfigurableJWTProcessor<SecurityContext> configurableJWTProcessor;

    @MockBean
    private FilterRegistrationBean<JwtUserPrivateAccessFilter> userDataRestFilter;

    @MockBean
    private UserService userServiceMock;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    void changeUserNicknameWithValidNickname() throws Exception {
        mockMvc.perform(post("/user/nickname")
                        .param("newNickname", "new_nickname"))
                .andExpect(status().isAccepted());
    }

    @Test
    @WithMockUser
    void changeUserNicknameWithInvalidNickname() throws Exception {
        mockMvc.perform(post("/user/nickname")
                        .param("newNickname", "    "))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "test_username", password = "current_pass", roles = "USER")
    void changingUserPasswordWithCorrectOldPassword() throws Exception {

        mockMvc.perform(post("/user/password")
                        .param("oldPassword", "current_pass")
                        .param("newPassword", "new_pass"))
                .andExpect(status().isAccepted());
    }

    @Test
    @WithMockUser(username = "test_username", password = "current_pass", roles = "USER")
    void changingUserPasswordWithWrongOldPassword() throws Exception {

        mockMvc.perform(post("/user/password")
                        .param("oldPassword", "current_pass_wrong")
                        .param("newPassword", "new_pass"))
                .andExpect(status().isBadRequest());
    }

}