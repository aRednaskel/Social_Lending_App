package pl.fintech.challenge2.backend2.controller.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.fintech.challenge2.backend2.controller.dto.OfferDTO;
import pl.fintech.challenge2.backend2.domain.inquiry.Inquiry;
import pl.fintech.challenge2.backend2.domain.loan.Loan;
import pl.fintech.challenge2.backend2.domain.offer.Offer;
import pl.fintech.challenge2.backend2.domain.user.User;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class OfferMapper {

    public Offer mapOfferDTOToOffer(OfferDTO offerDTO, User user, Inquiry inquiry) {
        return Offer.builder()
                .lender(user)
                .inquiry(inquiry)
                .loanAmount(offerDTO.getProposedAmount())
                .annualInterestRate(offerDTO.getAnnualInterestRate())
                .paymentFrequency(offerDTO.getPaymentFrequency())
                .build();
    }

    public Loan mapOfferAndInquiryToLoan(Offer offer, Inquiry inquiry) {
        return Loan.builder()
                .borrower(inquiry.getBorrower())
                .lender(offer.getLender())
                .loanAmount(offer.getLoanAmount())
                .loanDuration(inquiry.getLoanDuration())
                .annualInterestRate(offer.getAnnualInterestRate())
                .paymentFrequency(offer.getPaymentFrequency())
                .createdAt(LocalDate.now()).build();
    }

}
