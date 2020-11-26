package pl.fintech.challenge2.backend2.domain.offer;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import pl.fintech.challenge2.backend2.domain.Status;
import pl.fintech.challenge2.backend2.domain.bank.BankAppClient;
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
    private final BankAppClient bankAppClient;

    @Override
    @Transactional
    public Offer create(Offer offer) {
        if (offer == null)
            throw new HttpClientErrorException(HttpStatus.NO_CONTENT);
        BigDecimal balance = bankAppClient.getAccountInfo(
                offer.getLender().getAccountNumber())
                .getAccountBalance();
        if (balance.compareTo(offer.getLoanAmount()) < 0)
            throw new InsuficientMoneyException("User with id " + offer.getLender().getId() + "does not have enough money to create an offer");
        offer.setStatus(Status.CREATED);
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
            return getBestOffersThatWillCoverInquiryAmountAndChangeStatusForRejectedOffers(inquiry.getLoanAmount(), offers);
        }
        throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
    }

    private List<Offer> getBestOffersThatWillCoverInquiryAmountAndChangeStatusForRejectedOffers(BigDecimal amount, List<Offer> offers) {
        List<Offer> returnOffers = new ArrayList<>();
        for (Offer offer : offers) {
            if (amount.compareTo(offer.getLoanAmount()) >= 0) {
                amount = amount.subtract(offer.getLoanAmount());
                offer.setLoanAmountGiven(offer.getLoanAmount());
                returnOffers.add(offer);
                offer.setStatus(Status.PENDING);
            } else if (amount.compareTo(BigDecimal.ZERO) > 0) {
                offer.setLoanAmountGiven(amount);
                amount = amount.subtract(offer.getLoanAmount());
                offer.setStatus(Status.PENDING);
                returnOffers.add(offer);
            } else {
                offer.setStatus(Status.REJECTED);
            }
        }
        return returnOffers;
    }
}
