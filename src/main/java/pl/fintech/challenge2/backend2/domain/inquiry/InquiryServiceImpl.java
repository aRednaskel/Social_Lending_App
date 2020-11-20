package pl.fintech.challenge2.backend2.domain.inquiry;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InquiryServiceImpl implements InquiryService {

    private final InquiryRepository inquiryRepository;

    @Override
    @Transactional
    public void create(Inquiry inquiry) {
        inquiryRepository.save(inquiry);
    }

    @Override
    public Inquiry findById(Long id) {
        return inquiryRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }

    @Override
    public List<Inquiry> findAllByLoanDurationAndAmount(Integer minLoanDuration, Integer maxLoanDuration, BigDecimal minAmount, BigDecimal maxAmount) {
        List<Inquiry> inquiries = inquiryRepository
                .findByLoanDurationBetweenAndLoanAmountBetween(minLoanDuration, maxLoanDuration, minAmount, maxAmount);
        inquiries = inquiries.stream().sorted(Comparator
                .comparing(Inquiry::getLoanAmount)
                .thenComparing(Inquiry::getLoanDuration))
                .collect(Collectors.toList());
        return inquiries;
    }

    @Override
    public List<Inquiry> findBySubmissionDeadLine(LocalDate submissionDeadline) {
        return inquiryRepository.findBySubmissionDeadline(submissionDeadline);
    }
}
