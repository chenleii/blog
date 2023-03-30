package com.chen.blog.infrastructure.persistence.repository.domainconverter;

import com.chen.blog.core.sharedkernel.converter.Converter;
import org.javamoney.moneta.Money;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

import javax.money.MonetaryAmount;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * @author cl
 * @version 1.0
 * @since 2021/7/1 18:35
 */
@Mapper(componentModel = MappingConstants.ComponentModel.DEFAULT)
public interface MonetaryMapper extends Converter {
    MonetaryMapper MAPPER = Mappers.getMapper(MonetaryMapper.class);

    default BigDecimal map(MonetaryAmount monetaryAmount) {
        if (Objects.isNull(monetaryAmount)) {
            return null;
        }
        return monetaryAmount.getNumber().numberValueExact(BigDecimal.class);
    }

    default String mapMonetaryAmountCurrencyCode(MonetaryAmount monetaryAmount) {
        if (Objects.isNull(monetaryAmount)) {
            return null;
        }
        return monetaryAmount.getCurrency().getCurrencyCode();
    }

    default MonetaryAmount map(BigDecimal value, String currencyCode) {
        if (Objects.isNull(value) || Objects.isNull(currencyCode)) {

            return null;
        }
        return Money.of(value, currencyCode);
    }

}
