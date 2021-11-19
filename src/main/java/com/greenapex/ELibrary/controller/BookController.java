package com.greenapex.ELibrary.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.greenapex.ELibrary.model.Book;
import com.greenapex.ELibrary.model.User;
import com.greenapex.ELibrary.model.UserAndBookDTO;
import com.greenapex.ELibrary.model.UserAndBooks;
import com.greenapex.ELibrary.repository.BookRepository;
import com.greenapex.ELibrary.repository.UserAndBookRepository;
import com.greenapex.ELibrary.repository.UserRepository;
//import com.greenapex.ELibrary.repository.BookRepository;
import com.greenapex.ELibrary.service.BookService;
import com.greenapex.ELibrary.service.UseService;

@Controller
public class BookController {
	@Autowired
	public BookService bookService;

	@Autowired
	public BookRepository bookrepo;

	@Autowired
	private UserRepository userrepo;

	@Autowired
	public UseService userService;

	@Autowired
	public UserAndBookRepository userAndBookRepo;

	@Autowired
	private JavaMailSender javaMailSender;

	public void sendEmail(String mailTo, String subject, String message) {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
			messageHelper.setFrom("akhilareddyboda1234@gmail.com");
			messageHelper.setTo(mailTo);
			messageHelper.setSubject(subject);
			messageHelper.setText("Ok", message);
		} catch (MessagingException exception) {
			exception.printStackTrace();
		}
		javaMailSender.send(mimeMessage);
	}

	@GetMapping("/addbook-form")
	public String showaddbookpage(Model model) {
		model.addAttribute("book", new Book());
		return "addbook-form";

	}

	@PostMapping("/create-book")
	public String addbook(Book book, Model model) {
		bookService.addBook(book);
		model.addAttribute("message", "Book added Successfully");
		return "success";

	}

	@GetMapping("/bookslist")
	public String getBooks(Book book, Model model, Principal principal) {
		User user = userService.getCustomerDetails(principal.getName());
		List<Book> bookList = bookService.listAll();
		if (user.getRole().equalsIgnoreCase("Admin")) {
			model.addAttribute("bookList", bookList);
			return "bookslist";
		} else if (user.getRole().equalsIgnoreCase("User")) {
			model.addAttribute("bookList", bookList);
			return "bookListforUser";
		}
		return null;
	}

	@GetMapping("/updateBook")
	public ModelAndView updateBook(@RequestParam Long bookId) {
		ModelAndView modelAndView = new ModelAndView("addbook-form");
		Optional<Book> book = bookrepo.findById(bookId);
		modelAndView.addObject("book", book.get());
		return modelAndView;
	}

	@GetMapping("/usersList")
	public String getUsers(User user, Model model) {
		List<User> allUsers = userrepo.findByUserRole("User");
		model.addAttribute("userList", allUsers);
		return "userList";
	}

	@GetMapping("/requestBook")
	public String bookRequest(@RequestParam Long bookId, Principal principal, Model model) {
		User user = userService.getCustomerDetails(principal.getName());
		UserAndBooks existingRequest = userAndBookRepo.findByBookIdAndUserId(bookId, user.getUID());
		if (existingRequest == null) {
			UserAndBooks userAndBooks = new UserAndBooks();
			userAndBooks.setBook_id(bookId);
			userAndBooks.setUser_id(user.getUID());
			userAndBooks.setStatus("Requested");
			userAndBookRepo.save(userAndBooks);

		} else {
			existingRequest.setStatus("Requested");
			userAndBookRepo.save(existingRequest);
		}
		List<User> adminInfo = userrepo.findByUserRole("Admin");
		User emailDetails = adminInfo.get(0);
		sendEmail(emailDetails.getEmail(), "Book Request", user.getUser_name() + " Requested ");
		model.addAttribute("message", "Book request sent Successfully");
		return "success";
	}

	@GetMapping("/myrequestedbookslist")
	public String requestedBookList(Principal principal, Model model) {
		User user = userService.getCustomerDetails(principal.getName());
		List<UserAndBooks> userAndBooks = userAndBookRepo.findByUserId(user.getUID());
		List<Book> booksList = new ArrayList<Book>();
		userAndBooks.forEach(bookId -> {
			if (bookId.getStatus().equalsIgnoreCase("Requested")) {
				Optional<Book> book = bookrepo.findById(bookId.getBook_id());
				booksList.add(book.get());
			}
		});
		model.addAttribute("bookList", booksList);
		return "requestedBookList";
	}

	@GetMapping("/cancelrequestBook")
	public String cancelrequestBook(@RequestParam Long bookId, Principal principal, Model model) {
		User user = userService.getCustomerDetails(principal.getName());
		UserAndBooks userAndBooks = userAndBookRepo.findByBookIdAndUserId(bookId, user.getUID());
		userAndBooks.setStatus("Cancelled");
		userAndBookRepo.save(userAndBooks);
		model.addAttribute("message", "Request cancelled Successfully");
		return "success";
	}

	@GetMapping("/bookRequests")
	public String bookRequests(UserAndBookDTO userAndBookDTO, Model model) {
		List<UserAndBooks> userAndBooks = userAndBookRepo.findAll();
		List<UserAndBookDTO> bookDTOs = new ArrayList<UserAndBookDTO>();
		for (UserAndBooks books : userAndBooks) {
			if (books.getStatus().equalsIgnoreCase("Requested")) {
				Optional<User> user = userrepo.findById(books.getUser_id());
				Optional<Book> book = bookrepo.findById(books.getBook_id());
				UserAndBookDTO andBookDTO = new UserAndBookDTO();
				andBookDTO.setBook_id(book.get().getBID());
				andBookDTO.setBook_name(book.get().getBook_name());
				andBookDTO.setUser_name(user.get().getEmail());
				andBookDTO.setUserId(user.get().getUID());
				andBookDTO.setUser_and_book_id(books.getUser_and_book_id());
				bookDTOs.add(andBookDTO);
			}
		}
		model.addAttribute("booksAndUsers", bookDTOs);

		return "allBookRequest";
	}

	@GetMapping("/approveRequest")
	public String approveRequest(@RequestParam Long userbookId, Model model) {
		Optional<UserAndBooks> userAndBooks = userAndBookRepo.findById(userbookId);
		Optional<Book> book = bookrepo.findById(userAndBooks.get().getBook_id());
		book.get().setQuantity(book.get().getQuantity() - 1);
		bookrepo.save(book.get());
		userAndBooks.get().setStatus("Approved");
		userAndBookRepo.save(userAndBooks.get());

		model.addAttribute("message", "Request approved Successfully");
		return "success";
	}

	@GetMapping("/declineRequest")
	public String declineRequest(@RequestParam Long userbookId, Model model) {
		Optional<UserAndBooks> userAndBooks = userAndBookRepo.findById(userbookId);
		userAndBooks.get().setStatus("Declined");
		userAndBookRepo.save(userAndBooks.get());
		model.addAttribute("message", "Request declined Successfully");
		return "success";
	}

	@GetMapping("/mybooks")
	public String myBooks(Principal principal, Model model, Book book) {
		User user = userService.getCustomerDetails(principal.getName());
		List<UserAndBooks> userAndBooks = userAndBookRepo.findByUserId(user.getUID());
		List<Book> booksList = new ArrayList<Book>();
		userAndBooks.forEach(bookId -> {
			if (bookId.getStatus().equalsIgnoreCase("Approved")) {
				Optional<Book> books = bookrepo.findById(bookId.getBook_id());
				booksList.add(books.get());
			}
		});
		model.addAttribute("bookList", booksList);
		return "myBooks";
	}

	@GetMapping("/bookReturn")
	public String bookReturn(@RequestParam Long bookId, Principal principal, Model model) {
		User user = userService.getCustomerDetails(principal.getName());
		UserAndBooks userAndBooks = userAndBookRepo.findByBookIdAndUserId(bookId, user.getUID());
		userAndBooks.setStatus("Returned");
		userAndBookRepo.save(userAndBooks);
		Optional<Book> book = bookrepo.findById(bookId);
		book.get().setQuantity(book.get().getQuantity() + 1);
		bookrepo.save(book.get());
		model.addAttribute("message", "Book returned Successfully");
		return "success";
	}

	@GetMapping("/issuedBooks")
	public String issuedBooks(UserAndBookDTO userAndBookDTO, Model model) {
		List<UserAndBooks> userAndBooks = userAndBookRepo.findAll();
		List<UserAndBookDTO> bookDTOs = new ArrayList<UserAndBookDTO>();
		for (UserAndBooks books : userAndBooks) {
			if (books.getStatus().equalsIgnoreCase("Approved")) {
				Optional<User> user = userrepo.findById(books.getUser_id());
				Optional<Book> book = bookrepo.findById(books.getBook_id());
				UserAndBookDTO andBookDTO = new UserAndBookDTO();
				andBookDTO.setBook_id(book.get().getBID());
				andBookDTO.setBook_name(book.get().getBook_name());
				andBookDTO.setUser_name(user.get().getEmail());
				andBookDTO.setUserId(user.get().getUID());
				andBookDTO.setUser_and_book_id(books.getUser_and_book_id());
				bookDTOs.add(andBookDTO);
			}
		}
		model.addAttribute("booksAndUsers", bookDTOs);

		return null;
	}
}
