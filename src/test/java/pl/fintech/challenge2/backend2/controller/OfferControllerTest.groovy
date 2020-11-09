package pl.fintech.challenge2.backend2.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import pl.fintech.challenge2.backend2.controller.dto.InquiryDTO
import pl.fintech.challenge2.backend2.controller.dto.OfferDTO
import pl.fintech.challenge2.backend2.controller.mapper.InquiryMapper
import pl.fintech.challenge2.backend2.controller.mapper.OfferMapper
import pl.fintech.challenge2.backend2.domain.inquiry.Inquiry
import pl.fintech.challenge2.backend2.domain.inquiry.InquiryService
import pl.fintech.challenge2.backend2.domain.offer.Offer
import pl.fintech.challenge2.backend2.domain.offer.OfferService
import pl.fintech.challenge2.backend2.domain.offer.PaymentFrequency
import pl.fintech.challenge2.backend2.domain.user.User
import pl.fintech.challenge2.backend2.domain.user.UserService
import spock.lang.Specification
import spock.mock.DetachedMockFactory

import static org.mockito.Mockito.when
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ActiveProfiles("LOCAL")
@WebMvcTest(controllers = [InquiryController.class])
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
        when(userService.findById(1)).thenReturn(new User())
        when(inquiryService.findById(1)).thenReturn(new Inquiry())
        when(offerMapper.mapOfferDTOToOffer(offerDTO, new User(), new Inquiry())).thenReturn(offer)

        expect:
        mvc.perform(post(new URI("/api/offers"))
                .content(objectMapper.writeValueAsString(offerDTO))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
    }

    Offer createOffer() {
        return Offer.builder()
                .loanAmount(BigDecimal.TEN)
                .annualInterestRate(5d)
                .paymentFrequency(PaymentFrequency.MONTHLY).build()
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
    }
}
