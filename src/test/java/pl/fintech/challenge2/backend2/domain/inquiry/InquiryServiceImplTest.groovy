package pl.fintech.challenge2.backend2.domain.inquiry

import org.springframework.http.HttpStatus
import org.springframework.web.client.HttpClientErrorException
import spock.lang.Specification

import java.time.LocalDate

class InquiryServiceImplTest extends Specification {

    def inquiryRepository = Mock(InquiryRepository)
    def inquiryService = new InquiryServiceImpl(inquiryRepository)

    def "Save method should save an inquiry"() {
        given:
        def inquiry = createInquiry()
        inquiryRepository.findById(1) >> Optional.of(inquiry)
        when:
        inquiryService.create(inquiry)
        inquiry = inquiryRepository.findById(1).get()
        then:
        assert inquiry.getLoanAmount() == BigDecimal.TEN
        assert inquiry.getLoanDuration() == 10
        assert inquiry.getCreatedAt() == LocalDate.now()
        assert inquiry.getSubmissionDeadline() == LocalDate.now().plusDays(5)
    }

    def "FindById method should return an inquiry"() {
        given:
        def inquiry = createInquiry()
        inquiryRepository.findById(1) >> Optional.of(inquiry)
        when:
        inquiry = inquiryService.findById(1)
        then:
        assert inquiry.getLoanAmount() == BigDecimal.TEN
        assert inquiry.getLoanDuration() == 10
        assert inquiry.getCreatedAt() == LocalDate.now()
        assert inquiry.getSubmissionDeadline() == LocalDate.now().plusDays(5)
    }

    def "FindById method should throw an error"() {
        given:
        inquiryRepository.findById(1) >> Optional.empty()
        when:
        inquiryService.findById(1)
        then:
        thrown(HttpClientErrorException)
    }

    def "FindAllByLoanDurationAndAmount"() {
        given:
        def inquiry = createInquiry()
        inquiryRepository
                .findByLoanDurationBetweenAndLoanAmountBetween(9,11,
                        BigDecimal.valueOf(9), BigDecimal.valueOf(11)) >> List.of(inquiry)
        when:
        inquiry = inquiryService.findAllByLoanDurationAndAmount(9,11,
                BigDecimal.valueOf(9), BigDecimal.valueOf(11)).get(0)
        then:
        assert inquiry.getLoanAmount() == BigDecimal.TEN
        assert inquiry.getLoanDuration() == 10
        assert inquiry.getCreatedAt() == LocalDate.now()
        assert inquiry.getSubmissionDeadline() == LocalDate.now().plusDays(5)
    }

    Inquiry createInquiry() {
        return Inquiry.builder()
                .loanAmount(BigDecimal.TEN)
                .loanDuration(10)
                .submissionDeadline(LocalDate.now().plusDays(5))
                .createdAt(LocalDate.now()).build()
    }
}
