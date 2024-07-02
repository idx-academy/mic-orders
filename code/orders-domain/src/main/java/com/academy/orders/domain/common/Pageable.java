package com.academy.orders.domain.common;

import java.util.List;
import lombok.Builder;

@Builder
public record Pageable(Integer page, Integer size, List<String> sort) {
}
