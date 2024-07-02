package com.academy.orders.domain.common;

import lombok.Builder;
import java.util.List;

@Builder
public record Pageable(Integer page, Integer size, List<String> sort) {
}
