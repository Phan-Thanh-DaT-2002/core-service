package com.neo.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.neo.core.entities.PasswordPolicies;

public interface PasswordPoliciesRepository extends JpaRepository<PasswordPolicies, Integer> {


}
