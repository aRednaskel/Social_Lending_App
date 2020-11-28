package pl.fintech.challenge2.backend2.restclient.bank;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class Transaction {

    Integer id;
    String type;
    BigDecimal amount;
    String referenceId;
    LocalDate timestamp;

}
