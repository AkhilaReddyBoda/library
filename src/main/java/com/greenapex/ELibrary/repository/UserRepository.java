package com.greenapex.ELibrary.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.greenapex.ELibrary.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	@Query(value = "select * from user_details where email =?1", nativeQuery = true)
	User findByEmail(String email);

	@Query(value ="select * from user_details where role =?1",nativeQuery = true)
	List<User> findByUserRole(String string);
}
