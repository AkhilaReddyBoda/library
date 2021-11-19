package com.greenapex.ELibrary.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_and_books")
public class UserAndBooks {
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	public Long user_and_book_id;
	
	public Long book_id;
	
	public Long user_id;
	
	public String status;

	public UserAndBooks() {
		super();
	}

	public Long getUser_and_book_id() {
		return user_and_book_id;
	}

	public void setUser_and_book_id(Long user_and_book_id) {
		this.user_and_book_id = user_and_book_id;
	}

	public Long getBook_id() {
		return book_id;
	}

	public void setBook_id(Long book_id) {
		this.book_id = book_id;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	

}
