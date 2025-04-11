package com.example.test_task.persistence.repo;

import com.example.test_task.persistence.entity.EmailDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface EmailDataRepository extends JpaRepository<EmailDataEntity, Long> {
    @Query("SELECT e.email FROM EmailDataEntity e WHERE e.email IN :emailList")
    List<String> findExistingEmails(@Param("emailList") List<String> emailList);

    void deleteByUserIdAndEmailIn(Long userId, List<String> emailList);

    List<EmailDataEntity> findAllByUserIdAndEmailIn(Long userId, List<String> emailList);

    List<EmailDataEntity> findAllByUserIdIn(Set<Long> userIdList);

    long countAllByUserId(Long userId);
}
