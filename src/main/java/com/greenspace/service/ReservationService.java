package com.greenspace.service;

import com.greenspace.dto.request.ReservationRequest;
import com.greenspace.dto.response.ReservationResponse;
import com.greenspace.enums.ReservationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReservationService {
    ReservationResponse createReservation(ReservationRequest request, Long gardenerId);
    ReservationResponse updateReservationStatus(Long reservationId, Long ownerId, ReservationStatus status);
    Page<ReservationResponse> getReservationsByGardener(Long gardenerId, Pageable pageable);
    Page<ReservationResponse> getReservationRequestsForOwner(Long ownerId, Pageable pageable);
}