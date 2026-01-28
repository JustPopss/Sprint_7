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
    CourierModel courier = new CourierModel(LOGIN, PASSWORD, FIRSTNAME);
    private int id;

    @BeforeClass
    public static void setUp() {
        RestAssured.baseURI = utilityCourierAPI.getBaseUri();

    }

    @Before
    @DisplayName("Create and delete new courier with valid data")
    @Step("Create and extract id")
    public void createNewCourierTest201() {
        utilityCourierAPI.createCourierExpectStatus200OK(courier);
    }

    @Test
    @Step("Login courier by id")
    public void loginNewCourierTest200() {
        utilityCourierAPI.loginCourierExpectStatus200OK(courier);
    }

    @After
    @Step("Delete a new courier with id")
    public void deleteCourierTest200() {
        utilityCourierAPI.deleteCourierExpectStatus200OK();
    }
}
