package com.team.bookstore.Entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "chapter")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Chapter extends Auditable implements Comparable<Chapter>{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "chapter_id")
    int id;
    @Column(name = "chapter_index")
    int chapterIndex;
    @Column(name = "book_id")
    int bookId;
    byte[] sourcefile;
    @ManyToOne()
    @JoinColumn(name = "book_id",insertable = false,updatable = false)
    Book book;

    @Override
    public int compareTo(Chapter o) {
        return Integer.compare(this.chapterIndex, o.getChapterIndex());
    }
}
