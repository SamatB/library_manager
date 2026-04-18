package com.beganov.library.service;

import com.beganov.library.model.Author;

import java.util.List;

public interface AuthorService {

    Long saveAuthor(Author author);

    Long saveAuthorWithBooks(
            String fullName,
            String email,
            String bio,
            List<BookDraft> books
    );

    Author getById(Long id);

    List<Author> getAllAuthors();

    Author updateAuthor(Long id, String fullName);

    Long addBookToAuthor(Long authorId, String title, List<Long> categoryIds);
    Author addExistingBookToAuthor(Long authorId, Long bookId);

    String delete(Long id);

    /**
     * Это вложенный record в интерфейсе AuthorService.
     * Он нужен как простая структура данных для передачи параметров книги при создании автора вместе с книгами.
     * То есть, вместо того чтобы передавать отдельно:
     * - title
     * - categoryIds
     * мы объединяем их в один объект.
     */
    record BookDraft(String title, List<Long> categoryIds) {
    }

}
