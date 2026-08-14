package com.greenspace.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "garden_photos")
@Data @NoArgsConstructor @AllArgsConstructor
@Builder
public class GardenPhoto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "garden_id", nullable = false)
    private Garden garden;

    @Column(name = "photo_url", nullable = false)
    private String photoUrl;
}