package pl.fintech.challenge2.backend2.domain.loan;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    List<Loan> findByLoanAmountIsGreaterThan(BigDecimal loanAmount);
}
