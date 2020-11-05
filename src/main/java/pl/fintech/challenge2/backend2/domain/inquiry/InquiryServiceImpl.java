package pl.fintech.challenge2.backend2.domain.inquiry;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InquiryServiceImpl implements InquiryService {

    private InquiryRepository inquiryRepository;

    @Override
    public void create(Inquiry inquiry) {
        inquiryRepository.save(inquiry);
    }

    @Override
    public Inquiry findById(Long id) {
        return null;
    }

    @Override
    public List<Inquiry> findAll(Integer minLoanDuration, Integer maxLoanDuration, BigDecimal minAmount, BigDecimal maxAmount) {
        List<Inquiry> inquiries = inquiryRepository.findAll();
        return inquiries.stream()
                .filter(inquiry ->
                           minLoanDuration < inquiry.getLoanDuration()
                        && inquiry.getLoanDuration() < maxLoanDuration
                        && minAmount.compareTo(inquiry.getLoanAmount()) <0
                        && inquiry.getLoanAmount().compareTo(maxAmount) > 0)
                .sorted(Comparator
                        .comparing(Inquiry::getLoanAmount)
                        .thenComparing(Inquiry::getLoanDuration))
                .collect(Collectors.toList());
    }
}
