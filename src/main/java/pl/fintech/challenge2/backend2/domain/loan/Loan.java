package pl.fintech.challenge2.backend2.domain.loan;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.fintech.challenge2.backend2.domain.offer.PaymentFrequency;

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

    private BigDecimal loanAmount;

    private Integer loanDuration;

    @Enumerated(value = EnumType.STRING)
    private PaymentFrequency paymentFrequency;

    private LocalDate createdAt;
}
