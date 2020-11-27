package pl.fintech.challenge2.backend2.domain.inquiry;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface InquiryService {

    Inquiry create(Inquiry inquiry);
    Inquiry findById(Long id);
    List<Inquiry> findAllByAmountAndLoanDuration(Integer minLoanDuration, Integer maxLoanDuration,
                                                 BigDecimal minAmount, BigDecimal maxAmount);
    List<Inquiry> findBySubmissionDeadLine(LocalDate submissionDeadline);
}
