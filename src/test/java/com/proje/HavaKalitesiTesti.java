package com.proje;

import io.restassured.RestAssured;
import org.junit.Test;
import static org.hamcrest.Matchers.*;

public class HavaKalitesiTesti extends TestTaban {

    private final String API_KEY = "8bcc663557e95b0586e37e96b6531c39"; // Örnek Anahtar

    @Test
    public void getHavaKalitesi_KontrolleriYap() {
        RestAssured.given()
            .queryParam("lat", "41.00") // İstanbul Enlem
            .queryParam("lon", "28.97") // İstanbul Boylam
            .queryParam("appid", API_KEY)
        .when()
            .get("/data/2.5/air_pollution") // BaseURI sonuna eklenir [cite: 9]
        .then()
            .statusCode(200) // 1. Status Code Kontrolü [cite: 6]
            .time(lessThan(3000L)) // 2. Süre Kontrolü (3sn altı) [cite: 8]
            .body("list[0].main.aqi", notNullValue()) // 3. Body Değer Kontrolü [cite: 7]
            .body("list[0].components.co", greaterThan(0.0f)) // Ekstra değer kontrolü
            .log().all();
    }

    @Test
    public void postHavaRaporu_Senaryosu() {
        // Ödevde istenen POST ve Request Body kullanımı [cite: 9]
        String jsonBody = "{\"city\": \"Istanbul\", \"aqi_level\": 2}";

        RestAssured.given()
            .body(jsonBody) // Request Body [cite: 9, 10]
        .when()
            .post("https://jsonplaceholder.typicode.com/posts") // Örnek test sunucusu
        .then()
            .statusCode(201) // Başarılı oluşturma kodu [cite: 6]
            .body("city", equalTo("Istanbul")) // Değer kontrolü [cite: 7]
            .log().all();
    }
}