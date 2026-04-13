package com.beganov.library.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "profiles")
@NoArgsConstructor
@Getter
@Setter
//@Data = геттеры + сеттеры + equals & hashCode + toString + @RequiredArgsConstructor( для final и @NonNull полей)
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profile_seq_gen")
    @SequenceGenerator(
            name = "profile_seq_gen",
            sequenceName = "profile_seq",
            allocationSize = 1
    )
    private long id;

    @Column(nullable = false) //not null
    private String email;

    @Column
    private String bio;

    @OneToOne(mappedBy = "profile")
    private Author author;

    @Override
    public String toString() {
        return "Profile{id=%d, email='%s', bio='%s'}".formatted(id, email, bio);
    }
}
