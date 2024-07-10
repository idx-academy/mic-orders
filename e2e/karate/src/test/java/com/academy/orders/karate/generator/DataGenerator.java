package com.academy.orders.karate.generator;

import com.github.javafaker.Faker;
import java.util.UUID;

public class DataGenerator {
    private static final Faker faker = new Faker();

    public static String generateEmail(){
        return faker.internet().emailAddress();
    }

    public static String generateUUID(){
        return UUID.randomUUID().toString();
    }
}
