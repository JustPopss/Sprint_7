package model;

import com.github.javafaker.Faker;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class UtilityCourierPOM {

    private static final String BASE_URI = "https://qa-scooter.praktikum-services.ru";


    public String getBaseUri() {
        return BASE_URI;
    }

    public static final String COURIER_ENDPOINT = "/api/v1/courier";
    public static final String LOGIN_COURIER_ENDPOINT = "/api/v1/courier/login";
    public static final String DELETE_COURIER_ENDPOINT = "/api/v1/courier/";

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
                .post(COURIER_ENDPOINT)
                .then()
                .extract().response();
    }

    public static Response loginCourier(CourierModel courierModel) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(courierModel)
                .when()
                .post(LOGIN_COURIER_ENDPOINT)
                .then()
                .extract().response();
    }
}




