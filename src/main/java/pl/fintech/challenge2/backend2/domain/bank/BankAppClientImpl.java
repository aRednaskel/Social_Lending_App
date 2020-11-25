package pl.fintech.challenge2.backend2.domain.bank;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.fintech.challenge2.backend2.domain.user.User;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class BankAppClientImpl implements BankAppClient{

    private final RestTemplate restTemplate;

    @Override
    public String createAccount(String email) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("example", "example");
        HttpEntity<AccountDTO> request = new HttpEntity<>(new AccountDTO(email), headers);
        ResponseEntity<String> getData = restTemplate.exchange(
                "https://hltechbank.thebe-team.sit.fintechchallenge.pl/accounts",
                HttpMethod.POST, request, String.class);
        return getData.getHeaders()
                .get("Location").get(0)
                .replace("https://hltechbank.thebe-team.sit.fintechchallenge.pl/accounts/", "");
    }

    @Override
    public Account getAccountInfo(String accountNumber) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("example", "example");
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<Account> getData = restTemplate.exchange(
                "https://hltechbank.thebe-team.sit.fintechchallenge.pl/accounts/" + accountNumber,
                HttpMethod.GET, request, Account.class);
        return getData.getBody();
    }

    @Override
    public void createInternalTransaction(User sender, User receiver, BigDecimal value) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("example", "example");
        HttpEntity<InternalTransactionDTO> request = new HttpEntity<>(
                new InternalTransactionDTO(sender.getAccountNumber(),receiver.getAccountNumber(), value),headers);
        ResponseEntity<String> getData = restTemplate.exchange(
                "https://hltechbank.thebe-team.sit.fintechchallenge.pl/transactions/",
                HttpMethod.POST, request, String.class);
    }

    @Override
    public void createExternalPayment(User user, BigDecimal value) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("example", "example");
        HttpEntity<ExternalTransactionDto> request = new HttpEntity<>(
                new ExternalTransactionDto(user.getAccountNumber(), value),headers);
        ResponseEntity<String> getData = restTemplate.exchange(
                "https://hltechbank.thebe-team.sit.fintechchallenge.pl/payments/",
                HttpMethod.POST, request, String.class);
    }

    public void createExternalWithdrawal(User user, BigDecimal value){
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("example", "example");
        HttpEntity<ExternalTransactionDto> request = new HttpEntity<>(
                new ExternalTransactionDto(user.getAccountNumber(), value),headers);
        ResponseEntity<String> getData = restTemplate.exchange(
                "https://hltechbank.thebe-team.sit.fintechchallenge.pl/withdrawals/",
                HttpMethod.POST, request, String.class);
    }
}
