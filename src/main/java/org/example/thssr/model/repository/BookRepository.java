package org.example.thssr.model.repository;

import org.example.thssr.model.entity.BookEntity;

import java.util.List;

public abstract class BookRepository {
    public abstract void createBook(BookEntity entity);

    public abstract List<BookEntity> findAll();

    public abstract BookEntity findById(long id);

    public abstract void deleteById(long id);

    public abstract void updateBook(long id, BookEntity entity);
}
