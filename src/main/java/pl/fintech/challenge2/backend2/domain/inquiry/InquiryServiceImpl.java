package pl.fintech.challenge2.backend2.domain.inquiry;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import pl.fintech.challenge2.backend2.domain.user.UserService;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InquiryServiceImpl implements InquiryService {

    private final InquiryRepository inquiryRepository;

    private final UserService userService;

    @Override
    @Transactional
    public Inquiry create(Inquiry inquiry) {
        inquiry.setBorrower(userService.getCurrentUser());
        return inquiryRepository.save(inquiry);
    }

    @Override
    public Inquiry findById(Long id) {
        return inquiryRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }

    @Override
    public List<Inquiry> findAllByAmountAndLoanDuration(Integer minLoanDuration, Integer maxLoanDuration, BigDecimal minAmount, BigDecimal maxAmount) {
        List<Inquiry> inquiries = new ArrayList<>(inquiryRepository
                .findByLoanDurationBetweenAndLoanAmountBetween(minLoanDuration, maxLoanDuration, minAmount, maxAmount));
        inquiries.sort(Comparator
                .comparing(Inquiry::getLoanAmount)
                .thenComparing(Inquiry::getLoanDuration));
        return inquiries;
    }

    @Override
    public List<Inquiry> findBySubmissionDeadLine(LocalDate submissionDeadline) {
        return inquiryRepository.findBySubmissionDeadline(submissionDeadline);
    }
}
