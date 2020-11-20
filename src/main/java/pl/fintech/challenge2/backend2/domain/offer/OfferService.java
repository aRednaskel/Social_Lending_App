package pl.fintech.challenge2.backend2.domain.offer;

import pl.fintech.challenge2.backend2.domain.inquiry.Inquiry;

import java.util.List;

public interface OfferService {

    Offer create(Offer offer);

    Offer findById(Long offerId);

    List<Offer> getBestOffersForInquiry(Inquiry inquiry);
}
