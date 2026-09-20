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
    Page<GardenResponse> getAllGardens(Pageable pageable);
    GardenResponse updateGardenStatus(Long id, GardenStatus status);
    GardenResponse updateGarden(Long id, GardenRequest request, List<String> photoUrls, Long callerId, boolean isAdmin);
    void deleteGarden(Long id, Long callerId, boolean isAdmin);
}
