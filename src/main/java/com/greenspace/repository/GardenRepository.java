package com.greenspace.repository;

import com.greenspace.entity.Garden;
import com.greenspace.enums.GardenStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GardenRepository extends JpaRepository<Garden, Long> {

    List<Garden> findByOwnerId(Long ownerId);

    @Query("SELECT g FROM Garden g WHERE g.status = :status " +
            "AND LOWER(g.city) LIKE LOWER(CONCAT('%', :city, '%')) " +
            "AND g.areaSize >= :minArea")
    List<Garden> searchAvailableGardens(@Param("status") GardenStatus status,
                                        @Param("city") String city,
                                        @Param("minArea") Double minArea);
}
