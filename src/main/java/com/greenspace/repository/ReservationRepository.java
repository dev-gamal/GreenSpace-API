package com.greenspace.repository;

import com.greenspace.entity.Reservation;
import com.greenspace.enums.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByGardenerId(Long gardenerId);

    @Query("SELECT r FROM Reservation r JOIN r.garden g WHERE g.owner.id = :ownerId ORDER BY r.createdAt DESC")
    List<Reservation> findRequestsForOwner(@Param("ownerId") Long ownerId);

    @Query("SELECT COUNT(r) > 0 FROM Reservation r WHERE r.garden.id = :gardenId " +
            "AND r.gardener.id = :gardenerId AND r.status IN (:statuses)")
    boolean existsActiveReservation(@Param("gardenId") Long gardenId,
                                    @Param("gardenerId") Long gardenerId,
                                    @Param("statuses") List<ReservationStatus> statuses);
}
