package com.beganov.library.service;

import com.beganov.library.model.Book;

import java.util.List;

public interface BookService {

    Long save(Book book);

    Long save(Long authorId, String title, List<Long> categoryIds);

    Book getById(Long id);

    List<Book> getAll();

    Book update(Long id, String title);

    Book updateCategories(Long bookId, List<Long> categoryIds);

    String delete(Long id);
}
