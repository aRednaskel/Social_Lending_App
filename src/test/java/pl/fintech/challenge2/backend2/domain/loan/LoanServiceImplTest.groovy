package pl.fintech.challenge2.backend2.domain.loan

import pl.fintech.challenge2.backend2.domain.offer.PaymentFrequency
import pl.fintech.challenge2.backend2.domain.user.User
import spock.lang.Specification

import java.time.LocalDate

class LoanServiceImplTest extends Specification {

    def loanRepository = Mock(LoanRepository)
    def loanService = new LoanServiceImpl(loanRepository)

    def "Create method should return a loan"() {
        given:
        def loan = createLoan()
        loanRepository.save(loan) >> loan
        when:
        loan =loanService.create(loan)
        then:
        assert loan.getLoanAmount() == BigDecimal.TEN
        assert loan.getAnnualInterestRate() == 5
        assert loan.getPaymentFrequency() == PaymentFrequency.MONTHLY
        assert loan.getCreatedAt() == LocalDate.now()
    }

    Loan createLoan() {
        return Loan.builder()
            .lender(new User())
            .borrower(new User())
            .loanAmount(BigDecimal.TEN)
            .annualInterestRate(5d)
            .paymentFrequency(PaymentFrequency.MONTHLY)
            .createdAt(LocalDate.now()).build()
    }
}
