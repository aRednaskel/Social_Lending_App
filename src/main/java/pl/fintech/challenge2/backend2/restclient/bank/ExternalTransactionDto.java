package pl.fintech.challenge2.backend2.restclient.bank;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class ExternalTransactionDto {
    String accountNumber;
    BigDecimal amount;
}
