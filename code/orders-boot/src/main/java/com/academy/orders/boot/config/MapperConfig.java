package com.academy.orders.boot.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class MapperConfig {
	@Primary
	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		SimpleModule simpleModule = new SimpleModule();
		simpleModule.addSerializer(OffsetDateTime.class, new JsonSerializer<>() {
			@Override
			public void serialize(OffsetDateTime offsetDateTime, JsonGenerator jsonGenerator,
					SerializerProvider serializerProvider) throws IOException {
				jsonGenerator.writeString(DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(offsetDateTime));
			}
		});
		objectMapper.registerModule(simpleModule);

		return objectMapper;
	}
}
