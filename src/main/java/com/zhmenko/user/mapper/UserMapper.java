package com.zhmenko.user.mapper;

import com.zhmenko.author.mapper.AuthorMapper;
import com.zhmenko.book.mapper.BookCollectionMapper;
import com.zhmenko.book.mapper.BookMapper;
import com.zhmenko.user.data.model.UserEntity;
import com.zhmenko.user.model.UserInsertRequest;
import com.zhmenko.user.model.UserModifyRequest;
import com.zhmenko.user.model.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.JSR330, uses = {AuthorMapper.class, BookMapper.class, BookCollectionMapper.class})
public interface UserMapper {
    UserEntity userInsertRequestToUserEntity(UserInsertRequest userInsertRequest);

    UserEntity userModifyRequestToUserEntity(UserModifyRequest userModifyRequest);

    @Mapping(source = "bookEntitySet", target = "booksName")
    UserResponse userEntityToUserResponse(UserEntity userEntity);
}
