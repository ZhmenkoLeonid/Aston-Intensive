package com.zhmenko.user.mapper;

import com.zhmenko.user.data.model.BankAccountEntity;
import com.zhmenko.user.data.model.BillingDetailsEntity;
import com.zhmenko.user.data.model.CreditCardEntity;
import com.zhmenko.user.model.request.BankAccountInsertRequest;
import com.zhmenko.user.model.request.BillingDetailsInsertRequest;
import com.zhmenko.user.model.request.CreditCardInsertRequest;
import com.zhmenko.user.model.response.BankAccountResponse;
import com.zhmenko.user.model.response.BillingDetailsResponse;
import com.zhmenko.user.model.response.CreditCardResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.SubclassMapping;

@Mapper(componentModel = MappingConstants.ComponentModel.JSR330,
        uses = {UserMapper.class, CreditCardMapper.class, BankAccountMapper.class})
public interface BillingDetailsMapper {
    @SubclassMapping(source = CreditCardInsertRequest.class, target = CreditCardEntity.class)
    @SubclassMapping(source = BankAccountInsertRequest.class, target = BankAccountEntity.class)
    @Mapping(source = "userId", target = "user")
    BillingDetailsEntity toEntity(BillingDetailsInsertRequest billingDetailsInsertRequest);

    @SubclassMapping(source = CreditCardEntity.class, target = CreditCardResponse.class)
    @SubclassMapping(source = BankAccountEntity.class, target = BankAccountResponse.class)
    @Mapping(source = "user", target = "userId")
    BillingDetailsResponse toDto(BillingDetailsEntity billingDetailsEntity);
}