package com.team.bookstore.Repositories;

import com.team.bookstore.Entities.Book;
import com.team.bookstore.Entities.Book_Author;
import com.team.bookstore.Entities.ComposeKey.BookAuthorKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Book_AuthorRepository extends JpaRepository<Book_Author, BookAuthorKey>{
    void deleteAllById(Book_Author id);
    List<Book_Author> findBook_AuthorsByBook(Book book);
    void deleteAll(Iterable<? extends Book_Author> entities);
}
