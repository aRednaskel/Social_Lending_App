package pl.fintech.challenge2.backend2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.fintech.challenge2.backend2.controller.dto.OfferDTO;
import pl.fintech.challenge2.backend2.controller.mapper.OfferMapper;
import pl.fintech.challenge2.backend2.domain.inquiry.Inquiry;
import pl.fintech.challenge2.backend2.domain.inquiry.InquiryService;
import pl.fintech.challenge2.backend2.domain.loan.LoanService;
import pl.fintech.challenge2.backend2.domain.offer.Offer;
import pl.fintech.challenge2.backend2.domain.offer.OfferService;
import pl.fintech.challenge2.backend2.domain.user.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/offers")
@RequiredArgsConstructor
public class OfferController {

    private final OfferService offerService;
    private final OfferMapper offerMapper;
    private final UserService userService;
    private final InquiryService inquiryService;
    private final LoanService loanService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody OfferDTO offerDTO) {

        offerService.create(offerMapper.mapOfferDTOToOffer(offerDTO,
                userService.findById(offerDTO.getLenderId()),
                inquiryService.findById(offerDTO.getInquiryId())));
    }

    @PostMapping("/accept")
    @ResponseStatus(HttpStatus.CREATED)
    public void acceptOffer(@RequestParam Long offerId, @RequestParam Long inquiryId){
        Offer offer = offerService.findById(offerId);
        Inquiry inquiry = inquiryService.findById(inquiryId);
        loanService.create(offerMapper.mapOfferAndInquiryToLoan(offer, inquiry));
    }
}
