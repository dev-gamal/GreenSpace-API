package com.greenspace.dto.response;

import com.greenspace.enums.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data @NoArgsConstructor @AllArgsConstructor
@Builder
public class ReservationResponse {
    private Long id;
    private Long gardenId;
    private String gardenTitle;
    private Long gardenerId;
    private String gardenerName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String requestMessage;
    private ReservationStatus status;
    private LocalDateTime createdAt;
}