package pl.fintech.challenge2.backend2.domain.loan;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
class LoanServiceImpl implements LoanService{

    private final LoanRepository loanRepository;

    @Override
    @Transactional
    public Loan create(Loan loan) {
        return loanRepository.save(loan);
    }

    @Override
    public List<Loan> getAll() {
        return loanRepository.findAll();
    }
}
