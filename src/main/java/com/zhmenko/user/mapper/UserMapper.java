package com.zhmenko.user.mapper;

import com.zhmenko.author.mapper.AuthorMapper;
import com.zhmenko.book.mapper.BookCollectionMapper;
import com.zhmenko.book.mapper.BookMapper;
import com.zhmenko.user.data.model.UserEntity;
import com.zhmenko.user.model.request.UserInsertRequest;
import com.zhmenko.user.model.request.UserModifyRequest;
import com.zhmenko.user.model.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.JSR330,
        uses = {AuthorMapper.class, BookMapper.class, BookCollectionMapper.class,
                BillingDetailsMapper.class, BillingDetailsCollectionMapper.class})
public abstract class UserMapper {
    public abstract UserEntity userInsertRequestToUserEntity(UserInsertRequest userInsertRequest);

    public abstract UserEntity userModifyRequestToUserEntity(UserModifyRequest userModifyRequest);

    @Mapping(source = "bookEntitySet", target = "booksName")
    @Mapping(source = "billingDetailsSet", target = "billingDetails")
    public abstract UserResponse userEntityToUserResponse(UserEntity userEntity);

    public Long userEntityToUserId(UserEntity userEntity) {
        return userEntity.getId();
    }

    public UserEntity userEntityToUserId(Long userId) {
        UserEntity user = new UserEntity();
        user.setId(userId);
        return user;
    }
}
