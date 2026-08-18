package com.greenspace.dto.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data @NoArgsConstructor @AllArgsConstructor
@Builder
public class ReservationRequest {

    @NotNull(message = "Garden ID is required")
    private Long gardenId;

    @NotNull(message = "The start date is required")
    @FutureOrPresent(message = "The start date cannot be in the past")
    private LocalDate startDate;

    @NotNull(message = "The end date is required")
    @FutureOrPresent(message = "The end date cannot be in the past")
    private LocalDate endDate;

    private String requestMessage;
}
