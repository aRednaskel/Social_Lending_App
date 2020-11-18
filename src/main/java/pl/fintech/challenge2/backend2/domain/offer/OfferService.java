package pl.fintech.challenge2.backend2.domain.offer;

public interface OfferService {

    Offer create(Offer offer);

    Offer findById(Long offerId);
}
