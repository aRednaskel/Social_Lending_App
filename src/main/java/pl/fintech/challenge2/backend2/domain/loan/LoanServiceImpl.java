package pl.fintech.challenge2.backend2.domain.loan;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
class LoanServiceImpl implements LoanService{

    private final LoanRepository loanRepository;

    @Override
    @Transactional
    public Loan create(Loan loan) {
        return loanRepository.save(loan);
    }
}
