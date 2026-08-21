package com.greenspace.service.impl;

import com.greenspace.dto.request.GardenRequest;
import com.greenspace.dto.response.GardenResponse;
import com.greenspace.entity.Garden;
import com.greenspace.entity.GardenPhoto;
import com.greenspace.entity.User;
import com.greenspace.enums.GardenStatus;
import com.greenspace.mapper.GardenMapper;
import com.greenspace.repository.GardenRepository;
import com.greenspace.repository.UserRepository;
import com.greenspace.service.GardenService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class GardenServiceImpl implements GardenService {

    private final GardenRepository gardenRepository;
    private final UserRepository userRepository;
    private final GardenMapper gardenMapper;

    @Override
    public GardenResponse createGarden(GardenRequest request, Long ownerId, List<String> photoUrls) {
        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new EntityNotFoundException("Owner not found"));

        Garden garden = gardenMapper.toEntity(request);
        garden.setOwner(owner);
        garden.setStatus(GardenStatus.AVAILABLE);

        if (photoUrls != null && !photoUrls.isEmpty()) {
            List<GardenPhoto> photos = photoUrls.stream()
                    .map(url -> GardenPhoto.builder().photoUrl(url).garden(garden).build())
                    .toList();
            garden.setPhotos(photos);
        }

        Garden savedGarden = gardenRepository.save(garden);
        return gardenMapper.toResponse(savedGarden);
    }

    @Override
    @Transactional
    public GardenResponse getGardenById(Long id) {
        return gardenRepository.findById(id)
                .map(gardenMapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Garden not found"));
    }

    @Override
    @Transactional
    public List<GardenResponse> getGardensByOwner(Long ownerId) {
        return gardenRepository.findByOwnerId(ownerId).stream()
                .map(gardenMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public List<GardenResponse> searchAvailableGardens(String city, Double minArea) {
        return gardenRepository.searchAvailableGardens(GardenStatus.AVAILABLE, city, minArea).stream()
                .map(gardenMapper::toResponse)
                .toList();
    }

    @Override
    public GardenResponse updateGardenStatus(Long id, GardenStatus status) {
        Garden garden = gardenRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Garden not found"));
        garden.setStatus(status);
        return gardenMapper.toResponse(gardenRepository.save(garden));
    }

    @Override
    public void deleteGarden(Long id, Long ownerId) {
        Garden garden = gardenRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Garden not found"));

        if (!garden.getOwner().getId().equals(ownerId)) {
            throw new IllegalArgumentException("You're not allowed to delete this garden");
        }
        gardenRepository.delete(garden);
    }
}