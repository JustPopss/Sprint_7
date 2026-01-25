import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import model.CourierModel;
import model.UtilityCourierPOM;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_NOT_FOUND;
import static model.UtilityCourierPOM.*;
import static model.UtilityCourierPOM.loginEndpoint;
import static org.hamcrest.Matchers.equalTo;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class InvalidDataCourierTests {

    static UtilityCourierPOM utilityCourierPOM = new UtilityCourierPOM();

    @BeforeClass
    public static void setUp() {
        RestAssured.baseURI = utilityCourierPOM.getBaseUri();
    }

    @Test //если какого-то поля нет (пароль=null), запрос возвращает ошибку;
    @DisplayName("Create courier with Password=null")
    public void aCreateCourierWithNullPasswordTest400() {
        CourierModel courier = new CourierModel(LOGIN, null);
        createCourier(courier)
                .then()
                .log().all()
                .statusCode(HTTP_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));

    }

    @Test //если какого-то поля нет (логин=null), запрос возвращает ошибку;
    @DisplayName("Create courier with Login=null")
    public void bCreateCourierWithNullLoginTest400() {
        CourierModel courier = new CourierModel(null,PASSWORD);
        createCourier(courier)
                .then()
                .log().all()
                .statusCode(HTTP_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));

    }

    @Test //если какого-то поля нет (логин), запрос возвращает ошибку;
    @DisplayName("Create courier without Login field")
    public void cCreateCourierWithoutLoginTest400() {
        given()
                .log().all()
                .contentType(ContentType.JSON)
                .body("{\"password\": \"1234\"}")
                .when()
                .post(courierEndpoint)
                .then()
                .log().all()
                .statusCode(HTTP_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test //если какого-то поля нет (пароль), запрос возвращает ошибку;
    @DisplayName("Create courier without Password field")
    public void dCreateCourierWithoutPasswordTest400() {
        given()
                .log().all()
                .contentType(ContentType.JSON)
                .body("{\"login\": \"login\"}")
                .when()
                .post(courierEndpoint)
                .then()
                .log().all()
                .statusCode(HTTP_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }


    @Test //система вернёт ошибку, если неправильно указать логин;
    @DisplayName("Try to login courier with invalid Login")
    public void eLoginCourierWithInvalidLoginTest404() {
        CourierModel courier = new CourierModel(utilityCourierPOM.WRONGLOGIN, PASSWORD);
        loginCourier(courier)
                .then()
                .log().all()
                .statusCode(HTTP_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test //система вернёт ошибку, если неправильно указать пароль;
    @DisplayName("Try to login courier with invalid Password")
    public void fLoginCourierWithInvalidPasswordTest404() {
        CourierModel courier = new CourierModel(LOGIN, utilityCourierPOM.WRONGPASSWORD);
        loginCourier(courier)
                .then()
                .log().all()
                .statusCode(HTTP_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test //если авторизоваться под несуществующим пользователем, запрос возвращает ошибку;
    @DisplayName("Try to login courier without created")
    public void gLoginCourierWithValidValuesButNotInDBTest404() {
        CourierModel courier = new CourierModel(LOGIN, PASSWORD);
        loginCourier(courier)
                .then()
                .log().all()
                .statusCode(HTTP_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test //если какого-то поля нет (логин), запрос возвращает ошибку;
    @DisplayName("Try to login courier without Login field")
    public void hLoginCourierWithoutLoginTest400() {
        given()
                .log().all()
                .contentType(ContentType.JSON)
                .body("{\"password\": \"1234\"}")
                .when()
                .post(loginEndpoint)
                .then()
                .extract().response()
                .then()
                .log().all()
                .statusCode(HTTP_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test //если какого-то поля нет (пароль), запрос возвращает ошибку;
    //баг? Ответ сервера 504 Gateway time out
    @DisplayName("Try to login courier without Password field")
    public void iLoginCourierWithoutPasswordTest400() {
        given()
                .log().all()
                .contentType(ContentType.JSON)
                .body("{\"login\": \"login\"}")
                .when()
                .post(loginEndpoint)
                .then()
                .extract().response()
                .then()
                .log().all()
                .statusCode(HTTP_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));
    }
}
