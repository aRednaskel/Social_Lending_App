package pl.fintech.challenge2.backend2.domain.bank;

import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;

@Value
public class Transaction {

    Integer id;
    String type;
    BigDecimal amount;
    String referenceId;
    LocalDate timestamp;

}
