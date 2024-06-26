package com.zhmenko.author.mapper;

import com.zhmenko.author.data.model.AuthorEntity;
import com.zhmenko.author.model.AuthorInsertRequest;
import com.zhmenko.author.model.AuthorModifyRequest;
import com.zhmenko.author.model.AuthorResponse;
import com.zhmenko.book.mapper.BookCollectionMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.JSR330, uses = {BookCollectionMapper.class})
public abstract class AuthorMapper {
    public abstract AuthorEntity authorInsertRequestToAuthorEntity(AuthorInsertRequest authorInsertRequest);

    public abstract AuthorEntity authorModifyRequestToAuthorEntity(AuthorModifyRequest authorModifyRequest);

    @Mapping(source = "bookEntities", target = "booksName")
    public abstract AuthorResponse authorEntityToAuthorResponse(AuthorEntity authorEntity);

    public String authorEntityToAuthorName(AuthorEntity authorEntity) {
        return authorEntity.getSecondName() + " " + authorEntity.getFirstName() + " " + authorEntity.getThirdName();
    }

    public AuthorEntity authorIdToAuthorEntity(Long authorId) {
        AuthorEntity authorEntity = new AuthorEntity();
        authorEntity.setId(authorId);
        return authorEntity;
    }
}
