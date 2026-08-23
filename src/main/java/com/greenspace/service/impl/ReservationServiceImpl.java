package com.greenspace.service.impl;

import com.greenspace.dto.request.ReservationRequest;
import com.greenspace.dto.response.ReservationResponse;
import com.greenspace.entity.Garden;
import com.greenspace.entity.Reservation;
import com.greenspace.entity.User;
import com.greenspace.enums.ReservationStatus;
import com.greenspace.mapper.ReservationMapper;
import com.greenspace.repository.GardenRepository;
import com.greenspace.repository.ReservationRepository;
import com.greenspace.repository.UserRepository;
import com.greenspace.service.ReservationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final GardenRepository gardenRepository;
    private final UserRepository userRepository;
    private final ReservationMapper reservationMapper;

    @Override
    public ReservationResponse createReservation(ReservationRequest request, Long gardenerId) {
        Garden garden = gardenRepository.findById(request.getGardenId())
                .orElseThrow(() -> new EntityNotFoundException("Garden not found"));
        User gardener = userRepository.findById(gardenerId)
                .orElseThrow(() -> new EntityNotFoundException("Garden not found"));

        if (reservationRepository.existsActiveReservation(garden.getId(), gardenerId, List.of(ReservationStatus.PENDING, ReservationStatus.ACCEPTED))) {
            throw new IllegalArgumentException("You already have an active or pending reservation for this field.");
        }

        Reservation reservation = reservationMapper.toEntity(request);
        reservation.setGarden(garden);
        reservation.setGardener(gardener);
        reservation.setStatus(ReservationStatus.PENDING);

        return reservationMapper.toResponse(reservationRepository.save(reservation));
    }

    @Override
    public ReservationResponse updateReservationStatus(Long reservationId, Long ownerId, ReservationStatus status) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new EntityNotFoundException("Reservation not found"));

        if (!reservation.getGarden().getOwner().getId().equals(ownerId)) {
            throw new IllegalArgumentException("Action not authorized.");
        }

        reservation.setStatus(status);
        return reservationMapper.toResponse(reservationRepository.save(reservation));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ReservationResponse> getReservationsByGardener(Long gardenerId, Pageable pageable) {
        return reservationRepository.findByGardenerId(gardenerId, pageable)
                .map(reservationMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ReservationResponse> getReservationRequestsForOwner(Long ownerId, Pageable pageable) {
        return reservationRepository.findRequestsForOwner(ownerId, pageable)
                .map(reservationMapper::toResponse);
    }
}