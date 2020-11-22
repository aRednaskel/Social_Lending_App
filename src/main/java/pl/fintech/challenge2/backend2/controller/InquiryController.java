package pl.fintech.challenge2.backend2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.fintech.challenge2.backend2.controller.dto.InquiryDTO;
import pl.fintech.challenge2.backend2.controller.mapper.InquiryMapper;
import pl.fintech.challenge2.backend2.domain.inquiry.Inquiry;
import pl.fintech.challenge2.backend2.domain.inquiry.InquiryService;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/inquiries")
@RequiredArgsConstructor
public class InquiryController {

    private final InquiryService inquiryService;
    private final InquiryMapper inquiryMapper = new InquiryMapper();

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody InquiryDTO inquiryDTO) {
        inquiryService.create(inquiryMapper.mapInquiryDTOToInquiry(inquiryDTO));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Inquiry> getInquiries(@RequestParam(required = false, defaultValue = "0") Integer minLoanDuration,
                                      @RequestParam(required = false, defaultValue = "420") Integer maxLoanDuration,
                                      @RequestParam(required = false, defaultValue = "0") BigDecimal minAmount,
                                      @RequestParam(required = false, defaultValue = "9999999999999999999") BigDecimal maxAmount) {
        return inquiryService.findAllByAmountAndLoanDuration(minLoanDuration, maxLoanDuration, minAmount, maxAmount);
    }
}
