package pl.fintech.challenge2.backend2.domain.offer

import spock.lang.Specification

class OfferServiceImplTest extends Specification {

    def offerRepository = Mock(OfferRepository)
    def offerService = new OfferServiceImpl(offerRepository)

    def "Save method should save and return an offer"() {
        given:
        Offer offer = Offer.builder()
                .loanAmount(BigDecimal.TEN)
                .annualInterestRate(5).build()
        offerRepository.save(offer) >> offer
        when:
        offer = offerService.create(offer)
        then:
        assert offer.getLoanAmount() == BigDecimal.TEN
        assert offer.getAnnualInterestRate() == 5
    }
}
