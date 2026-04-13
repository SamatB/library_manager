package com.beganov.library;

import com.beganov.library.model.Author;
import com.beganov.library.model.Category;
import com.beganov.library.model.Profile;
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
            Profile profile = new Profile();
            profile.setEmail("orwell@mail.com");
            profile.setBio("English writer");

            Author author = new Author();
            author.setFullName("George Orwell");
            author.setProfile(profile);

            Long authorId = authorService.saveAuthor(author);

            Category dystopia = new Category();
            dystopia.setName("Dystopia");
            Long categoryId1 = categoryService.save(dystopia);

            Category classic = new Category();
            classic.setName("Classic");
            Long categoryId2 = categoryService.save(classic);

            Long bookId = bookService.save(authorId, "1984", List.of(categoryId1, categoryId2));

            Profile profile2 = new Profile();
            profile2.setEmail("tolstoy@mail.com");
            profile2.setBio("Russian writer");

            Author author2 = new Author();
            author2.setFullName("Leo Tolstoy");
            author2.setProfile(profile2);

            Long author2Id = authorService.saveAuthor(author2);

            authorService.assignBook(author2Id, bookId);

            System.out.println("=== CREATE ===");
            System.out.println(authorService.getById(authorId));
            System.out.println(bookService.getById(bookId));
            System.out.println(categoryService.getAllCategories());

            System.out.println("=== READ ALL BOOKS ===");
            System.out.println(bookService.getAll());

            System.out.println("=== UPDATE ===");
            bookService.update(bookId, "Animal Farm");
            authorService.updateAuthor(authorId, "Eric Arthur Blair");
            categoryService.update(categoryId1, "Political fiction");
            bookService.updateCategories(bookId, List.of(categoryId1));

            System.out.println(bookService.getById(bookId));
            System.out.println(authorService.getById(authorId));
            System.out.println(categoryService.getById(categoryId1));

            System.out.println("=== DELETE BOOK ===");
//            bookService.delete(bookId);
//            System.out.println(bookService.getById(bookId));
        } finally {
            HibernateUtil.shutdown();
        }
    }
}