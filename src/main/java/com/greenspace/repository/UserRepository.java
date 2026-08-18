package com.greenspace.repository;

import com.greenspace.entity.User;
import com.greenspace.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.role = :role AND LOWER(u.city) LIKE LOWER(CONCAT('%', :city, '%'))")
    List<User> findByRoleAndCity(@Param("role") Role role,
                                 @Param("city") String city);
}
