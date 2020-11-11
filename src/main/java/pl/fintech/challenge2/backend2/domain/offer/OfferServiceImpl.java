package pl.fintech.challenge2.backend2.domain.offer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;

    @Override
    @Transactional
    public Offer create(Offer offer) {
        return offerRepository.save(offer);
    }
}
