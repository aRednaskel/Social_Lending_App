package pl.fintech.challenge2.backend2.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import pl.fintech.challenge2.backend2.domain.bank.BankAppClient
import pl.fintech.challenge2.backend2.domain.user.User
import pl.fintech.challenge2.backend2.domain.user.UserService
import spock.lang.Specification
import spock.mock.DetachedMockFactory

import static org.mockito.Mockito.when
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ActiveProfiles("LOCAL")
@WebMvcTest(controllers = [BankController.class])
class BankControllerTest extends Specification {

    @Autowired
    MockMvc mvc

    @MockBean
    BankAppClient bankAppClient

    @MockBean
    UserService userService

    def objectMapper = new ObjectMapper()

    def "POST / CreateInternalTransaction should return status created"() {
        given:
        when(userService.findById(1)).thenReturn(createUser("631b7cb4-daca-4c35-ab10-85e4c4b30c26"))
        when(userService.findById(2)).thenReturn(createUser("95bd9b19-9465-4403-8f4e-d9f17ebb82a9"))

        expect:
        mvc.perform(post(new URI("/api/transactions"))
                .param("senderId", "1")
                .param("receiverId", "2")
                .param("value", "10")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated())
    }

    def "POST / CreateExternalPayment should return status created"() {
        given:
        when(userService.findById(1)).thenReturn(createUser("631b7cb4-daca-4c35-ab10-85e4c4b30c26"))
        expect:
        mvc.perform(post(new URI("/api/transactions/externalPayment"))
                .param("userId", "1")
                .param("value", "10")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated())
    }

    def "CreateExternalWithdrawal"() {
        given:
        when(userService.findById(1)).thenReturn(createUser("631b7cb4-daca-4c35-ab10-85e4c4b30c26"))
        expect:
        mvc.perform(post(new URI("/api/transactions/externalWithdrawal"))
                .param("userId", "1")
                .param("value", "10")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated())
    }

    User createUser(String accountNumber) {
        return User.builder()
                .accountNumber(accountNumber).build()
    }

    @TestConfiguration
    static class InquiryStubConfig {
        def detachedMockFactory = new DetachedMockFactory()

        @Bean
        BankAppClient bankAppClient() {
            return detachedMockFactory.Stub(BankAppClient)
        }

        @Bean
        UserService userService() {
            return detachedMockFactory.Stub(UserService)
        }

    }
}
