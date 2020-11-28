package pl.fintech.challenge2.backend2.controller.dto;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class OfferDTO {

    Long lenderId;
    Long inquiryId;
    BigDecimal proposedAmount;
    Double annualInterestRate;
}
