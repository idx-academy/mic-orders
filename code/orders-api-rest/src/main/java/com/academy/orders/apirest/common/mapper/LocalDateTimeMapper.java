package com.academy.orders.apirest.common.mapper;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LocalDateTimeMapper {
	default OffsetDateTime map(LocalDateTime value) {
		return OffsetDateTime.of(value, ZoneOffset.UTC);
	}
}
