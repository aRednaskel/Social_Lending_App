package pl.fintech.challenge2.backend2.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Value;
import pl.fintech.challenge2.backend2.domain.offer.PaymentFrequency;

import java.math.BigDecimal;

@Value
public class OfferDTO {

    Long lenderId;
    Long inquiryId;
    BigDecimal proposedAmount;
    Double annualInterestRate;
    PaymentFrequency paymentFrequency;
}
