package pl.fintech.challenge2.backend2.domain.loan;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.fintech.challenge2.backend2.domain.offer.PaymentFrequency;
import pl.fintech.challenge2.backend2.domain.user.User;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private User borrower;

    @ManyToOne
    private User lender;

    private BigDecimal loanAmount;
    private Integer loanDuration;
    private Double annualInterestRate;
    private PaymentFrequency paymentFrequency;
    private LocalDate createdAt;

    @JsonIgnore
    private BigDecimal monthlyInstallment;
}
