package com.kientran.identity_service.repository;

import com.kientran.identity_service.entity.AccountStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountStatusRepository extends JpaRepository<AccountStatus, Integer> {
    @Query(value = "select * from account_status where status = :status", nativeQuery = true)
    AccountStatus findAccountStatusByStatus(@Param("status") String status);
}
