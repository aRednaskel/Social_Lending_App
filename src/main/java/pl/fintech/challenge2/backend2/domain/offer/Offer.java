package pl.fintech.challenge2.backend2.domain.offer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.fintech.challenge2.backend2.domain.inquiry.Inquiry;
import pl.fintech.challenge2.backend2.domain.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @NotNull
    private User lender;

    @OneToMany
    private List<Inquiry> inquiries;

    private BigDecimal loanAmount;

    private double annualInterestRate;

    @Enumerated(value = EnumType.STRING)
    private PaymentFrequency paymentFrequency;



}
