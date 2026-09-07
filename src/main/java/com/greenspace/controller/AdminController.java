package com.greenspace.controller;

import com.greenspace.dto.response.AdminStateResponse;
import com.greenspace.dto.response.UserResponse;
import com.greenspace.enums.GardenStatus;
import com.greenspace.enums.Role;
import com.greenspace.repository.GardenRepository;
import com.greenspace.repository.ProductRepository;
import com.greenspace.repository.ReservationRepository;
import com.greenspace.repository.UserRepository;
import com.greenspace.service.GardenService;
import com.greenspace.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final GardenRepository gardenRepository;
    private final ReservationRepository reservationRepository;

    private final UserService userService;
    private final GardenService gardenService;

    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/users/search")
    public ResponseEntity<List<UserResponse>> getUsersByRoleAndCity(@RequestParam String role, @RequestParam String city) {
        return ResponseEntity.ok(userService.getUsersByRoleAndCity(role.equalsIgnoreCase("ADMIN") ? null : role.equalsIgnoreCase("OWNER") ? Role.OWNER : null, city));
    }

    @GetMapping("/stats")
    public ResponseEntity<AdminStateResponse> getDashboard() {
        AdminStateResponse stats = AdminStateResponse.builder()
                .totalUsers(userRepository.count())
                .totalGardens(gardenRepository.count())
                .totalProduct(productRepository.count())
                .activeReservations(reservationRepository.count())
                .build();

        return ResponseEntity.ok(stats);
    }

    @PutMapping("/users/{id}/toggle-block")
    public ResponseEntity<Void> toggleUserBlock(@PathVariable Long id) {
        userService.blockedUser(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/gardens/{id}/status")
    public ResponseEntity<Void> updateGardenStatus(@PathVariable Long id, @RequestParam GardenStatus status) {
        gardenService.updateGardenStatus(id, status);
        return ResponseEntity.ok().build();
    }
}
