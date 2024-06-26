package com.zhmenko.user.mapper;

import com.zhmenko.user.data.model.BankAccountEntity;
import com.zhmenko.user.model.request.BankAccountInsertRequest;
import com.zhmenko.user.model.response.BankAccountResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.JSR330,
        uses = UserMapper.class)
public interface BankAccountMapper {
    @Mapping(source = "userId", target = "user")
    BankAccountEntity toEntity(BankAccountInsertRequest bankAccountRequest);

    @Mapping(source = "user", target = "userId")
    BankAccountResponse toDto(BankAccountEntity bankAccountEntity);
}