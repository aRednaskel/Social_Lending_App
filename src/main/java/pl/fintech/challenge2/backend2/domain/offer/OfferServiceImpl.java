package pl.fintech.challenge2.backend2.domain.offer;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
class OfferServiceImpl implements OfferService {

    private OfferRepository offerRepository;

    @Override
    @Transactional
    public Offer create(Offer offer) {
        return offerRepository.save(offer);
    }
}
