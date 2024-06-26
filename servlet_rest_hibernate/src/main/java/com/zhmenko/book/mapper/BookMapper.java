package com.zhmenko.book.mapper;

import com.zhmenko.author.mapper.AuthorMapper;
import com.zhmenko.book.data.model.BookEntity;
import com.zhmenko.book.model.BookInsertRequest;
import com.zhmenko.book.model.BookModifyRequest;
import com.zhmenko.book.model.BookResponse;
import com.zhmenko.user.mapper.UserCollectionMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.JSR330, uses = {AuthorMapper.class, UserCollectionMapper.class})
public abstract class BookMapper {
    @Mapping(source = "authorId", target = "author")
    public abstract BookEntity bookInsertRequestBookEntity(final BookInsertRequest bookInsertRequest);

    @Mapping(source = "authorId", target = "author")
    public abstract BookEntity bookModifyRequestToBookEntity(final BookModifyRequest bookModifyRequest);


    @Mapping(source = "author", target = "authorName")
    @Mapping(source = "userEntities", target = "usersName")
    public abstract BookResponse bookEntityToBookResponse(final BookEntity bookEntity);

    public String bookEntityToBookName(final BookEntity bookEntity) {
        return bookEntity.getName();
    }

    public BookEntity bookIdToBookEntity(final Long bookId) {
        BookEntity bookEntity = new BookEntity();
        bookEntity.setId(bookId);
        return bookEntity;
    }
}
