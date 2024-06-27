package com.academy.orders.domain.common;

import java.util.List;

public record Pageable(Integer page, Integer size, List<String> sort) {
}
