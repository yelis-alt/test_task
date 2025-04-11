package com.example.test_task.persistence.repo;

import com.example.test_task.dto.projection.UserProjection;
import com.example.test_task.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query(value =
            "SELECT u.id, u.name, u.date_of_birth " +
            "FROM business.users u " +
            "JOIN business.phone_data p ON u.id = p.user_id " +
            "JOIN business.email_data e ON u.id = e.user_id " +
            "WHERE (:dateOfBirth IS NULL OR u.date_of_birth > :dateOfBirth) " +
            "AND (:phone IS NULL OR p.phone = :phone) " +
            "AND (:name IS NULL OR u.name LIKE CONCAT(:name, '%')) " +
            "AND (:email IS NULL OR e.email = :email) " +
            "ORDER BY u.name", nativeQuery = true)
    List<UserProjection> findWithFilters(@Param("dateOfBirth") LocalDate dateOfBirth,
                                         @Param("phone") String phone,
                                         @Param("name") String name,
                                         @Param("email") String email);

    @Query(value =
            "SELECT u.id, u.name, u.date_of_birth, u.password " +
            "FROM business.users u " +
            "JOIN business.phone_data p ON u.id = p.user_id " +
            "JOIN business.email_data e ON u.id = e.user_id " +
            "WHERE (:phone IS NULL OR p.phone = :phone) " +
            "AND (:email IS NULL OR e.email = :email)",
            nativeQuery = true)
    Optional<UserEntity> findByCredentials(@Param("phone") String phone,
                                           @Param("email") String email);
}
