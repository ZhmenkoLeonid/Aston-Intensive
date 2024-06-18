package com.zhmenko.book.mapper;

import com.zhmenko.book.data.model.BookEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.JSR330, uses = BookMapper.class)
public interface BookCollectionMapper {
    List<String> bookEntityCollectionToBookNameList(final Collection<BookEntity> bookEntityCollection);

    List<Integer> bookEntityCollectionToBookIdList(final Collection<BookEntity> bookEntityCollection);
}
