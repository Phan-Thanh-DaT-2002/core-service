package com.neo.core.service;

import org.springframework.stereotype.Service;

import com.neo.core.entities.PasswordPolicies;


@Service
public interface PasswordPoliciesService extends IRootService<PasswordPolicies>{

	PasswordPolicies getPasswordPolicy();
}
