package pl.fintech.challenge2.backend2.restclient.bank;

import lombok.Value;
import java.math.BigDecimal;

@Value
public class InternalTransactionDTO {
    String sourceAccountNumber;
    String targetAccountNumber;
    BigDecimal amount;
}
