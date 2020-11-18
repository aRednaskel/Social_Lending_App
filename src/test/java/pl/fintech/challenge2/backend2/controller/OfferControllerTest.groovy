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
import pl.fintech.challenge2.backend2.controller.dto.OfferDTO
import pl.fintech.challenge2.backend2.controller.mapper.OfferMapper
import pl.fintech.challenge2.backend2.domain.inquiry.Inquiry
import pl.fintech.challenge2.backend2.domain.inquiry.InquiryService
import pl.fintech.challenge2.backend2.domain.loan.Loan
import pl.fintech.challenge2.backend2.domain.loan.LoanService
import pl.fintech.challenge2.backend2.domain.offer.Offer
import pl.fintech.challenge2.backend2.domain.offer.OfferService
import pl.fintech.challenge2.backend2.domain.offer.PaymentFrequency
import pl.fintech.challenge2.backend2.domain.user.User
import pl.fintech.challenge2.backend2.domain.user.UserService
import spock.lang.Specification
import spock.mock.DetachedMockFactory

import java.time.LocalDate

import static org.mockito.Mockito.when
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ActiveProfiles("LOCAL")
@WebMvcTest(controllers = [OfferController.class])
class OfferControllerTest extends Specification {

    @Autowired
    MockMvc mvc

    @MockBean
    private OfferService offerService;
    @MockBean
    private OfferMapper offerMapper;
    @MockBean
    private UserService userService;
    @MockBean
    private InquiryService inquiryService;

    def objectMapper = new ObjectMapper()

    def "POST / should create an Offer and return status created"() {
        given:
        def offerDTO = new OfferDTO(1,1,BigDecimal.TEN,5, PaymentFrequency.ONCE)
        def offer = createOffer()
        def user = createUser(1)
        when(userService.findById(1)).thenReturn(user)
        when(inquiryService.findById(1)).thenReturn(createInquiry())
        when(offerMapper.mapOfferDTOToOffer(offerDTO, user, createInquiry())).thenReturn(offer)

        expect:
        mvc.perform(post(new URI("/api/offers"))
                .content(objectMapper.writeValueAsString(offerDTO))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isCreated())
    }

    def "POST / should create a loan and return status created"() {
        given:
        def offer = createOffer()
        offer.setLender(createUser(1))
        def inquiry = createInquiry()
        inquiry.setBorrower(createUser(2))
        when(offerService.findById(1)).thenReturn(offer)
        when(inquiryService.findById(1)).thenReturn(inquiry)
        when(offerMapper.mapOfferAndInquiryToLoan(offer, inquiry)).thenReturn(createLoan())

        expect:
        mvc.perform(post(new URI("/api/offers/accept"))
                .param("offerId", "1")
                .param("inquiryId", "1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated())
    }

    Offer createOffer() {
        return Offer.builder()
                .id(1l)
                .loanAmount(BigDecimal.TEN)
                .annualInterestRate(5d)
                .paymentFrequency(PaymentFrequency.MONTHLY).build()
    }

    Inquiry createInquiry() {
        return Inquiry.builder()
                .loanAmount(BigDecimal.TEN)
                .loanDuration(5)
                .submissionDeadline(LocalDate.now().plusDays(5))
                .createdAt(LocalDate.now()).build()
    }

    User createUser(long id) {
        return User.builder()
                    .id(id)
                    .email("example@example.com")
                    .password("example")
                    .name("firstExample")
                    .surname("LastExample")
                    .phone("123456789").build()
    }

    Loan createLoan() {
        return Loan.builder()
                    .borrower(createUser(2))
                    .lender(createUser(1))
                    .loanAmount(BigDecimal.TEN)
                    .loanDuration(5)
                    .annualInterestRate(5)
                    .paymentFrequency(PaymentFrequency.MONTHLY)
                    .createdAt(LocalDate.now()).build()
    }

    @TestConfiguration
    static class OfferStubConfig {
        def detachedMockFactory = new DetachedMockFactory()

        @Bean
        OfferService offerService() {
            return detachedMockFactory.Stub(OfferService)
        }

        @Bean
        OfferMapper offerMapper(){
            return detachedMockFactory.Stub(OfferMapper)
        }

        @Bean
        UserService userService(){
            return detachedMockFactory.Stub(UserService)
        }

        @Bean
        InquiryService inquiryService(){
            return detachedMockFactory.Stub(InquiryService)
        }

        @Bean
        LoanService loanService(){
            return detachedMockFactory.Stub(LoanService)
        }
    }
}
