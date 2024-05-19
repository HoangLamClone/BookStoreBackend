package com.team.bookstore.Repositories;

import com.team.bookstore.Entities.Book_Keyword;
import com.team.bookstore.Entities.ComposeKey.BookKeywordKey;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Book_KeywordRepository extends JpaRepository<Book_Keyword, BookKeywordKey> {
    Book_Keyword findBook_KeywordById(BookKeywordKey id);
    Boolean existsById(Book_Keyword id);
    List<Book_Keyword> findAll(Specification<Book_Keyword> spec);
}
