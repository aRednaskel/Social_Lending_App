package pl.fintech.challenge2.backend2.domain.bank;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class Account {
    String name;
    String number;
    List<Transaction> transactions;
    BigDecimal accountBalance;
}
