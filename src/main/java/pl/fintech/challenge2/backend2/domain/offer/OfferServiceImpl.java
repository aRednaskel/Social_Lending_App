package pl.fintech.challenge2.backend2.domain.offer;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import pl.fintech.challenge2.backend2.domain.inquiry.Inquiry;
import pl.fintech.challenge2.backend2.restclient.bank.BankAppClient;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;
    private final BankAppClient bankAppClient;

    @Override
    @Transactional
    public Offer create(Offer offer) {
        if (offer == null)
            throw new IllegalArgumentException();
        BigDecimal balance = bankAppClient.getAccountInfo(
                offer.getLender().getAccountNumber())
                .getAccountBalance();
        if (balance.compareTo(offer.getLoanAmount()) < 0)
            throw new InsuficientMoneyException("User with id " + offer.getLender().getId() + "does not have enough money to create an offer");
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
        if (inquiry != null && inquiry.getLoanAmount().compareTo(BigDecimal.ZERO) > 0) {
            List<Offer> offers = offerRepository.findAllByInquiry(inquiry);
            offers.sort(Comparator.comparing(Offer::getAnnualInterestRate));
            return getBestOffersThatWillCoverInquiryAmountAndChangeStatusForRejectedOffers(inquiry.getLoanAmount(), offers);
        }
        throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
    }

    private List<Offer> getBestOffersThatWillCoverInquiryAmountAndChangeStatusForRejectedOffers(BigDecimal amount, List<Offer> offers) {
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
