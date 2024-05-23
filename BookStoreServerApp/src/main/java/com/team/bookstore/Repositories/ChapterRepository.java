package com.team.bookstore.Repositories;

import com.team.bookstore.Entities.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChapterRepository extends JpaRepository<Chapter,Integer> {

    Chapter findChapterById(int id);
    boolean existsChapterByChapterIndexAndBookId(int chapter_index,int book_id);
}
