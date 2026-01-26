import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.http.ContentType;
import model.OrderModel;
import model.UtilityCourierPOM;
import model.UtilityOrderPOM;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class OrderColorParameterizedTest {

    private static final UtilityCourierPOM utilityCourierPOM = new UtilityCourierPOM();
    private static final String BASE_URL = utilityCourierPOM.getBaseUri();
    private static final String track = UtilityOrderPOM.trackOrderEndpoint;
    private static final String delete = UtilityOrderPOM.deleteOrderEndpoint;

    private final String testName;
    private final OrderModel order;

    public OrderColorParameterizedTest(String testName, OrderModel order) {
        this.testName = testName;
        this.order = order;
    }

    @Parameterized.Parameters(name = "{0}")
    public static Object[][] getTestData() {
        return new Object[][]{
                {"BLACK", new OrderModel(
                        UtilityOrderPOM.FIRSTNAME,
                        UtilityOrderPOM.LASTNAME,
                        UtilityOrderPOM.ADDRES,
                        UtilityOrderPOM.METROSTATION,
                        UtilityOrderPOM.PHONE,
                        UtilityOrderPOM.RENTTIME,
                        UtilityOrderPOM.DELIVERYDATE,
                        UtilityOrderPOM.COMMENT,
                        UtilityOrderPOM.COLORBLACK
                )},
                {"GREY", new OrderModel(
                        UtilityOrderPOM.FIRSTNAME,
                        UtilityOrderPOM.LASTNAME,
                        UtilityOrderPOM.ADDRES,
                        UtilityOrderPOM.METROSTATION,
                        UtilityOrderPOM.PHONE,
                        UtilityOrderPOM.RENTTIME,
                        UtilityOrderPOM.DELIVERYDATE,
                        UtilityOrderPOM.COMMENT,
                        UtilityOrderPOM.COLORGREY
                )},
                {"BLACK + GREY", new OrderModel(
                        UtilityOrderPOM.FIRSTNAME,
                        UtilityOrderPOM.LASTNAME,
                        UtilityOrderPOM.ADDRES,
                        UtilityOrderPOM.METROSTATION,
                        UtilityOrderPOM.PHONE,
                        UtilityOrderPOM.RENTTIME,
                        UtilityOrderPOM.DELIVERYDATE,
                        UtilityOrderPOM.COMMENT,
                        UtilityOrderPOM.COLORBOTH
                )}
        };
    }

    @Test
    @DisplayName("Tests for Color BLACK, GREY, BLACK+GREY")
    public void createOrderTest() {
        Integer id = createOrderStep();
        checkTrackOrderStep(id);
        deleteOrderStep(id);
    }

    @Step("1. Create orders")
    private Integer createOrderStep() {
        return given()
                .baseUri(BASE_URL)
                .log().body()
                .contentType(ContentType.JSON)
                .body(order)
                .post(UtilityOrderPOM.orderEndpoint)
                .then()
                .statusCode(201)
                .body("track", notNullValue())
                .log().body()
                .log().status()
                .extract().path("track");
    }

    @Step("2. Check order by track")
    private void checkTrackOrderStep(Integer id) {
        given()
                .baseUri(BASE_URL)
                .log().body()
                .get(track + id)
                .then()
                .statusCode(200)
                .log().body();
    }

    @Step("3. Delete order by track")
    private void deleteOrderStep(Integer id) {
        given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL)
                .log().all()
                .put(delete + "?track=" + id)
                .then()
                .statusCode(200)
                .log().body();
    }
}