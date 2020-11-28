package pl.fintech.challenge2.backend2.controller.dto;

import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;

@Value
public class InquiryDTO {


    BigDecimal loanAmount;
    Integer loanDuration;
    LocalDate submissionDeadline;
    LocalDate createdAt;
}
