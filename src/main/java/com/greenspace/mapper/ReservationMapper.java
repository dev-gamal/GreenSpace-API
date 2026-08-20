package com.greenspace.mapper;

import com.greenspace.dto.request.ReservationRequest;
import com.greenspace.dto.response.ReservationResponse;
import com.greenspace.entity.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReservationMapper {

    @Mapping(source = "garden.id", target = "gardenId")
    @Mapping(source = "garden.title", target = "gardenTitle")
    @Mapping(source = "gardener.id", target = "gardenerId")
    @Mapping(target = "gardenerName", expression = "java(reservation.getGardener() != null ? reservation.getGardener().getFirstName() + \" \" + reservation.getGardener().getLastName() : null)")
    ReservationResponse toResponse(Reservation reservation);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "garden", ignore = true)
    @Mapping(target = "gardener", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Reservation toEntity(ReservationRequest request);
}
