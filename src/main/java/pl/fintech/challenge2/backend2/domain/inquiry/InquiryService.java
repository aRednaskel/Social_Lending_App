package pl.fintech.challenge2.backend2.domain.inquiry;

import java.math.BigDecimal;
import java.util.List;

public interface InquiryService {

    void create(Inquiry inquiry);
    Inquiry findById(Long id);
    List<Inquiry> findAll(Integer minLoanDuration, Integer maxLoanDuration,
                        BigDecimal minAmount, BigDecimal maxAmount);
}
