package com.proje;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import org.junit.BeforeClass;

public class TestTaban {

    @BeforeClass
    public static void kurulum() {
        // API'nin temel adresi (Base URL)
        RestAssured.baseURI = "http://api.openweathermap.org";

        // Ortak istek özellikleri: Tüm isteklerin JSON formatında gidip gelmesini
        // sağlar.
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .build();

        System.out.println("--- Test Hazirligi: BaseURI ve RequestSpecBuilder ayarlandi! ---");
    }
}