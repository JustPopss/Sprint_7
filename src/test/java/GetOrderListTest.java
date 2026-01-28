import io.qameta.allure.Step;
import io.restassured.RestAssured;
import model.CourierModel;
import model.UtilityCourierAPI;
import model.UtilityOrderAPI;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static model.UtilityCourierAPI.*;

public class GetOrderListTest {

    static UtilityCourierAPI utilityCourierAPI = new UtilityCourierAPI();
    private final CourierModel courier = new CourierModel(LOGIN, PASSWORD, FIRSTNAME);

    private UtilityOrderAPI utilityOrderAPI;
    private int id;

    @BeforeClass
    public static void setUp() {
        RestAssured.baseURI = utilityCourierAPI.getBaseUri();
    }

    @Before
    @Step("Create and login courier")
    public void setUpData() {
        utilityCourierAPI.createCourierExpectStatus200OK(courier);
        utilityCourierAPI.loginCourierExpectStatus200OK(courier);
        this.utilityOrderAPI = new UtilityOrderAPI();
    }

    @Test
    @Step("Get order list for courier")
    public void createOrderTest() {
        utilityOrderAPI.getOrderListExpectStatus200OK(utilityCourierAPI.getId());
    }

    @After
    @Step("Delete courier")
    public void deleteOrderTest() {
        utilityCourierAPI.deleteCourierExpectStatus200OK();
    }
}