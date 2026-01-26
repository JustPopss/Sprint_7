import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import model.CourierModel;
import model.UtilityCourierPOM;
import org.junit.*;
import org.junit.runners.MethodSorters;
import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.*;
import static model.UtilityCourierPOM.*;
import static org.hamcrest.Matchers.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ValidDataCourierTests {

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
                .log().all()
                .statusCode(HTTP_CREATED)
                .body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Try to create courier when courier already created")
    public void bTryToCreateDoubleCourierTest409() {
        CourierModel courier = new CourierModel(LOGIN, PASSWORD, FIRSTNAME);
        createCourier(courier)
                .then()
                .log().all()
                .statusCode(HTTP_CONFLICT)
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));

    }

    //курьер может авторизоваться;
    @Test //нужен для получения id для удаления курьера
    @DisplayName("1 - Login courier with valid data." +
                " 2 - Extract id for delete courier")
    public void cLoginCourierTest200() {
        CourierModel courier = new CourierModel(login, password);

        id = given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(courier)
                .when()
                .post(loginEndpoint)
                .then()
                .log().body()
                .statusCode(HTTP_OK)
                .extract().path("id");
    }

    @After
    @DisplayName("Deleting courier with id")
    public void dDeleteCourierTest200() {
        given()
                .log().all()
                .contentType(ContentType.JSON)
                .delete(deleteEndpoint+id)
                .then()
                .log().all()
                .statusCode(HTTP_OK);
    }
}
