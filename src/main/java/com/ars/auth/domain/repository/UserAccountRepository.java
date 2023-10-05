package com.ars.auth.domain.repository;

import com.ars.auth.domain.entity.UserAccount;
import com.ars.auth.domain.entity.UserAccountType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Integer> {

  List<UserAccount> findAllByType(UserAccountType type);

}
