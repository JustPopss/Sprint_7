package model;

import com.github.javafaker.Faker;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UtilityCourierPOM {

    private static final String BASE_URI = "https://qa-scooter.praktikum-services.ru";

    public String getBaseUri() {
        return BASE_URI;
    }

    public static String courierEndpoint = "/api/v1/courier";
    public static String loginEndpoint = "/api/v1/courier/login";
    public static String deleteEndpoint = "/api/v1/courier/";


    static Faker faker = new Faker();
    public static final String LOGIN = faker.name().lastName() + System.currentTimeMillis();
    public static final String PASSWORD = faker.regexify("[0-9]{4}");
    public static final String FIRSTNAME = faker.name().firstName();

    public final String WRONGLOGIN = "@логин";
    public final String WRONGPASSWORD = "#пароль";

    public String getLogin() {
        return LOGIN;
    }

    public String getPassword() {
        return PASSWORD;
    }

    public static Response createCourier(CourierModel courierModel) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(courierModel)
                .when()
                .post(courierEndpoint)
                .then()
                .extract().response();
    }

    public static Response loginCourier(CourierModel courierModel) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(courierModel)
                .when()
                .post(loginEndpoint)
                .then()
                .extract().response();
    }
}
