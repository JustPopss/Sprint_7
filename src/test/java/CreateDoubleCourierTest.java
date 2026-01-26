import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import model.CourierModel;
import model.UtilityCourierPOM;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.*;
import static model.UtilityCourierPOM.*;
import static model.UtilityCourierPOM.createCourier;
import static org.hamcrest.Matchers.equalTo;

public class CreatDoubleCourierTest {

    static UtilityCourierPOM utilityCourierPOM = new UtilityCourierPOM();
    String password = utilityCourierPOM.getPassword();
    String login = utilityCourierPOM.getLogin();
    private static int id;

    @BeforeClass
    public static void setUp() {
        RestAssured.baseURI = utilityCourierPOM.getBaseUri();
    }

    @Before
    @DisplayName("Create new courier with valid data")
    public void createNewCourierTest201() {
        CourierModel courier = new CourierModel(LOGIN, PASSWORD, FIRSTNAME);

        createCourier(courier)
                .then()
                .log().body()
                .statusCode(HTTP_CREATED)
                .body("ok", equalTo(true));

        id = given()
                .log().body()
                .contentType(ContentType.JSON)
                .body(courier)
                .when()
                .post(loginEndpoint)
                .then()
                .log().body()
                .statusCode(HTTP_OK)
                .extract().path("id");
    }

    @Test
    @DisplayName("Try to create courier when courier already created")
    public void bTryToCreateDoubleCourierTest409() {
        CourierModel courier = new CourierModel(LOGIN, PASSWORD, FIRSTNAME);
        createCourier(courier)
                .then()
                .log().body()
                .statusCode(HTTP_CONFLICT)
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @After
    @DisplayName("Deleting courier with id")
    public void deleteCourierTest200() {
        given()
                .log().body()
                .contentType(ContentType.JSON)
                .delete(deleteEndpoint+id)
                .then()
                .log().body()
                .statusCode(HTTP_OK);
    }

}
