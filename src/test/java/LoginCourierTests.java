import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import model.CourierModel;
import model.UtilityCourierAPI;
import org.junit.*;
import org.junit.runners.MethodSorters;
import static model.UtilityCourierAPI.*;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LoginCourierTests {

    static UtilityCourierAPI utilityCourierAPI = new UtilityCourierAPI();

    @BeforeClass
    public static void setUp() {
        RestAssured.baseURI = utilityCourierAPI.getBaseUri();
    }

    @Before
    @DisplayName("Create and delete new courier with valid data")
    @Step("Create and extract id")
    public void createNewCourierTest201() {
        CourierModel courier = new CourierModel(LOGIN, PASSWORD, FIRSTNAME);
        utilityCourierAPI.createCourierExpectStatus_200_OK(courier);
        utilityCourierAPI.loginCourierExpectStatus_200_OK(courier);
    }

    @Test
    @DisplayName("Delete a new courier with id")
    public void deleteCourierTest200() {
        utilityCourierAPI.deleteCourierExpectStatus_200_OK();
    }
}
