package com.zhmenko.user.mapper;

import com.zhmenko.user.data.model.CreditCardEntity;
import com.zhmenko.user.model.request.CreditCardInsertRequest;
import com.zhmenko.user.model.response.CreditCardResponse;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.JSR330,
uses = UserMapper.class)
public interface CreditCardMapper {
    @Mapping(source = "userId", target = "user")
    CreditCardEntity toEntity(CreditCardInsertRequest creditCardRequest);

    @Mapping(source = "user", target = "userId")
    CreditCardResponse toDto(CreditCardEntity creditCardEntity);
}