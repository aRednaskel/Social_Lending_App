package pl.fintech.challenge2.backend2.domain.offer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.fintech.challenge2.backend2.domain.inquiry.Inquiry;
import pl.fintech.challenge2.backend2.domain.user.User;

import javax.persistence.*;
import java.math.BigDecimal;

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
    private User lender;

    @ManyToOne
    private Inquiry inquiry;

    private BigDecimal loanAmount;

    private double annualInterestRate;

    @Enumerated(value = EnumType.STRING)
    private PaymentFrequency paymentFrequency;

    @JsonIgnore
    private OfferStatus offerStatus;

}
