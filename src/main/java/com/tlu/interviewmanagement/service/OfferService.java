package com.tlu.interviewmanagement.service;

import com.tlu.interviewmanagement.entities.Offer;
import com.tlu.interviewmanagement.web.request.OfferRequest;
import com.tlu.interviewmanagement.web.request.SearchRequest;
import jakarta.mail.MessagingException;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface OfferService {
    Page<Offer> findAllOffer(SearchRequest searchRequest);
    void deleteOffer(Long id);
    List<Offer> findAllOfferByDate(LocalDate fromDate, LocalDate toDate);

    Offer saveOffer(OfferRequest offerRequest) throws MessagingException;

    Offer findOfferById(Long id);

    void approveOffer(Long id, String notes);
    void rejectOffer(Long id, String notes);
    Offer updateOffer(OfferRequest offerRequest);
}
