package com.beganov.library;

import com.beganov.library.model.Author;
import com.beganov.library.model.Book;
import com.beganov.library.service.AuthorService;
import com.beganov.library.service.AuthorServiceImpl;
import com.beganov.library.service.BookService;
import com.beganov.library.service.BookServiceImpl;
import com.beganov.library.util.HibernateUtil;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        BookService bookService = new BookServiceImpl();
        AuthorService authorService = new AuthorServiceImpl();

        try {

            Book book1 = new Book("1984");
            Book book2 = new Book("Java EE");
            Book book3 = new Book("423 Forengeit");
            Book book4 = new Book("Java Script");

            Author author1 = new Author("Muha");
            Author author2 = new Author("Aisezim");

            author1.add(book1);
            author1.add(book2);
            author2.add(book4);
            author2.add(book3);

            authorService.saveAuthor(author1);
            authorService.saveAuthor(author2);


        } finally {
            HibernateUtil.shutdown();
        }


    }
}