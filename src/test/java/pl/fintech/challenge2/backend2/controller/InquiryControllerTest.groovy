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
import pl.fintech.challenge2.backend2.controller.dto.InquiryDTO
import pl.fintech.challenge2.backend2.controller.mapper.InquiryMapper
import pl.fintech.challenge2.backend2.domain.inquiry.Inquiry
import pl.fintech.challenge2.backend2.domain.inquiry.InquiryService
import pl.fintech.challenge2.backend2.domain.user.UserService
import spock.lang.Specification
import spock.mock.DetachedMockFactory

import java.time.LocalDate

import static org.mockito.Mockito.when
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ActiveProfiles("LOCAL")
@WebMvcTest(controllers = [InquiryController.class])
class InquiryControllerTest extends Specification {

    @Autowired
    MockMvc mvc

    @MockBean
    InquiryService inquiryService

    @MockBean
    InquiryMapper inquiryMapper

    @MockBean
    UserService userService;

    def objectMapper = new ObjectMapper()

    def "POST / should create an Inquiry and return status created"() {
        given:
        def inquiryDTO = new InquiryDTO(BigDecimal.TEN, 5, LocalDate.of(2020,10,10), LocalDate.of(2020,9,9))
        def inquiry = createInquiry()
        when(inquiryMapper.mapInquiryDTOToInquiry(inquiryDTO)).thenReturn(inquiry)

        expect:
        mvc.perform(post(new URI("/api/inquiries"))
                .content(objectMapper.writeValueAsString(inquiryDTO))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isCreated())
    }

    def "GET "() {
        given:
        when(inquiryService.findAllByAmountAndLoanDuration(4,6,BigDecimal.valueOf(9),BigDecimal.valueOf(11)))
                .thenReturn(List.of(createInquiry()))
        expect:
        def result = mvc.perform(get(new URI("/api/inquiries"))
                .param("minLoanDuration", "4")
                .param("maxLoanDuration", "6")
                .param("minAmount", "9")
                .param("maxAmount","11"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
    }

    Inquiry createInquiry() {
        return Inquiry.builder()
                .loanAmount(BigDecimal.TEN)
                .loanDuration(5)
                .submissionDeadline(LocalDate.of(2022,10,10))
                .createdAt(LocalDate.now()).build()
    }

    @TestConfiguration
    static class InquiryStubConfig {
        def detachedMockFactory = new DetachedMockFactory()

        @Bean
        InquiryService inquiryService() {
            return detachedMockFactory.Stub(InquiryService)
        }

        @Bean
        InquiryMapper inquiryMapper(){
            return detachedMockFactory.Stub(InquiryMapper)
        }
    }
}
