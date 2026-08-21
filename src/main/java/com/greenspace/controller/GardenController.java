package com.greenspace.controller;

import com.greenspace.dto.request.GardenRequest;
import com.greenspace.dto.response.GardenResponse;
import com.greenspace.enums.GardenStatus;
import com.greenspace.service.GardenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/garden")
@RequiredArgsConstructor
public class GardenController {

    private final GardenService gardenService;

    @PostMapping
    public ResponseEntity<GardenResponse> createGarden(
            @Valid @RequestBody GardenRequest request,
            @RequestParam Long ownerId,
            @RequestParam List<String> photoUrls) {

        GardenResponse response = gardenService.createGarden(request, ownerId, photoUrls);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GardenResponse> getGarden(@PathVariable Long id) {
        return ResponseEntity.ok(gardenService.getGardenById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<GardenResponse>> searchGardens(
            @RequestParam String city,
            @RequestParam Double minArea,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(gardenService.searchAvailableGardens(city, minArea, pageable));
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<Page<GardenResponse>> getGardenByOwner(
            @PathVariable Long ownerId,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(gardenService.getGardensByOwner(ownerId, pageable));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<GardenResponse> updateStatus(
            @PathVariable Long id,
            @RequestParam GardenStatus status) {
        return ResponseEntity.ok(gardenService.updateGardenStatus(id, status));
    }

    @DeleteMapping("/#{id}")
    public ResponseEntity<Void> deleteGarden(
            @PathVariable Long id,
            @RequestParam Long ownerId) {
        gardenService.deleteGarden(id, ownerId);
        return ResponseEntity.noContent().build();
    }
}
