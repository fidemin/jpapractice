package org.yunhongmin.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.yunhongmin.shop.domain.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByName(String name);
}
