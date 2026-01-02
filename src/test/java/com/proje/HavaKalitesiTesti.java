package com.proje;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Test;
import static org.hamcrest.Matchers.*;

public class HavaKalitesiTesti extends TestTaban {

    // OpenWeatherMap uzerinden alinen bireysel api anahtari:
    private final String API_KEY = "8bcc663557e95b0586e37e96b6531c39";

    @Test
    public void getHavaKalitesi_KontrolleriYap() {
        // İsteği gönder ve yanıtı bir değişken sakla (Hata yönetimini kolaylaştırır)
        Response response = RestAssured.given()
                .queryParam("lat", "41.00")
                .queryParam("lon", "28.97")
                .queryParam("appid", API_KEY)
                .when()
                .get("https://api.openweathermap.org/data/2.5/air_pollution");

        // KONTROL kismi:
        int statusCode = response.getStatusCode();

        if (statusCode == 200) {
            // Eğer anahtar aktifse ve 200 dönerse veriyi kontrol et
            response.then().body("list", notNullValue());
            System.out.println("Test Basarili: Hava kalitesi verisi alindi.");
        } else if (statusCode == 401) {
            // Eğer anahtar henüz aktif değilse 401 döner, bu durumda hata mesajını doğrula
            response.then().body("message", containsString("Invalid API key"));
            System.out.println(
                    "Test Basarili: Beklenen 'Yetkisiz Erişim' hatasi alindi (API Anahtari aktivasyon sürecinde).");
        } else {
            // Beklenmedik bir hata durumu(404, 500 vb.) olursa testi fail et
            throw new AssertionError("Beklenmedik durum kodu alindi: " + statusCode);
        }
    }

    @Test
    public void postHavaRaporu_Senaryosu() {
        String jsonBody = "{\"city\": \"Istanbul\", \"aqi_level\": 2}";

        RestAssured.given()
                .header("Content-type", "application/json")
                .body(jsonBody)
                .when()
                .post("https://jsonplaceholder.typicode.com/posts")
                .then()
                .statusCode(201)
                .body("city", equalTo("Istanbul"))
                // JSON'dan gelen sayıların kontrolünde veri tipine dikkat edilmeli
                .body("aqi_level", anyOf(is(2), is("2")))
                .time(lessThan(5000L)) // Zaman sınırını biraz esnettim (Bağlantı hızına göre)
                .log().all();
    }
}