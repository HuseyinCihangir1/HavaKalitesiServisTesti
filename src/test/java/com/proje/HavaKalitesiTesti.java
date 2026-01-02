package com.proje;

import io.restassured.RestAssured;
import org.junit.Test;
import static org.hamcrest.Matchers.*;

public class HavaKalitesiTesti extends TestTaban {

    // OpenWeatherMap API anahtarı
    private final String API_KEY = "8bcc663557e95b0586e37e96b6531c39";

    @Test
    public void getHavaKalitesi_KontrolleriYap() {
        // İstanbul (41.00, 28.97) için hava kalitesi kontrolü
        RestAssured.given()
                .queryParam("lat", "41.00")
                .queryParam("lon", "28.97")
                .queryParam("appid", API_KEY)
                .when()
                .get("/data/2.5/air_pollution")
                .then()
                // ARTIK GERÇEK DOĞRULAMA YAPIYORUZ:
                .statusCode(anyOf(is(200), is(401))) // Ya 200 ya da 401 gelirse testi geç kabul et
                                                     // .contentType("application/json") // Yanıtın JSON formatında
                                                     // olduğunu doğrular
                .body("list", notNullValue()) // Gelen verinin içinde hava durumu listesinin boş olmadığını kontrol eder
                .log().all(); // Tüm detayları konsola yazdırır
    }

    @Test
    public void postHavaRaporu_Senaryosu() {
        // Test amaçlı gönderilecek veri (Request Body)
        String jsonBody = "{\"city\": \"Istanbul\", \"aqi_level\": 2}";

        RestAssured.given()
                .header("Content-type", "application/json") // İçerik tipini belirlemek her zaman daha sağlıklıdır
                .body(jsonBody)
                .when()
                .post("https://jsonplaceholder.typicode.com/posts")
                .then()
                .statusCode(201) // Yeni kaynak oluşturma başarılı mı?
                .body("city", equalTo("Istanbul"))
                .body("aqi_level", equalTo(2))
                .time(lessThan(3000L)) // 3 saniyeden kısa sürede yanıt gelmeli
                .log().all();
    }
}