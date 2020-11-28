package pl.fintech.challenge2.backend2.domain.inquiry


import org.springframework.web.client.HttpClientErrorException
import pl.fintech.challenge2.backend2.domain.user.UserService
import spock.lang.Specification

import java.time.LocalDate

class InquiryServiceImplTest extends Specification {

    def inquiryRepository = Mock(InquiryRepository)
    def userService = Mock(UserService)
    def inquiryService = new InquiryServiceImpl(inquiryRepository, userService)

    def "Save method should save an inquiry"() {
        given:
        def inquiry = createInquiry(1)
        inquiryRepository.findById(1) >> Optional.of(inquiry)
        when:
        inquiryService.create(inquiry)
        inquiry = inquiryRepository.findById(1).get()
        then:
        assert inquiry.getLoanAmount() == BigDecimal.valueOf(199999)
        assert inquiry.getLoanDuration() == 11
        assert inquiry.getCreatedAt() == LocalDate.now()
        assert inquiry.getSubmissionDeadline() == LocalDate.now().plusDays(5)
    }

    def "FindById method should return an inquiry"() {
        given:
        def inquiry = createInquiry(1)
        inquiryRepository.findById(1) >> Optional.of(inquiry)
        when:
        inquiry = inquiryService.findById(1)
        then:
        assert inquiry.getLoanAmount() == BigDecimal.valueOf(199999)
        assert inquiry.getLoanDuration() == 11
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

    def "FindAllByLoanDurationAndAmount should retrieve and sort a list of inquiries"() {
        given:
        def inquiries = createListOfInquiries()
        inquiryRepository
                .findByLoanDurationBetweenAndLoanAmountBetween(9,51,
                        BigDecimal.valueOf(9), BigDecimal.valueOf(999999)) >> inquiries
        when:
        inquiries = inquiryService.findAllByAmountAndLoanDuration(9,51,
        BigDecimal.valueOf(9), BigDecimal.valueOf(999999))
        then:
        assert inquiries.get(0).getLoanAmount() == inquiries.get(1).getLoanAmount()
        assert inquiries.get(0).getLoanDuration() < inquiries.get(1).getLoanDuration()
        assert inquiries.get(1).getLoanAmount() < inquiries.get(2).getLoanAmount()
    }

    def "FindBySubmissionDeadLine should return list of inquiries with needed submission deadline"(){
        given:
        def inquiry = createInquiry(1)
        def submissionDeadline = LocalDate.now().plusDays(5)
        inquiryRepository
                .findBySubmissionDeadline(submissionDeadline) >> List.of(inquiry)
        when:
        inquiry = inquiryService.findBySubmissionDeadLine(submissionDeadline).get(0)
        then:
        assert inquiry.getLoanAmount() == BigDecimal.valueOf(199999)
        assert inquiry.getLoanDuration() == 11
        assert inquiry.getCreatedAt() == LocalDate.now()
        assert inquiry.getSubmissionDeadline() == LocalDate.now().plusDays(5)
    }

    Inquiry createInquiry(int inquiryFactor) {
        return Inquiry.builder()
                .id((Long) (Math.random() * Long.MAX_VALUE))
                .loanAmount(BigDecimal.valueOf(200000 - inquiryFactor))
                .loanDuration(10 + inquiryFactor)
                .submissionDeadline(LocalDate.now().plusDays(5))
                .createdAt(LocalDate.now()).build()
    }
    List<Inquiry> createListOfInquiries() {
        List<Inquiry> inquiries = new ArrayList<>()
        def inquiry = createInquiry(5)
        inquiry.setLoanDuration(10)
        inquiries.add(createInquiry(3))
        inquiries.add(createInquiry(5))
        inquiries.add(createInquiry(4))
        inquiries.add(inquiry)
        return inquiries
    }
}
