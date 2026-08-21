package com.greenspace.service;

import com.greenspace.dto.request.GardenRequest;
import com.greenspace.dto.response.GardenResponse;
import com.greenspace.enums.GardenStatus;

import java.util.List;

public interface GardenService {
    GardenResponse createGarden(GardenRequest request, Long ownerId, List<String> photoUrls);
    GardenResponse getGardenById(Long id);
    List<GardenResponse> getGardensByOwner(Long ownerId);
    List<GardenResponse> searchAvailableGardens(String city, Double minArea);
    GardenResponse updateGardenStatus(Long id, GardenStatus status);
    void deleteGarden(Long id, Long ownerId);
}
