package pl.fintech.challenge2.backend2.controller.mapper;

import org.springframework.stereotype.Component;
import pl.fintech.challenge2.backend2.controller.dto.InquiryDTO;
import pl.fintech.challenge2.backend2.domain.inquiry.Inquiry;

import java.time.LocalDate;

@Component
public class InquiryMapper {

    public Inquiry mapInquiryDTOToInquiry(InquiryDTO inquiryDTO) {
        return Inquiry.builder()
                .loanAmount(inquiryDTO.getLoanAmount())
                .loanDuration(inquiryDTO.getLoanDuration())
                .submissionDeadline(inquiryDTO.getSubmissionDeadline())
                .createdAt(LocalDate.now())
                .build();
    }
}
