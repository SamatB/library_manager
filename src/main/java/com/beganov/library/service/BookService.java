package com.beganov.library.service;

import com.beganov.library.model.Book;

import java.util.List;

public interface BookService {

    Long save(Book book);

    Book getById(Long id);

    List<Book> getAll();

    Book updateTitle(Long id, String title);

    Book changeAuthor(Long bookId, Long newAuthorId);

    String delete(Long id);

    List<Book> findByTitle(String name);

    List<Book> findByAuthor(String authorName);

    List<Book> findByCategory(String category);

    List<Book> findAllWithAuthor();
}
