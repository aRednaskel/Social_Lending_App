package pl.fintech.challenge2.backend2.domain.loan;

import java.util.List;

public interface LoanService {

    Loan create(Loan loan);

    List<Loan> getAll();
}
