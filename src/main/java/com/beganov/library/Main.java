package com.beganov.library;

import com.beganov.library.model.Book;
import com.beganov.library.service.BookService;
import com.beganov.library.service.BookServiceImpl;
import com.beganov.library.util.HibernateUtil;

public class Main {
    public static void main(String[] args) {

        BookService bookService = new BookServiceImpl();

        try {
            Book book1 = new Book("1984", "Orwell");
            Book book2 = new Book("Java EE", "A. Toktosunov");

            long id1 = bookService.save(book1);
            System.out.println("книга " + book1 + " сохранена в БД и имеет id: " + id1);
            System.out.println("Сохраненная книга:" + bookService.getById(id1));

            long id2 = bookService.save(book2);
            System.out.println("книга " + book2 + " сохранена в БД и имеет id: " + id2);
            System.out.println("Сохраненная книга:" + bookService.getById(id2));

            Book updated = bookService.update(2L, "Гордость и предупреждение", "Jame Oston");
            System.out.println("Обновленная книга: " + updated);

            System.out.println(bookService.delete(1L));

        } finally {
            HibernateUtil.shutdown();
        }


    }
}