package pl.fintech.challenge2.backend2.domain.inquiry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
interface InquiryRepository extends JpaRepository<Inquiry, Long> {

    List<Inquiry> findByLoanDurationBetweenAndLoanAmountBetween(
            Integer minLoanDuration, Integer maxLoanDuration,
            BigDecimal minAmount, BigDecimal maxAmount);
}
