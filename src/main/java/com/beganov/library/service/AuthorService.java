package com.beganov.library.service;

import com.beganov.library.model.Author;
import com.beganov.library.model.Book;

public interface AuthorService {

    Long saveAuthor(Author author);

    Author getById(Long id);

    Author updateAuthor(Long id, Long newId, String name);

    String delete(Long id);


}
