package com.example.test_task.persistence.repo;

import com.example.test_task.persistence.entity.PhoneDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface PhoneDataRepository extends JpaRepository<PhoneDataEntity, Long> {
    @Query("SELECT p.phone FROM PhoneDataEntity p WHERE p.phone IN :phoneList")
    List<String> findExistingPhones(@Param("phoneList") List<String> phoneList);

    void deleteByUserIdAndPhoneIn(Long userId, List<String> phoneList);

    List<PhoneDataEntity> findAllByUserIdAndPhoneIn(Long userId, List<String> phoneList);

    List<PhoneDataEntity> findAllByUserIdIn(Set<Long> userIdList);

    long countAllByUserId(Long userId);
}