package com.academy.orders.boot.infrastructure.common;

import com.academy.orders.boot.Application;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest(classes = {Application.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("it")
public abstract class AbstractRepository {

}
