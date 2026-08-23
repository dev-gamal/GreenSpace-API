package com.greenspace.service;

import com.greenspace.dto.request.GardenRequest;
import com.greenspace.dto.response.GardenResponse;
import com.greenspace.enums.GardenStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GardenService {
    GardenResponse createGarden(GardenRequest request, Long ownerId, List<String> photoUrls);
    GardenResponse getGardenById(Long id);
    Page<GardenResponse> getGardensByOwner(Long ownerId, Pageable pageable);
    Page<GardenResponse> searchAvailableGardens(String city, Double minArea, Pageable pageable);
    GardenResponse updateGardenStatus(Long id, GardenStatus status);
    void deleteGarden(Long id, Long ownerId);
}
