import io.qameta.allure.junit4.DisplayName;
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
    @DisplayName("Create new courier with valid data")
    public void createNewCourierTest201() {
        utilityCourierAPI.createCourierExpectStatus_200_OK(courier);
        utilityCourierAPI.loginCourierExpectStatus_200_OK(courier);
    }

    @Test
    @DisplayName("Try to create courier when courier already created")
    public void tryToCreateDoubleCourierTest409() {
        utilityCourierAPI.createDoubleCourierExpectStatus_409_CONFLICT(courier);
    }

    @After
    @DisplayName("Deleting courier with id")
    public void deleteCourier() {
        utilityCourierAPI.deleteCourierExpectStatus_200_OK();
    }
}
