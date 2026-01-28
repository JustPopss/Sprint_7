import io.qameta.allure.Step;
import io.restassured.RestAssured;
import model.CourierModel;
import model.UtilityCourierAPI;
import org.junit.BeforeClass;
import org.junit.Test;
import static model.UtilityCourierAPI.*;


public class CreateCourierWithInvalidDataTests {

    static UtilityCourierAPI utilityCourierAPI = new UtilityCourierAPI();

    @BeforeClass
    public static void setUp() {
        RestAssured.baseURI = utilityCourierAPI.getBaseUri();
    }

    // с RestAssured 5.0+, null-поля исключаются из JSON по умолчанию
    // поэтому нет смысла делать поле=null \ отсутствие поля
    // В ТЗ нет понятия какой логин и пароль является неверным
    // Невозможно сделать тесты на невалидные логин и пароль

    @Test //Create. Если какого-то поля нет (пароль=null), запрос возвращает ошибку;
    @Step("Create courier with password=null")
    public void createCourierWithNullPasswordTest400() {
        CourierModel courier = new CourierModel(LOGIN, null);
        utilityCourierAPI.createCourierExpectStatus400BADREQUEST(courier);
    }

    @Test //Create. Если какого-то поля нет (логин=null), запрос возвращает ошибку;
    @Step("Create courier with Login=null")
    public void createCourierWithNullLoginTest400() {
        CourierModel courier = new CourierModel(null,PASSWORD);
        utilityCourierAPI.createCourierExpectStatus400BADREQUEST(courier);
    }
}
