package com.beganov.library;

import com.beganov.library.model.Category;
import com.beganov.library.service.AuthorService;
import com.beganov.library.service.BookService;
import com.beganov.library.service.CategoryService;
import com.beganov.library.service.impl.AuthorServiceImpl;
import com.beganov.library.service.impl.BookServiceImpl;
import com.beganov.library.service.impl.CategoryServiceImpl;
import com.beganov.library.util.HibernateUtil;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        AuthorService authorService = new AuthorServiceImpl();
        BookService bookService = new BookServiceImpl();
        CategoryService categoryService = new CategoryServiceImpl();

        try {
            Category dystopia = new Category();
            dystopia.setName("Dystopia");
            Long dystopiaId = categoryService.save(dystopia);

            Category classic = new Category();
            classic.setName("Classic");
            Long classicId = categoryService.save(classic);

            Long authorId = authorService.saveAuthorWithBooks(
                    "George Orwell",
                    "orwell@mail.com",
                    "English writer",
                    List.of(
                            new AuthorService.BookDraft("1984", List.of(dystopiaId, classicId)),
                            new AuthorService.BookDraft("Animal Farm", List.of(classicId))
                    )
            );

            Long newBookId = authorService.addBookToAuthor(
                    authorId,
                    "Homage to Catalonia",
                    List.of(classicId)
            );

            bookService.updateTitle(newBookId, "Homage to Catalonia - Updated");

            Long secondAuthorId = authorService.saveAuthorWithBooks(
                    "Another Author",
                    "author2@mail.com",
                    "Second writer",
                    List.of()
            );

            bookService.changeAuthor(newBookId, secondAuthorId);

            System.out.println(authorService.getById(authorId));
            System.out.println(bookService.getById(newBookId));
            System.out.println(categoryService.getAllCategories());
        } finally {
            HibernateUtil.shutdown();
        }
    }
}