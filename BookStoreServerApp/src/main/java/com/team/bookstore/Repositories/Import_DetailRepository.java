package com.team.bookstore.Repositories;

import com.team.bookstore.Entities.Book;
import com.team.bookstore.Entities.ComposeKey.ImportDetailKey;
import com.team.bookstore.Entities.Import;
import com.team.bookstore.Entities.Import_Detail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Import_DetailRepository extends JpaRepository<Import_Detail, ImportDetailKey> {
    List<Import_Detail> findImport_DetailByYmport(Import ymport);
}
