package pl.fintech.challenge2.backend2.controller.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.fintech.challenge2.backend2.controller.dto.OfferDTO;
import pl.fintech.challenge2.backend2.domain.inquiry.InquiryService;
import pl.fintech.challenge2.backend2.domain.offer.Offer;
import pl.fintech.challenge2.backend2.domain.user.UserService;

@Component
@RequiredArgsConstructor
public class OfferMapper {

    private final UserService userService;
    private final InquiryService inquiryService;

    public Offer mapInquiryDTOToInquiry(OfferDTO offerDTO) {
        return Offer.builder()
                .lender(userService.findById(offerDTO.getLenderId()))
                .inquiry(inquiryService.findById(offerDTO.getInquiryId()))
                .loanAmount(offerDTO.getProposedAmount())
                .annualInterestRate(offerDTO.getAnnualInterestRate())
                .paymentFrequency(offerDTO.getPaymentFrequency())
                .build();
    }

}
