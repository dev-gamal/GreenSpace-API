package com.greenspace.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
@Builder
public class AdminStateResponse {
    private long totalUsers;
    private long totalGardens;
    private long totalProduct;
    private long activeReservations;
}
