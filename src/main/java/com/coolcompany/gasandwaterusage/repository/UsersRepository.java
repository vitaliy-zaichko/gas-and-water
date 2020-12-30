package com.coolcompany.gasandwaterusage.repository;

import com.coolcompany.gasandwaterusage.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<User, String> {
}
