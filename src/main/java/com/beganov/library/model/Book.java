package com.beganov.library.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "books")
@NoArgsConstructor //Конструктор без параметров
@AllArgsConstructor //Конструктор с параметрами
//@RequiredArgsConstructor //Для обязательных полей (final)
@Getter
@Setter
@ToString
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "author", nullable = false )//not null
    private String author;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }
}
