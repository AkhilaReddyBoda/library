package com.greenapex.ELibrary.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "books")
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long BID;

	private String book_name;

	private String author;

	private Long quantity;

	public Book() {
		super();
	}



	public Book(Long bID, String book_name, String author, Long quantity) {
		super();
		BID = bID;
		this.book_name = book_name;
		this.author = author;
		this.quantity = quantity;
	}



	public Long getBID() {
		return BID;
	}

	public void setBID(Long bID) {
		BID = bID;
	}

	public String getBook_name() {
		return book_name;
	}

	public void setBook_name(String book_name) {
		this.book_name = book_name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}



	public Long getQuantity() {
		return quantity;
	}



	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}



}
