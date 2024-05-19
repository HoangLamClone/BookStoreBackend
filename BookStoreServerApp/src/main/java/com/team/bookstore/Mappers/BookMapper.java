package com.team.bookstore.Mappers;

import com.sun.source.tree.CaseTree;
import com.team.bookstore.Dtos.Requests.BookRequest;
import com.team.bookstore.Dtos.Responses.BookResponse;
import com.team.bookstore.Dtos.Responses.CategoryResponse;
import com.team.bookstore.Entities.*;
import jdk.jfr.Name;
import lombok.extern.log4j.Log4j2;
import org.hibernate.boot.jaxb.hbm.internal.CacheAccessTypeConverter;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.HashSet;
import java.util.Set;
@Mapper(componentModel = "spring")
public interface BookMapper {
    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);
    @Mapping(target = "id",ignore = true)
    @Mapping(target = "createAt",ignore = true)
    @Mapping(target = "updateAt",ignore = true)
    @Mapping(target = "createBy",ignore = true)
    @Mapping(target = "updateBy",ignore = true)
    @Mapping(target = "total_pay",ignore = true)
    @Mapping(target = "book_keyword",ignore = true)
    @Mapping(target = "import_detail",ignore = true)
    @Mapping(target = "order_detail",ignore = true)
    @Mapping(target = "customer_book",ignore = true)
    @Mapping(target = "feedback",ignore = true)
    @Mapping(target = "sourcefile",ignore = true)
    @Mapping(target = "readingsession",ignore = true)
    @Mapping(target = "language",source = "language_id",qualifiedByName =
            "toLanguage")
    @Mapping(target = "category",source = "category_id",qualifiedByName =
            "toCategory")
    @Mapping(target = "publisher",source = "publisher_id",qualifiedByName =
            "toPublisher")
    @Mapping(target = "provider",source = "provider_id",qualifiedByName =
            "toProvider")
    @Mapping(target = "galleryManage",source = "gallery_ids",qualifiedByName
            = "toGallery")
    @Mapping(target = "book_author",source = "author_ids",qualifiedByName =
            "toBook_Author")
    Book toBook(BookRequest bookRequest);
    @Named("toLanguage")
    default Language toLanguage(int language_id){
        Language language = new Language();
        language.setId(language_id);
        return language;
    }
    @Named("toCategory")
    default Category toCategory(int category_id){
        Category category =  new Category();
        category.setId(category_id);
        return category;
    }
    @Named("toPublisher")
    default Publisher toPublisher(int publisher_id){
        Publisher publisher = new Publisher();
        publisher.setId(publisher_id);
        return publisher;
    }
    @Named("toProvider")
    default Provider toProvider(int provider_id){
        Provider provider = new Provider();
        provider.setId(provider_id);
        return provider;
    }
    @Named("toGallery")
    default Set<GalleryManage> toGallery(Set<Integer> gallery_ids){
        Set<GalleryManage> galleryManages =  new HashSet<>();
        gallery_ids.forEach(id -> {
            GalleryManage gallery = new GalleryManage();
            gallery.setId(id);
            galleryManages.add(gallery);
        });
        return galleryManages;
    }
    @Named("toBook_Author")
    default Set<Book_Author> toBookAuthor(Set<Integer> author_ids){
        Set<Book_Author> book_authors = new HashSet<>();
        author_ids.forEach(id->{
            Book_Author bookAuthor = new Book_Author();
            Author author = new Author();
            author.setId(id);
            bookAuthor.setAuthor(author);
            book_authors.add(bookAuthor);
        });
        return book_authors;
    }
    @Mapping(target = "createAt",ignore = true)
    Book toBook(Book Other);
    @Mapping(target = "authors",source = "book_author",qualifiedByName = "toAuthor")
    BookResponse toBookResponse(Book book);
    @Named("toAuthor")
    default Set<Author> toAuthor(Set<Book_Author> book_author){
        Set<Author> authors=new HashSet<>();
        book_author.forEach(bookAuthor -> {
            authors.add(bookAuthor.getAuthor());
        });
        return authors;
    }
}
