package com.beganov.library.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "books")
@NoArgsConstructor //Конструктор без параметров
//@AllArgsConstructor Конструктор с параметрами
//@RequiredArgsConstructor Для обязательных полей (final)
@Getter
@Setter
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_seq_gen")
    @SequenceGenerator(
            name = "book_seq_gen",
            sequenceName = "book_seq",
            allocationSize = 1
    )
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    // @Column(nullable = false )//not null
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    private Author author;

    @ManyToMany
    @JoinTable(name = "books_categories",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories = new ArrayList<>();

    public Book(String title) {
        this.title = title;
    }

    public void addCategory(Category category) {
        if (!categories.contains(category)) {
            categories.add(category);
        }
        if (!category.getBooks().contains(this)) {
            category.getBooks().add(this);
        }
    }

    public void removeCategory(Category category) {
        categories.remove(category);
        category.getBooks().remove(this);
    }

    @Override
    public String toString() {
        String authorName = (author != null) ? author.getFullName() : "no author";
        return "Book{id=%d, title='%s', author='%s'}".formatted(id, title, authorName);
    }
}
