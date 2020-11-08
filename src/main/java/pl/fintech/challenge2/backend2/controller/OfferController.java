package pl.fintech.challenge2.backend2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.fintech.challenge2.backend2.controller.dto.OfferDTO;
import pl.fintech.challenge2.backend2.controller.mapper.OfferMapper;
import pl.fintech.challenge2.backend2.domain.inquiry.InquiryService;
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody OfferDTO offerDTO) {

        offerService.create(offerMapper.mapOfferDTOToOffer(offerDTO,
                userService.findById(offerDTO.getLenderId()),
                inquiryService.findById(offerDTO.getInquiryId())));
    }
}
