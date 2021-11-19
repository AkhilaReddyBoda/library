package com.greenapex.ELibrary.model;

public class UserAndBookDTO {
	
	
	public Long userId;
	
	public String user_name;
	
	public Long book_id;
	
	public String book_name;
	
	public Long user_and_book_id;

	public UserAndBookDTO() {
		super();
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public Long getBook_id() {
		return book_id;
	}

	public void setBook_id(Long book_id) {
		this.book_id = book_id;
	}

	public String getBook_name() {
		return book_name;
	}

	public void setBook_name(String book_name) {
		this.book_name = book_name;
	}

	public Long getUser_and_book_id() {
		return user_and_book_id;
	}

	public void setUser_and_book_id(Long user_and_book_id) {
		this.user_and_book_id = user_and_book_id;
	}
	
	

}
