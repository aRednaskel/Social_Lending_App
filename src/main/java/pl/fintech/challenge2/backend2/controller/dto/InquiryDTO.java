package pl.fintech.challenge2.backend2.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class InquiryDTO {

    private BigDecimal loanAmount;
    private Integer loanDuration;
    private LocalDate submissionDeadline;
}
