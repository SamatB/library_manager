package com.beganov.library.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "authors")
@Getter
@Setter
@NoArgsConstructor
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_seq_gen")
    @SequenceGenerator(
            name = "author_seq_gen",
            sequenceName = "author_seq",
            allocationSize = 1
    )
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Book> books = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    public Author(String fullName) {
        this.fullName = fullName;
    }

    public void add(Book book) {
        if (!books.contains(book)) {
            books.add(book);
        }
        book.setAuthor(this);
    }

    public void removeBook(Book book) {
        books.remove(book);
        book.setAuthor(null);
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
        if (profile != null) {
            profile.setAuthor(this);
        }
    }

    @Override
    public String toString() {
        return "Author{id=%d, fullName='%s'}".formatted(id, fullName);
    }
}
