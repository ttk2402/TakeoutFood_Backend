package com.kientran.identity_service.repository;

import com.kientran.identity_service.entity.Account;
import com.kientran.identity_service.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    List<Account> findByRole(Role role);

    @Query(value = "select * from account where account.username = :username and account.password = :password", nativeQuery = true)
    Account findAccountByLoginDto(@Param("username") String username, @Param("password") String password);

    @Query(value = "select * from account where account.username = :username or  account.email = :email", nativeQuery = true)
    List<Account> isValidOfUsernameAndEmail(@Param("username") String username, @Param("email") String email);

    @Query(value = "select count(*) from account a, role b where b.id=a.role_id and b.role='CUSTOMER';", nativeQuery = true)
    Integer getTotalCustomer();
}
