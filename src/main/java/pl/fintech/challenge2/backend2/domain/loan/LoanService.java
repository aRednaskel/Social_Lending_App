package pl.fintech.challenge2.backend2.domain.loan;

import pl.fintech.challenge2.backend2.domain.inquiry.Inquiry;
import pl.fintech.challenge2.backend2.domain.offer.Offer;

import javax.transaction.Transactional;
import java.util.List;

public interface LoanService {

    @Transactional
    List<Loan> createLoansFromOffers(List<Offer> offers, Inquiry inquiry);

    Loan create(Loan loan);

    List<Loan> updateLoans();

    Loan payBackInstallment(Loan loan);

}
