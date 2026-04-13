package com.beganov.library.service;

import com.beganov.library.model.Author;
import com.beganov.library.model.Book;

import java.util.List;

public interface AuthorService {

    Long saveAuthor(Author author);

    Author getById(Long id);

    List<Author> getAllAuthors();

    Author updateAuthor(Long id, String fullName);

    String delete(Long id);

    Author assignBook(Long authorId, Long bookId);

}
