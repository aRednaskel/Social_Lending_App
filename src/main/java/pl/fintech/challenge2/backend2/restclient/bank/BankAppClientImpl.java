package pl.fintech.challenge2.backend2.restclient.bank;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.fintech.challenge2.backend2.domain.user.User;

import java.math.BigDecimal;

@Service
public class BankAppClientImpl implements BankAppClient{

    private final String bankUrl;
    private final HttpHeaders headers;
    private final RestTemplate restTemplate;


    public BankAppClientImpl(@Value("${bank.url.host}") String bankUrl, @Value("${bank.user}") String user,
                             @Value("${bank.password}") String password, RestTemplate restTemplate) {
        this.bankUrl = bankUrl;
        this.restTemplate = restTemplate;
        this.headers = new HttpHeaders();
        this.headers.setBasicAuth(user, password);
    }

    @Override
    public String createAccount(String email) {
        HttpEntity<AccountDTO> request = new HttpEntity<>(new AccountDTO(email), headers);
        ResponseEntity<String> getData = restTemplate.exchange(
                bankUrl +  "accounts",
                HttpMethod.POST, request, String.class);
        return getData.getHeaders()
                .get("Location").get(0)
                .replace(bankUrl + "accounts/", "");
    }

    @Override
    public Account getAccountInfo(String accountNumber) {
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<Account> getData = restTemplate.exchange(
                bankUrl + "accounts/" + accountNumber,
                HttpMethod.GET, request, Account.class);
        return getData.getBody();
    }

    @Override
    public boolean createInternalTransaction(User sender, User receiver, BigDecimal value) {
        HttpEntity<InternalTransactionDTO> request = new HttpEntity<>(
                new InternalTransactionDTO(sender.getAccountNumber(),receiver.getAccountNumber(), value),headers);
        ResponseEntity<String> getData = restTemplate.exchange(
                bankUrl + "transactions/",
                HttpMethod.POST, request, String.class);
        return getData.getStatusCode() == HttpStatus.CREATED;
    }

    @Override
    public void createExternalPayment(User user, BigDecimal value) {
        HttpEntity<ExternalTransactionDto> request = new HttpEntity<>(
                new ExternalTransactionDto(user.getAccountNumber(), value),headers);
        ResponseEntity<String> getData = restTemplate.exchange(
                bankUrl + "payments/",
                HttpMethod.POST, request, String.class);
    }

    public void createExternalWithdrawal(User user, BigDecimal value){
        HttpEntity<ExternalTransactionDto> request = new HttpEntity<>(
                new ExternalTransactionDto(user.getAccountNumber(), value),headers);
        ResponseEntity<String> getData = restTemplate.exchange(
                bankUrl + "withdrawals/",
                HttpMethod.POST, request, String.class);
    }
}
