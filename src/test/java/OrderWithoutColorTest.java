import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.http.ContentType;

import model.UtilityCourierPOM;
import model.UtilityOrderPOM;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class OrderWithoutColorTest {

    private static final UtilityCourierPOM utilityCourierPOM = new UtilityCourierPOM();
    private static final String BASE_URL = utilityCourierPOM.getBaseUri();
    private static final String delete = UtilityOrderPOM.deleteOrderEndpoint;

    @Test
    @DisplayName("Create orders without Color field")
    public void createOrderWithoutColorField() {
        Integer id = createOrderWithoutColorStep();
        deleteOrderWithoutColorStep(id);
    }

    @Step("1. Create order without color field")
    private Integer createOrderWithoutColorStep() {
        String jsonWithoutColor = "{" +
                "\"firstName\":\"" + UtilityOrderPOM.FIRSTNAME + "\"," +
                "\"lastName\":\"" + UtilityOrderPOM.LASTNAME + "\"," +
                "\"address\":\"" + UtilityOrderPOM.ADDRES + "\"," +
                "\"metroStation\":\"" + UtilityOrderPOM.METROSTATION + "\"," +
                "\"phone\":\"" + UtilityOrderPOM.PHONE + "\"," +
                "\"rentTime\":" + UtilityOrderPOM.RENTTIME + "," +
                "\"deliveryDate\":\"" + UtilityOrderPOM.DELIVERYDATE + "\"," +
                "\"comment\":\"" + UtilityOrderPOM.COMMENT + "\"" +
                "}";

        return given()
                .baseUri(BASE_URL)
                .log().body()
                .contentType(ContentType.JSON)
                .body(jsonWithoutColor)
                .post(UtilityOrderPOM.orderEndpoint)
                .then()
                .statusCode(201)
                .log().body()
                .log().status()
                .body("track", notNullValue())
                .extract().path("track");
    }

    @Step("2. Delete order without color field")
    private void deleteOrderWithoutColorStep(Integer id) {
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