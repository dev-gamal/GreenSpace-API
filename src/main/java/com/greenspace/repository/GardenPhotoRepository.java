package com.greenspace.repository;

import com.greenspace.entity.GardenPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GardenPhotoRepository extends JpaRepository<GardenPhoto, Long> {

    List<GardenPhoto> findByGardenId(Long gardenId);
}
