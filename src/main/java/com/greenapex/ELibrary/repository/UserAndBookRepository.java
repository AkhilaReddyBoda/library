package com.greenapex.ELibrary.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.greenapex.ELibrary.model.UserAndBooks;

public interface UserAndBookRepository extends JpaRepository<UserAndBooks,Long> {

	@Query(value = "select * from user_and_books where user_id =?1",nativeQuery = true)
	List<UserAndBooks> findByUserId(Long uid);

	
	@Query(value = "select * from user_and_books where book_id =?1 and user_id=?2",nativeQuery = true)
	UserAndBooks findByBookIdAndUserId(Long bookId,Long userId);

}
