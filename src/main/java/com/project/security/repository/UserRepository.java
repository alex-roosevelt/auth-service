package com.project.security.repository;

import com.project.security.model.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByUserName(String username);

  Optional<User> findByUserNameOrEmail(String username, String email);

  Boolean existsByUserName(String username);
  Boolean existsByEmail(String email);
}
