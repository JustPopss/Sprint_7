package model;

import com.github.javafaker.Faker;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class UtilityCourierAPI {

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

    private Integer id;

    public String getLogin() {
        return LOGIN;
    }

    public String getPassword() {
        return PASSWORD;
    }

    public void createCourierExpectStatus_200_OK(CourierModel courierModel) {

        given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(courierModel)
                .when()
                .post(COURIER_ENDPOINT)
                .then()
                .log().body()
                .statusCode(HTTP_CREATED)
                .body("ok", equalTo(true));
    }

    public void createCourierExpectStatus_400_BAD_REQUEST(CourierModel courierModel) {

        given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(courierModel)
                .when()
                .post(COURIER_ENDPOINT)
                .then()
                .log().body()
                .statusCode(HTTP_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }


    public void loginCourierExpectStatus_200_OK(CourierModel courierModel) {
        id = given()
                .log().body()
                .contentType(ContentType.JSON)
                .body(courierModel)
                .when()
                .post(LOGIN_COURIER_ENDPOINT)
                .then()
                .log().body()
                .statusCode(HTTP_OK)
                .extract().path("id");
        assertThat(id, greaterThan(0));
    }

    public void loginCourierExpectStatus_404_NOT_FOUND(CourierModel courierModel) {
        given()
                .log().body()
                .contentType(ContentType.JSON)
                .body(courierModel)
                .when()
                .post(LOGIN_COURIER_ENDPOINT)
                .then()
                .log().body()
                .statusCode(HTTP_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));

    }

    public void loginCourierExpectStatus_400_BAD_REQUEST(CourierModel courierModel) {
        given()
                .log().body()
                .contentType(ContentType.JSON)
                .body("{\"password\": \"1234\"}")
                .when()
                .post(LOGIN_COURIER_ENDPOINT)
                .then()
                .log().body()
                .statusCode(HTTP_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));

    }

    public void deleteCourierExpectStatus_200_OK() {
        given()
                .log().uri()
                .contentType(ContentType.JSON)
                .delete(DELETE_COURIER_ENDPOINT + id)
                .then()
                .log().body()
                .statusCode(HTTP_OK);
    }

    public void createDoubleCourierExpectStatus_409_CONFLICT(CourierModel courierModel) {

        given()
                .log().body()
                .contentType(ContentType.JSON)
                .body(courierModel)
                .post(COURIER_ENDPOINT)
                .then()
                .log().all()
                .statusCode(HTTP_CONFLICT)
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));;
    }
}




