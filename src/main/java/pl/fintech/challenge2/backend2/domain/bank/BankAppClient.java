package pl.fintech.challenge2.backend2.domain.bank;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;

@Service
@RequiredArgsConstructor
public class BankAppClient {

    private final RestTemplate restTemplate;

    public String createAccount(String email) throws URISyntaxException {
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

    public Account getAccountInfo(String accountNumber) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("example", "example");
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<Account> getData = restTemplate.exchange(
                "https://hltechbank.thebe-team.sit.fintechchallenge.pl/accounts/" + accountNumber,
                HttpMethod.GET, request, Account.class);
        return getData.getBody();
    }
}
