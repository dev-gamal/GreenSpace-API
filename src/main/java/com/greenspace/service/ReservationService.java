package com.greenspace.service;

import com.greenspace.dto.request.ReservationRequest;
import com.greenspace.dto.response.ReservationResponse;
import com.greenspace.enums.ReservationStatus;

import java.util.List;

public interface ReservationService {
    ReservationResponse createReservation(ReservationRequest request, Long gardenerId);
    ReservationResponse updateReservationStatus(Long reservationId, Long ownerId, ReservationStatus status);
    List<ReservationResponse> getReservationsByGardener(Long gardenerId);
    List<ReservationResponse> getReservationRequestsForOwner(Long ownerId);
}