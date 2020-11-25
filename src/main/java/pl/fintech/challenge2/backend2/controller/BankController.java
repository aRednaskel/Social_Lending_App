package pl.fintech.challenge2.backend2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.fintech.challenge2.backend2.domain.bank.BankAppClient;
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
    public void createInternalTransaction(Long senderId, Long receiverId, BigDecimal value) {
        User sender = userService.findById(senderId);
        User receiver = userService.findById(receiverId);
        bankAppClient.createInternalTransaction(sender, receiver, value);
    };

    @PostMapping("/externalPayment")
    public void createExternalPayment(Long userId, BigDecimal value){
        User user = userService.findById(userId);
        bankAppClient.createExternalPayment(user,value);
    };

    @PostMapping("/externalWithdrawal")
    public void createExternalWithdrawal(Long userId, BigDecimal value){
        User user = userService.findById(userId);
        bankAppClient.createExternalWithdrawal(user,value);
    };
}
