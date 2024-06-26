package com.zhmenko.user.mapper;

import com.zhmenko.user.data.model.BillingDetailsEntity;
import com.zhmenko.user.model.response.BillingDetailsResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.JSR330, uses = BillingDetailsMapper.class)
public interface BillingDetailsCollectionMapper {
    List<BillingDetailsResponse> toDto (Collection<BillingDetailsEntity> billingDetailEntities);
}
