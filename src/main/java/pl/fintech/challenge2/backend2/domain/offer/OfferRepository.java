package pl.fintech.challenge2.backend2.domain.offer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.fintech.challenge2.backend2.domain.inquiry.Inquiry;

import java.util.List;

@Repository
interface OfferRepository extends JpaRepository<Offer, Long> {

    List<Offer> findAllByInquiry(Inquiry inquiry);
}
