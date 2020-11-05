package pl.fintech.challenge2.backend2.domain.inquiry;

import org.springframework.data.jpa.repository.JpaRepository;

interface InquiryRepository extends JpaRepository<Inquiry, Long> {
}
