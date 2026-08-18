package com.greenspace.dto.response;

import com.greenspace.enums.GardenStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor
@Builder
public class GardenResponse {
    private Long id;
    private Long ownerId;
    private String ownerName;
    private String title;
    private String description;
    private Double areaSize;
    private Double latitude;
    private Double longitude;
    private String address;
    private String city;
    private String postalCode;
    private String rules;
    private Boolean hasTools;
    private GardenStatus status;
    private List<String> photoUrls;
    private LocalDateTime createdAt;
}