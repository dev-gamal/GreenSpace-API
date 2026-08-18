package com.greenspace.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
@Builder
public class GardenRequest {

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    @NotNull(message = "Area size is required")
    @Positive(message = "Area size must be a positive number")
    private Double areaSize;

    private Double latitude;
    private Double longitude;

    private String address;

    @NotBlank(message = "City is required")
    private String city;

    private String postalCode;
    private String rules;

    private Boolean hasTools;
}
