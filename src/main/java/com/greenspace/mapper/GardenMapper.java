package com.greenspace.mapper;

import com.greenspace.dto.request.GardenRequest;
import com.greenspace.dto.response.GardenResponse;
import com.greenspace.entity.Garden;
import com.greenspace.entity.GardenPhoto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring")
public interface GardenMapper {

    @Mapping(source = "owner.id", target = "ownerId")
    @Mapping(target = "ownerName", expression = "java(garden.getOwner() != null ? garden.getOwner().getFirstName() + \" \" + garden.getOwner().getLastName() : null)")
    @Mapping(target = "photoUrls", expression = "java(mapPhotos(garden.getPhotos()))")
    GardenResponse toResponse(Garden garden);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "photos", ignore = true)
    @Mapping(target = "reservations", ignore = true)
    Garden toEntity(GardenRequest request);

    default List<String> mapPhotos(List<GardenPhoto> photos) {
        if (photos == null || photos.isEmpty()) {
            return Collections.emptyList();
        }
        return photos.stream()
                .map(GardenPhoto::getPhotoUrl)
                .toList();
    }
}
