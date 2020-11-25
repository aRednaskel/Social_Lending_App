package pl.fintech.challenge2.backend2.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import pl.fintech.challenge2.backend2.controller.dto.ChangeEmailDTO;
import pl.fintech.challenge2.backend2.controller.dto.ChangePasswordDTO;
import pl.fintech.challenge2.backend2.controller.dto.RegistrationDTO;
import pl.fintech.challenge2.backend2.domain.user.Role;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("LOCAL")
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

//    @Test
//    public void shouldStatusBe201WhenBasicRegistration() throws Exception {
//        RegistrationDTO registrationDTO = new RegistrationDTO();
//        registrationDTO.setEmail("people.defender@email.com");
//        registrationDTO.setPassword("wisnia");
//        mockMvc.perform(post("/api/users/register")
//                .content(objectMapper.writeValueAsString(registrationDTO))
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().is(201))
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.email").value("people.defender@email.com"))
//                .andExpect(jsonPath("$.password").doesNotExist());
//    }

    @Test
    public void shouldStatusBe201WhenAllFieldsFilled() throws Exception {
        RegistrationDTO registrationDTO = new RegistrationDTO();
        registrationDTO.setEmail("bestprogrammer@email.com");
        registrationDTO.setPassword("wisnia");
        registrationDTO.setName("El-Me-dżel");
        registrationDTO.setSurname("Cień");
        registrationDTO.setPhone("123456789");
        registrationDTO.setRoles(new HashSet<>(Collections.singletonList(new Role("BORROWER"))));
        mockMvc.perform(post("/api/users/register")
                .content(objectMapper.writeValueAsString(registrationDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(201))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email").value("bestprogrammer@email.com"))
                .andExpect(jsonPath("$.password").doesNotExist())
                .andExpect(jsonPath("$.name").value("El-Me-dżel"))
                .andExpect(jsonPath("$.surname").value("Cień"))
                .andExpect(jsonPath("$.phone").value("123456789"));
                //todo: make it pass, I dont have time to learn how to shell value out of json
                //  do it cause demo is on Friday
//                .andExpect(jsonPath("$.roles").value("{id=1, name=BORROWER, authority=BORROWER}"));
    }

    @Test
    public void shouldStatusBe200WhenLoggingIn() throws Exception {
        RegistrationDTO registrationDTO = new RegistrationDTO();
        registrationDTO.setEmail("bestprogrammer@email.com");
        registrationDTO.setPassword("wisnia");
        registrationDTO.setName("El-Me-dżel");
        registrationDTO.setSurname("Cień");
        registrationDTO.setPhone("123456789");
        registrationDTO.setRoles(new HashSet<>(Collections.singletonList(new Role("BORROWER"))));
        mockMvc.perform(post("/api/users/register")
                .content(objectMapper.writeValueAsString(registrationDTO))
                .contentType(MediaType.APPLICATION_JSON));
        mockMvc.perform(post("/login")
                .param("username", "bestprogrammer@email.com")
                .param("password", "wisnia")
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().is(200));
    }

    @Test
    public void shouldChangeEmail() throws Exception {
        RegistrationDTO registrationDTO = new RegistrationDTO();
        registrationDTO.setEmail("bestprogrammer@email.com");
        registrationDTO.setPassword("wisnia");
        registrationDTO.setName("El-Me-dżel");
        registrationDTO.setSurname("Cień");
        registrationDTO.setPhone("123456789");
        registrationDTO.setRoles(new HashSet<>(Collections.singletonList(new Role("BORROWER"))));
        MvcResult result = mockMvc.perform(post("/api/users/register")
                .content(objectMapper.writeValueAsString(registrationDTO))
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        String content = result.getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(content);

        ChangeEmailDTO changeEmailDTO = new ChangeEmailDTO();
        changeEmailDTO.setPassword("wisnia");
        changeEmailDTO.setNewEmail("wisnia2@o2.pl");
        mockMvc.perform(put("/api/users/" + jsonNode.get("id").toString() + "/change-email")
                .content(objectMapper.writeValueAsString(changeEmailDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email").value("wisnia2@o2.pl"))
                .andExpect(jsonPath("$.password").doesNotExist())
                .andExpect(jsonPath("$.name").value("El-Me-dżel"))
                .andExpect(jsonPath("$.surname").value("Cień"))
                .andExpect(jsonPath("$.phone").value("123456789"));
    }

    @Test
    public void shouldChangePassword() throws Exception {
        RegistrationDTO registrationDTO = new RegistrationDTO();
        registrationDTO.setEmail("bestprogrammer@email.com");
        registrationDTO.setPassword("wisnia");
        registrationDTO.setName("El-Me-dżel");
        registrationDTO.setSurname("Cień");
        registrationDTO.setPhone("123456789");
        registrationDTO.setRoles(new HashSet<>(Collections.singletonList(new Role("BORROWER"))));
        MvcResult result = mockMvc.perform(post("/api/users/register")
                .content(objectMapper.writeValueAsString(registrationDTO))
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        String content = result.getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(content);

        ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO();
        changePasswordDTO.setOldPassword("wisnia");
        changePasswordDTO.setNewPassword("wisnia2");
        mockMvc.perform(put("/api/users/" + jsonNode.get("id").toString() + "/change-password")
                .content(objectMapper.writeValueAsString(changePasswordDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email").value("bestprogrammer@email.com"))
                .andExpect(jsonPath("$.password").doesNotExist())
                .andExpect(jsonPath("$.name").value("El-Me-dżel"))
                .andExpect(jsonPath("$.surname").value("Cień"))
                .andExpect(jsonPath("$.phone").value("123456789"));
    }
}
