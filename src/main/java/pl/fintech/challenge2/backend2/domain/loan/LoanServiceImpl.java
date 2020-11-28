package pl.fintech.challenge2.backend2.domain.loan;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
class LoanServiceImpl implements LoanService{

    private final LoanRepository loanRepository;

    @Override
    @Transactional
    public Loan create(Loan loan) {
        loan.setMonthlyInstallment(
                calculateMonthlyInstallment(loan.getLoanAmount(), loan.getAnnualInterestRate(), loan.getLoanDuration()));
        return loanRepository.save(loan);
    }

    @Override
    public List<Loan> getAll() {
        return loanRepository.findAll();
    }

    private BigDecimal calculateMonthlyInstallment(BigDecimal amount, double annualInterestRate, int duration) {
        double monthlyInterestRate = 1 + annualInterestRate / 100 / 12;
        return amount
                .multiply(BigDecimal.valueOf(Math.pow(monthlyInterestRate, duration) * (monthlyInterestRate - 1) / (Math.pow(monthlyInterestRate, duration) - 1)));
    }
}
