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

    // @Column(nullable = false )//not null
    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    public Book(String title) {
        this.title = title;
    }
}
