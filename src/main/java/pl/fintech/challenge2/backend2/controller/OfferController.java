package pl.fintech.challenge2.backend2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.fintech.challenge2.backend2.controller.dto.OfferDTO;
import pl.fintech.challenge2.backend2.controller.mapper.OfferMapper;
import pl.fintech.challenge2.backend2.domain.offer.OfferService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/offers")
@RequiredArgsConstructor
public class OfferController {

    private final OfferService offerService;
    private final OfferMapper offerMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody OfferDTO offerDTO) {
        offerService.create(offerMapper.mapInquiryDTOToInquiry(offerDTO));
    }
}
