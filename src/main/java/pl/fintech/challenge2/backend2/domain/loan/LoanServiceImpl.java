package pl.fintech.challenge2.backend2.domain.loan;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.fintech.challenge2.backend2.domain.Status;
import pl.fintech.challenge2.backend2.domain.inquiry.Inquiry;
import pl.fintech.challenge2.backend2.domain.message.Message;
import pl.fintech.challenge2.backend2.domain.offer.Offer;
import pl.fintech.challenge2.backend2.domain.user.User;
import pl.fintech.challenge2.backend2.domain.user.UserRepository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
class LoanServiceImpl implements LoanService{

    private final LoanRepository loanRepository;

    private final UserRepository userRepository;

    @Override
    @Transactional
    public List<Loan> createLoansFromOffers(List<Offer> offers, Inquiry inquiry){
        List<Loan> loans = new ArrayList<>();
        for(Offer offer : offers){
            User lender = offer.getLender();
            User borrower = inquiry.getBorrower();

            Loan loan = new Loan();
            loan.setBorrower(borrower);
            loan.setLender(lender);
            loan.setLoanAmount(offer.getLoanAmount());
            loan.setLoanDuration(inquiry.getLoanDuration());
            loan.setAnnualInterestRate(offer.getAnnualInterestRate());
            loan.setMonthlyInstallment(
                    calculateMonthlyInstallment(loan.getLoanAmount(), loan.getAnnualInterestRate(),
                            loan.getLoanDuration()));
            loan.setStatus(Status.ACCEPTED);

            List<Message> lenderMessages = lender.getMessages();
            lenderMessages.add(new Message("Pożyczyłeś pieniądze użytkownikowi"));
            lender.setMessages(lenderMessages);
            userRepository.save(lender);

            List<Message> borrowerMessages = borrower.getMessages();
            borrowerMessages.add(new Message("Otrzymałeś pieniądze z pożyczki"));
            borrower.setMessages(borrowerMessages);
            userRepository.save(borrower);

            loanRepository.save(loan);
            loans.add(loan);
        }
        return loans;
    }

    @Override
    @Transactional
    public Loan create(Loan loan) {
        loan.setMonthlyInstallment(
                calculateMonthlyInstallment(loan.getLoanAmount(), loan.getAnnualInterestRate(), loan.getLoanDuration()));
        return loanRepository.save(loan);
    }

    @Override
    @Transactional
    public List<Loan> updateLoans() {
        List<Loan> loans = loanRepository.findByLoanAmountIsGreaterThan(BigDecimal.ZERO);
        BigDecimal monthlyInterestRate;
        for (Loan loan : loans) {
            monthlyInterestRate = BigDecimal.valueOf(1 + loan.getAnnualInterestRate() / 100 / 12);
            loan.setLoanAmount(
                    loan.getLoanAmount()
                            .multiply(monthlyInterestRate));
        }
        return loans;
    }

    @Override
    @Transactional
    public Loan payBackInstallment(Loan loan) {
        loan.setLoanAmount(
                loan.getLoanAmount()
                        .subtract(loan.getMonthlyInstallment()));
        return loan;
    }

    private BigDecimal calculateMonthlyInstallment(BigDecimal amount, double annualInterestRate, int duration) {
        double monthlyInterestRate = 1 + annualInterestRate / 100 / 12;
        return amount
                .multiply(BigDecimal.valueOf(Math.pow(monthlyInterestRate, duration) * (monthlyInterestRate - 1) / (Math.pow(monthlyInterestRate, duration) - 1)));
    }
}
