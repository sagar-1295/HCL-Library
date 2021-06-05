package com.library.hcl.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.library.hcl.model.Book;
import com.library.hcl.model.Member;
import com.library.hcl.repo.BookRepo;
import com.library.hcl.repo.MemberRepo;

@RestController
@RequestMapping("/book")
public class BookController {
	
	@Autowired
	BookRepo repo;
	
	@Autowired
	MemberRepo memberRepo;

	@GetMapping(value = "/search", produces = "application/json;charset=utf-8")
	public ResponseEntity<List<Book>> findBook(@RequestParam(value = "UUID", required = false) String uuid,
			@RequestParam(value = "title", required = false) String title,
			@RequestParam(value = "author ", required = false) String author,
			@RequestParam(value = "category", required = false) String category,
			@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize) {
		List<Book> bookLi = new ArrayList<>();
		try {
			if (isNotEmpty(uuid)) {
				Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("title"));
				Page<Book> book = repo.findbyUuid(uuid, paging);
				if (book.hasContent())
					bookLi = book.getContent();
				return new ResponseEntity<List<Book>>(bookLi, new HttpHeaders(), HttpStatus.OK);
			}
			if (isNotEmpty(title)) {
				Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("title"));
				Page<Book> book = repo.findbyTitle(title, paging);
				if (book.hasContent())
					bookLi = book.getContent();
				return new ResponseEntity<List<Book>>(bookLi, new HttpHeaders(), HttpStatus.OK);
			}
			if (isNotEmpty(author)) {
				Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("title"));
				Page<Book> book = repo.findbyAuthor(author, paging);
				if (book.hasContent())
					bookLi = book.getContent();
				return new ResponseEntity<List<Book>>(bookLi, new HttpHeaders(), HttpStatus.OK);
			}
			if (isNotEmpty(category)) {
				Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("title"));
				Page<Book> book = repo.findbyCategory(category, paging);
				if (book.hasContent())
					bookLi = book.getContent();
				return new ResponseEntity<List<Book>>(bookLi, new HttpHeaders(), HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ResponseEntity<List<Book>>(bookLi, new HttpHeaders(), HttpStatus.OK);
	}

	public static boolean isNotEmpty(String str) {
		return str != null && !str.contentEquals("null") && !str.isEmpty() && str.trim().length() > 0;
	}
	
	@GetMapping(value = "/borrow", produces = "application/json;charset=utf-8")
	public ResponseEntity<List<Book>> borrowBook(@RequestParam(value = "bookId", required = false) Integer bookId,
			@RequestParam(value = "memberId", required = false) Integer memberId) {
		List<Book> bookLi = new ArrayList<>();
		try {
			if (bookId != null && memberId != null) {
				Optional<Book> book = repo.findById(bookId);
				Optional<Member> member = memberRepo.findById(memberId);
				if (book.isPresent() && member.isPresent() && member.get().getBookCount() != null &&
						member.get().getBookCount() != 0 && book.get().getStatus().equals("Available")) {
					Member mem = member.get();
					Book b = book.get();
					int count = 1;
					mem.setBookCount(count + mem.getBookCount());
					mem.setBookName(b.getTitle());
					mem.setDueDate(prepareDate());
					memberRepo.save(mem);
					b.setStatus("Borrowed");
					repo.save(b);
				}
				return new ResponseEntity<List<Book>>(bookLi, new HttpHeaders(), HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ResponseEntity<List<Book>>(bookLi, new HttpHeaders(), HttpStatus.OK);
	}

	private String prepareDate(){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Calendar c = Calendar.getInstance();
		c.setTime(new Date()); 
		c.add(Calendar.DATE, 14); 
		return sdf.format(c.getTime());
	}
}
