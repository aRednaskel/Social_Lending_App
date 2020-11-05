package pl.fintech.challenge2.backend2.domain.offer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface OfferRepository extends JpaRepository<Offer, Long> {
}
