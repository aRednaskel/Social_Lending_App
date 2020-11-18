package pl.fintech.challenge2.backend2.domain.offer;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

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

    @Override
    public Offer findById(Long offerId) {
        return offerRepository.findById(offerId)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }
}
