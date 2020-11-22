package pl.fintech.challenge2.backend2.domain.offer

import org.springframework.web.client.HttpClientErrorException
import pl.fintech.challenge2.backend2.domain.inquiry.Inquiry
import spock.lang.Specification

class OfferServiceImplTest extends Specification {

    def offerRepository = Mock(OfferRepository)
    def offerService = new OfferServiceImpl(offerRepository)

    def "Save method should save and return an offer"() {
        given:
        def offer = createOffer(1)
        offerRepository.save(offer) >> offer
        when:
        offer = offerService.create(offer)
        then:
        assert offer.getLoanAmount() == BigDecimal.valueOf(101)
        assert offer.getAnnualInterestRate() == 98
    }

    def "Save method should throw excception"() {
        when:
        offerService.create(null)
        then:
        thrown(HttpClientErrorException)
    }

    def"FindById method should return an offer"() {
        given:
        def offer = createOffer(1)
        offerRepository.findById(1) >> Optional.of(offer)
        when:
        offer = offerService.findById(1)
        then:
        assert offer.getLoanAmount() == 101
        assert offer.getAnnualInterestRate() == 98

    }

    def"FindById method should throw an error"() {
        given:
        offerRepository.findById(1) >> Optional.empty()
        when:
        offerService.findById(1)
        then:
        thrown(HttpClientErrorException)
    }

    def"GetBestOffersForInquiry"() {
        def inquiry = Inquiry.builder()
                        .loanAmount(BigDecimal.valueOf(300)).build()
        offerRepository.findAllByInquiry(inquiry) >> createListOfOffers()
        when:
        def offers = offerService.getBestOffersForInquiry(inquiry)
        def sum = BigDecimal.ZERO
        for(offer in offers) {
            sum += offer.getLoanAmount()
        }
        then:
        assert sum >= 300
        assert offers.get(0).getAnnualInterestRate() <= offers.get(1).getAnnualInterestRate()

    }

    Offer createOffer(Long factor) {
        return Offer.builder()
                .id(factor)
                .loanAmount(BigDecimal.valueOf(100 + factor))
                .annualInterestRate(99-factor).build()
    }

    List<Offer> createListOfOffers() {
        def offers = new ArrayList<Offer>()
        for (i in 0..<10) {
            offers.add(createOffer(i))
        }
        return offers
    }
}
