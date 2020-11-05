package pl.fintech.challenge2.backend2.domain.offer;

import org.springframework.stereotype.Service;

@Service
class OfferServiceImpl implements OfferService {

    private OfferRepository offerRepository;

    @Override
    public Offer create(Offer offer) {
        return offerRepository.save(offer);
    }
}
