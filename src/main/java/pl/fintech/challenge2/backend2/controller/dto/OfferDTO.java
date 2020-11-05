package pl.fintech.challenge2.backend2.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.fintech.challenge2.backend2.domain.offer.PaymentFrequency;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class OfferDTO {

    private Long lenderId;
    private Long inquiryId;
    private BigDecimal proposedAmount;
    private Double annualInterestRate;
    private PaymentFrequency paymentFrequency;
}
