package pl.fintech.challenge2.backend2.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import pl.fintech.challenge2.backend2.domain.bank.BankAppClient;
import pl.fintech.challenge2.backend2.domain.inquiry.Inquiry;
import pl.fintech.challenge2.backend2.domain.inquiry.InquiryService;
import pl.fintech.challenge2.backend2.domain.loan.Loan;
import pl.fintech.challenge2.backend2.domain.loan.LoanService;
import pl.fintech.challenge2.backend2.domain.offer.Offer;
import pl.fintech.challenge2.backend2.domain.offer.OfferService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class Scheduler {

    private final LoanService loanService;
    private final OfferService offerService;
    private final InquiryService inquiryService;
    private final BankAppClient bankAppClient;

    @Scheduled(cron = "${scheduler.monthly.update}")
    public void updateLoanAmount() {
        List<Loan> loans = loanService.getAll();
        BigDecimal monthlyInterestRate;
        for (Loan loan : loans) {
            monthlyInterestRate = BigDecimal.valueOf(1 + loan.getAnnualInterestRate() / 12);
            loan.setLoanAmount(
                    loan.getLoanAmount()
                            .multiply(monthlyInterestRate));
            bankAppClient.createInternalTransaction(loan.getBorrower(), loan.getLender(), loan.getMonthlyInstallment());
        }

    }

    @Scheduled(cron = "${scheduler.daily.update}")
    public void everyDayTask() {
        List<Inquiry> inquiries = inquiryService.findBySubmissionDeadLine(LocalDate.now());
        List<Offer> offers;
        for (Inquiry inquiry: inquiries) {
            offers = offerService.getBestOffersForInquiry(inquiry);
        }
    }
}
