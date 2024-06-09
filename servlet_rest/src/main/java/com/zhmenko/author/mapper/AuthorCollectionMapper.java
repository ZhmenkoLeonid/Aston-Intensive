package com.zhmenko.author.mapper;

import com.zhmenko.author.data.model.AuthorEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.JSR330, uses = AuthorMapper.class)
public interface AuthorCollectionMapper {
    List<String> authorEntityCollectionToAuthorNameList(Collection<AuthorEntity> authorEntities);
}
