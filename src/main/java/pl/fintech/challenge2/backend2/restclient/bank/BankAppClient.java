package pl.fintech.challenge2.backend2.restclient.bank;

import pl.fintech.challenge2.backend2.domain.user.User;

import java.math.BigDecimal;

public interface BankAppClient {

     String createAccount(String email);

     Account getAccountInfo(String accountNumber);

     boolean createInternalTransaction(User sender, User receiver, BigDecimal value);

     boolean createExternalPayment(User user, BigDecimal value);

     boolean createExternalWithdrawal(User user, BigDecimal value);
}
