package com.greenapex.ELibrary.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_details")

public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long UID;

	private String user_name;

	private String password;

	private String DOB;

	private String email;

	private String phone_no;
	
	private String role;
	

	

	public User() {
		super();
	}

	public User(Long uID, String user_name, String password, String dOB, String email, String phone_no,
			boolean status) {
		super();
		UID = uID;
		this.user_name = user_name;
		this.password = password;
		DOB = dOB;
		this.email = email;
		this.phone_no = phone_no;
	}

	public Long getUID() {
		return UID;
	}

	public void setUID(Long uID) {
		UID = uID;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDOB() {
		return DOB;
	}

	public void setDOB(String dOB) {
		DOB = dOB;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone_no() {
		return phone_no;
	}

	public void setPhone_no(String phone_no) {
		this.phone_no = phone_no;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	

}
