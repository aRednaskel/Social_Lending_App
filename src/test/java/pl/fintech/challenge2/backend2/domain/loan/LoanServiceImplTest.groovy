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

    def "UpdateLoans should increase value of Loan Amount By monthlyInterestRate rate"() {
        given:
        def loan = createLoan()
        loanRepository.findByLoanAmountIsGreaterThan(BigDecimal.ZERO) >> List.of(loan)
        when:
        def valueAfterUpdate = loan.getLoanAmount()
                .multiply(BigDecimal.valueOf(1 + loan.getAnnualInterestRate() / 100 / 12))
        loan = loanService.updateLoans()
        then:
        loan.get(0).getLoanAmount() == valueAfterUpdate
    }

    def "PayBackInstallment should decrease loan amount by monthlyInstallment"() {
        given:
        def loan = createLoan()
        when:
        def valueAfterUpdate = loan.getLoanAmount()
                .subtract(loan.getMonthlyInstallment())
        loan = loanService.payBackInstallment(loan)
        then:
        loan.getLoanAmount() == valueAfterUpdate
    }

    Loan createLoan() {
        return Loan.builder()
            .lender(new User())
            .borrower(new User())
            .loanAmount(BigDecimal.TEN)
            .monthlyInstallment(BigDecimal.ONE)
            .loanDuration(10)
            .annualInterestRate(5d)
            .createdAt(LocalDate.now()).build()
    }
}
