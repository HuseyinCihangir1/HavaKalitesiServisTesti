package com.proje;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import org.junit.BeforeClass;

public class TestTaban {

    @BeforeClass
    public static void kurulum() {
        // API'nin temel adresi (Base URL) tanımlanıyor [cite: 2, 17]
        RestAssured.baseURI = "http://api.openweathermap.org";
        
        // Ortak istek özellikleri (opsiyonel ama profesyonel bir yaklaşım)
        RestAssured.requestSpecification = new RequestSpecBuilder()
            .setContentType(ContentType.JSON)
            .setAccept(ContentType.JSON)
            .build();
    }
}