import io.qameta.allure.Step;
import io.restassured.RestAssured;
import model.CourierModel;
import model.UtilityCourierAPI;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static model.UtilityCourierAPI.*;


public class CreateDoubleCourierTest {

    static UtilityCourierAPI utilityCourierAPI = new UtilityCourierAPI();
    CourierModel courier = new CourierModel(LOGIN, PASSWORD, FIRSTNAME);
    private static int id;

    @BeforeClass
    public static void setUp() {
        RestAssured.baseURI = utilityCourierAPI.getBaseUri();
    }

    @Before
    @Step("Create and login new courier with valid data")
    public void createNewCourierTest201() {
        utilityCourierAPI.createCourierExpectStatus200OK(courier);
        utilityCourierAPI.loginCourierExpectStatus200OK(courier);
    }

    @Test
    @Step("Try to create courier when courier already created")
    public void tryToCreateDoubleCourierTest409() {
        utilityCourierAPI.createDoubleCourierExpectStatus409CONFLICT(courier);
    }

    @After
    @Step("Deleting courier with id")
    public void deleteCourier() {
        utilityCourierAPI.deleteCourierExpectStatus200OK();
    }
}
