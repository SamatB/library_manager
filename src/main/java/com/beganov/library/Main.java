package com.beganov.library;

import com.beganov.library.model.Author;
import com.beganov.library.model.Book;
import com.beganov.library.model.Category;
import com.beganov.library.model.Profile;
import com.beganov.library.service.AuthorService;
import com.beganov.library.service.BookService;
import com.beganov.library.service.CategoryService;
import com.beganov.library.service.ProfileService;
import com.beganov.library.service.impl.AuthorServiceImpl;
import com.beganov.library.service.impl.BookServiceImpl;
import com.beganov.library.service.impl.CategoryServiceImpl;
import com.beganov.library.service.impl.ProfileServiceImpl;
import com.beganov.library.util.HibernateUtil;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        AuthorService authorService = new AuthorServiceImpl();
        BookService bookService = new BookServiceImpl();
        CategoryService categoryService = new CategoryServiceImpl();
        ProfileService profileService = new ProfileServiceImpl();

        try {
            // создание категорий
            Category dystopia = new Category();
            dystopia.setName("Dystopia");
            Long dystopiaId = categoryService.save(dystopia);

            Category classic = new Category();
            classic.setName("Classic");
            Long classicId = categoryService.save(classic);

            Category politicalFiction = new Category();
            politicalFiction.setName("Political fiction");
            Long politicalFictionId = categoryService.save(politicalFiction);

            Category satire = new Category();
            satire.setName("Satire");
            Long satireId = categoryService.save(satire);

            Category novel = new Category();
            novel.setName("Novel");
            Long novelId = categoryService.save(novel);

            // чтение одной категории
            System.out.println("// получение категории по id");
            System.out.println(categoryService.getById(dystopiaId));

            // чтение всех категорий
            System.out.println("// получение всех категорий");
            System.out.println(categoryService.getAllCategories());

            // изменение категории
            System.out.println("// изменение категории");
            categoryService.update(satireId, "Satirical fiction");
            System.out.println(categoryService.getById(satireId));

            // создание автора вместе с книгами и профилем
            System.out.println("// создание автора вместе с книгами и профилем");
            Long orwellId = authorService.saveAuthorWithBooks(
                    "George Orwell",
                    "orwell@mail.com",
                    "English writer",
                    List.of(
                            new AuthorService.BookDraft("1984", List.of(dystopiaId, classicId, novelId)),
                            new AuthorService.BookDraft("Animal Farm", List.of(politicalFictionId, satireId))
                    )
            );
            System.out.println(authorService.getById(orwellId));

            // создание второго автора вместе с книгами и профилем
            System.out.println("// создание второго автора вместе с книгами и профилем");
            Long tolkienId = authorService.saveAuthorWithBooks(
                    "J. R. R. Tolkien",
                    "tolkien@mail.com",
                    "Fantasy writer",
                    List.of(
                            new AuthorService.BookDraft("The Hobbit", List.of(classicId, novelId)),
                            new AuthorService.BookDraft("The Lord of the Rings", List.of(classicId, novelId))
                    )
            );
            System.out.println(authorService.getById(tolkienId));

            // создание автора отдельно без книг
            System.out.println("// создание автора отдельно без книг");
            Profile rowlingProfile = new Profile();
            rowlingProfile.setEmail("rowling@mail.com");
            rowlingProfile.setBio("British author");

            Author rowling = new Author();
            rowling.setFullName("J. K. Rowling");
            rowling.setProfile(rowlingProfile);

            Long rowlingId = authorService.saveAuthor(rowling);
            System.out.println(authorService.getById(rowlingId));

            // получение всех авторов
            System.out.println("// получение всех авторов");
            System.out.println(authorService.getAllAuthors());

            // изменение автора
            System.out.println("// изменение автора");
            authorService.updateAuthor(rowlingId, "Joanne Rowling");
            System.out.println(authorService.getById(rowlingId));

            // добавление новой книги существующему автору
            System.out.println("// добавление новой книги существующему автору");
            Long cataloniaBookId = authorService.addBookToAuthor(
                    orwellId,
                    "Homage to Catalonia",
                    List.of(classicId, novelId)
            );
            System.out.println(bookService.getById(cataloniaBookId));

            // создание книги отдельно через BookService
            System.out.println("// создание книги отдельно через BookService");
            Book manualBook = new Book();
            manualBook.setTitle("Manual Book");
            Long manualBookId = bookService.save(manualBook);
            System.out.println(bookService.getById(manualBookId));

            // получение книги по id
            System.out.println("// получение книги по id");
            System.out.println(bookService.getById(cataloniaBookId));

            // получение всех книг
            System.out.println("// получение всех книг");
            System.out.println(bookService.getAll());

            // изменение названия книги
            System.out.println("// изменение названия книги");
            bookService.updateTitle(cataloniaBookId, "Homage to Catalonia - Updated");
            System.out.println(bookService.getById(cataloniaBookId));

            // изменение автора книги без изменения названия
            System.out.println("// изменение автора книги без изменения названия");
            bookService.changeAuthor(cataloniaBookId, tolkienId);
            System.out.println(bookService.getById(cataloniaBookId));

            // создание профиля отдельно
            System.out.println("// создание профиля отдельно");
            Profile separateProfile = new Profile();
            separateProfile.setEmail("separate@mail.com");
            separateProfile.setBio("Separate profile");
            Long separateProfileId = profileService.save(separateProfile);
            System.out.println(profileService.getById(separateProfileId));

            // получение профиля по id
            System.out.println("// получение профиля по id");
            System.out.println(profileService.getById(separateProfileId));

            // получение всех профилей
            System.out.println("// получение всех профилей");
            System.out.println(profileService.getAllProfiles());

            // изменение email профиля
            System.out.println("// изменение email профиля");
            profileService.updateEmail(separateProfileId, "updated_profile@mail.com");
            System.out.println(profileService.getById(separateProfileId));

            // изменение bio профиля
            System.out.println("// изменение bio профиля");
            profileService.updateBio(separateProfileId, "Updated separate profile bio");
            System.out.println(profileService.getById(separateProfileId));

//            // удаление книги
//            System.out.println("// удаление книги");
//            bookService.delete(manualBookId);
//            System.out.println(bookService.getById(manualBookId));
//
//            // удаление профиля
//            System.out.println("// удаление профиля");
//            profileService.delete(separateProfileId);
//            System.out.println(profileService.getById(separateProfileId));
//
//            // удаление категории
//            System.out.println("// удаление категории");
//            categoryService.delete(novelId);
//            System.out.println(categoryService.getById(novelId));
//
//            // удаление автора
//            System.out.println("// удаление автора");
//            authorService.delete(rowlingId);
//            System.out.println(authorService.getById(rowlingId));

            // итоговое состояние авторов
            System.out.println("// итоговое состояние авторов");
            System.out.println(authorService.getAllAuthors());

            // итоговое состояние книг
            System.out.println("// итоговое состояние книг");
            System.out.println(bookService.getAll());

            // итоговое состояние категорий
            System.out.println("// итоговое состояние категорий");
            System.out.println(categoryService.getAllCategories());

            // итоговое состояние профилей
            System.out.println("// итоговое состояние профилей");
            System.out.println(profileService.getAllProfiles());

        } finally {
            HibernateUtil.shutdown();
        }
    }
}