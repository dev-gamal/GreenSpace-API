package com.greenspace.controller;

import com.greenspace.dto.request.ReservationRequest;
import com.greenspace.dto.response.ReservationResponse;
import com.greenspace.enums.ReservationStatus;
import com.greenspace.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    @PreAuthorize("hasAuthority('GARDENER')")
    public ResponseEntity<ReservationResponse> createReservation(
            @Valid @RequestBody ReservationRequest request,
            @RequestParam Long gardenerId) {

        ReservationResponse response = reservationService.createReservation(request, gardenerId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasAuthority('OWNER')")
    public ResponseEntity<ReservationResponse> updateStatus(
            @PathVariable Long id,
            @RequestParam Long ownerId,
            @RequestParam ReservationStatus status) {

        return ResponseEntity.ok(reservationService.updateReservationStatus(id, ownerId, status));
    }

    @GetMapping("/gardener/{gardenerId}")
    @PreAuthorize("hasAuthority('GARDENER')")
    public ResponseEntity<Page<ReservationResponse>> getGardenerReservations(
            @PathVariable Long gardenerId,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        return ResponseEntity.ok(reservationService.getReservationsByGardener(gardenerId, pageable));
    }

    @GetMapping("/owner/{ownerId}/requests")
    @PreAuthorize("hasAuthority('OWNER')")
    public ResponseEntity<Page<ReservationResponse>> getOwnerRequests(
            @PathVariable Long ownerId,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        return ResponseEntity.ok(reservationService.getReservationRequestsForOwner(ownerId, pageable));
    }
}