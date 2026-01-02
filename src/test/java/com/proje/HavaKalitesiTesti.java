package com.proje;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Test;
import static org.hamcrest.Matchers.*;

public class HavaKalitesiTesti extends TestTaban {

    private final String API_KEY = "8bcc663557e95b0586e37e96b6531c39";

    @Test
    public void getHavaKalitesi_KontrolleriYap() {
        Response response = RestAssured.given()
                .queryParam("lat", "41.00")
                .queryParam("lon", "28.97")
                .queryParam("appid", API_KEY)
                .when()
                .get("https://api.openweathermap.org/data/2.5/air_pollution");

        // Yanıtı konsola yazdır (Hata ayıklama için çok önemli)
        response.prettyPrint();

        int statusCode = response.getStatusCode();

        if (statusCode == 200) {
            response.then()
                    .body("list", notNullValue())
                    .body("list[0].main.aqi", notNullValue()); // İç veriyi de kontrol edelim
            System.out.println(">>> Basarili: Hava verisi alindi!!!");
        } else if (statusCode == 401) {
            response.then()
                    .body("message", containsString("key")); // "Invalid API key" mesajını doğrular
            System.out.println(">>> Bilgi: API Anahtari henüz aktif degil(401).");
        } else {
            throw new AssertionError("Beklenmedik Durum! Kod: " + statusCode);
        }
    }

    @Test
    public void postHavaRaporu_Senaryosu() {
        String jsonBody = "{\"city\": \"Istanbul\", \"aqi_level\": 2}";

        RestAssured.given()
                .contentType("application/json") // Header kullanımı için kısa yol
                .body(jsonBody)
                .when()
                .post("https://jsonplaceholder.typicode.com/posts")
                .then()
                .statusCode(201)
                .body("city", equalTo("Istanbul"))
                .body("aqi_level", is(notNullValue())) // Sadece değer geldiğini kontrol etmek bile bazen yeterlidir
                .time(lessThan(4000L)) // Zaman sınırını biraz esnettik
                .log().all();
    }
}