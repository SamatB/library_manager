package com.beganov.library.service;

import com.beganov.library.model.Book;

public interface BookService {

    Long save(Book book);

    Book getById(Long id);

    Book update(Long id, String title, String author);

    String delete(Long id);
}
