package pl.fintech.challenge2.backend2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import pl.fintech.challenge2.backend2.restclient.bank.BankAppClient;
import pl.fintech.challenge2.backend2.domain.user.User;
import pl.fintech.challenge2.backend2.domain.user.UserService;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class BankController {
    
    private final BankAppClient bankAppClient;
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createInternalTransaction(@RequestParam Long senderId, @RequestParam Long receiverId, @RequestParam BigDecimal value) {
        User sender = userService.findById(senderId);
        User receiver = userService.findById(receiverId);
        bankAppClient.createInternalTransaction(sender, receiver, value);
    };

    @PostMapping("/externalPayment")
    @ResponseStatus(HttpStatus.CREATED)
    public void createExternalPayment(@RequestParam Long userId, @RequestParam BigDecimal value){
        User user = userService.findById(userId);
        if (!bankAppClient.createExternalPayment(user,value)) {
            throw new HttpClientErrorException(HttpStatus.EXPECTATION_FAILED);
        }
    };

    @PostMapping("/externalWithdrawal")
    @ResponseStatus(HttpStatus.CREATED)
    public void createExternalWithdrawal(@RequestParam Long userId, @RequestParam BigDecimal value){
        User user = userService.findById(userId);
        if (!bankAppClient.createExternalWithdrawal(user,value)) {
            throw new HttpClientErrorException(HttpStatus.EXPECTATION_FAILED);
        }
    };
}
