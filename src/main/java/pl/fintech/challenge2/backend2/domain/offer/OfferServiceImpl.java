package pl.fintech.challenge2.backend2.domain.offer;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import pl.fintech.challenge2.backend2.domain.inquiry.Inquiry;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;

    @Override
    @Transactional
    public Offer create(Offer offer) {
        if (offer == null)
            throw new HttpClientErrorException(HttpStatus.NO_CONTENT);
        offer.setOfferStatus(OfferStatus.CREATED);
        return offerRepository.save(offer);
    }

    @Override
    public Offer findById(Long offerId) {
        return offerRepository.findById(offerId)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }

    @Override
    public List<Offer> getBestOffersForInquiry(Inquiry inquiry) {
        List<Offer> offers = offerRepository.findAllByInquiry(inquiry);
        offers.sort(Comparator.comparing(Offer::getAnnualInterestRate));
        if (inquiry != null && inquiry.getLoanAmount().compareTo(BigDecimal.ZERO) > 0) {
            return getBestOffersThatWillCoverInquiryAmount(inquiry.getLoanAmount(), offers);
        }
        throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
    }

    private List<Offer> getBestOffersThatWillCoverInquiryAmount(BigDecimal amount, List<Offer> offers) {
        List<Offer> returnOffers = new ArrayList<>();
        for (Offer offer : offers) {
            if (amount.compareTo(BigDecimal.ZERO) > 0) {
                amount = amount.subtract(offer.getLoanAmount());
                returnOffers.add(offer);
                offer.setOfferStatus(OfferStatus.PENDING);
            } else {
                offer.setOfferStatus(OfferStatus.REJECTED);
            }
        }
        return returnOffers;
    }
}
