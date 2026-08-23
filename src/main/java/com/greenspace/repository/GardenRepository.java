package com.greenspace.repository;

import com.greenspace.entity.Garden;
import com.greenspace.enums.GardenStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GardenRepository extends JpaRepository<Garden, Long> {

    Page<Garden> findByOwnerId(Long ownerId, Pageable pageable);

    @Query("SELECT g FROM Garden g WHERE g.status = :status " +
            "AND LOWER(g.city) LIKE LOWER(CONCAT('%', :city, '%')) " +
            "AND g.areaSize >= :minArea")
    Page<Garden> searchAvailableGardens(@Param("status") GardenStatus status,
                                        @Param("city") String city,
                                        @Param("minArea") Double minArea,
                                        Pageable pageable);
}
