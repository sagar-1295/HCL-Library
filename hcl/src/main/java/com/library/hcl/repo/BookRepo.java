package com.library.hcl.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.library.hcl.model.Book;

@Repository
public interface BookRepo extends PagingAndSortingRepository<Book, Integer>{

	Page<Book> findByLastname(String lastname, Pageable pageable);

	Page<Book> findbyUuid(String uuid, Pageable paging);

	Page<Book> findbyTitle(String title, Pageable paging);

	Page<Book> findbyAuthor(String author, Pageable paging);

	Page<Book> findbyCategory(String category, Pageable paging);

	Book findbyUuid(Integer bookId);
}
