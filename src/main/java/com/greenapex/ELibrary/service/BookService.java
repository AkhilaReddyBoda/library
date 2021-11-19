package com.greenapex.ELibrary.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.greenapex.ELibrary.model.Book;
import com.greenapex.ELibrary.repository.BookRepository;

@Service
public class BookService {
	@Autowired
	public BookRepository bookRepo;

	public Book save(Book book) {
		return bookRepo.save(book);

	}

	public Book addBook(Book book) {
		
		Book addBook = bookRepo.save(book);
		return addBook;

	}

	public List<Book> listAll() {
		
		return bookRepo.findAll();
	}

}
