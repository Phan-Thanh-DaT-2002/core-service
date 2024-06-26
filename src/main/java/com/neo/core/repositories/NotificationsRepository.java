package com.neo.core.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.neo.core.entities.Notifications;

@Transactional
public interface NotificationsRepository extends JpaRepository<Notifications, Integer> {
	@Query(value = "SELECT n FROM Notifications n WHERE n.username = ?1 AND status = 1 ORDER BY n.createdDate desc")
	List<Notifications> findNotiyByUsername(String username);
	
	@Modifying
	@Query(value = "update NOTIFICATIONS set STATUS = 0 where USERNAME = ?1", nativeQuery = true)
	void seenAll(String username);
}
