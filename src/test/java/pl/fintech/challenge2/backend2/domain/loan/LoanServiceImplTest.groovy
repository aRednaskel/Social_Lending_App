package pl.fintech.challenge2.backend2.domain.loan


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
        loan = loanService.create(loan)
        then:
        loan.getLoanAmount() == BigDecimal.TEN
        loan.getAnnualInterestRate() == 5
        loan.getCreatedAt() == LocalDate.now()
    }

    Loan createLoan() {
        return Loan.builder()
            .lender(new User())
            .borrower(new User())
            .loanAmount(BigDecimal.TEN)
            .loanDuration(10)
            .annualInterestRate(5d)
            .createdAt(LocalDate.now()).build()
    }
}
