import io.qameta.allure.Step;
import io.restassured.RestAssured;
import model.CourierModel;
import model.UtilityCourierAPI;
import org.junit.BeforeClass;
import org.junit.Test;

import static model.UtilityCourierAPI.*;


public class LoginCourierWithInvalidDataTest {

    static UtilityCourierAPI utilityCourierAPI = new UtilityCourierAPI();

    @BeforeClass
    public static void setUp() {
        RestAssured.baseURI = utilityCourierAPI.getBaseUri();
    }

    // В ТЗ нет понятия какой логин и пароль является неверным
    // Невозможно сделать тесты на невалидные логин и пароль

    @Test   //Login. Система вернёт ошибку, если неправильно указать логин;
    @Step("Try to login courier with invalid Login")
    public void loginCourierWithInvalidLoginTest404() {
        CourierModel courier = new CourierModel(utilityCourierAPI.WRONGLOGIN, PASSWORD);
        utilityCourierAPI.loginCourierExpectStatus404NOTFOUND(courier);
    }

    @Test   //Login. Система вернёт ошибку, если неправильно указать пароль;
    @Step("Try to login courier with invalid Password")
    public void loginCourierWithInvalidPasswordTest404() {
        CourierModel courier = new CourierModel(LOGIN, utilityCourierAPI.WRONGPASSWORD);
         utilityCourierAPI.loginCourierExpectStatus404NOTFOUND(courier);
    }

    @Test //Login. Если авторизоваться под несуществующим пользователем, запрос возвращает ошибку;
    @Step("Try to login courier without created")
    public void loginCourierWithValidValuesButNotInDBTest404() {
        CourierModel courier = new CourierModel(LOGIN, PASSWORD);
        utilityCourierAPI.loginCourierExpectStatus404NOTFOUND(courier);
    }

    @Test //Login. Если какого-то поля нет (логин), запрос возвращает ошибку;
    @Step("Try to login courier without Login field")
    public void loginCourierWithoutLoginTest400() {
        CourierModel courier = new CourierModel(null, PASSWORD);
        utilityCourierAPI.createCourierExpectStatus400BADREQUEST(courier);
    }

    @Test //Login. Если какого-то поля нет (пароль), запрос возвращает ошибку;
    //баг? В какие-то моменты ответ сервера 504 Gateway time out
    @Step("Try to login courier without Password field")
    public void loginCourierWithoutPasswordTest400() {
        CourierModel courier = new CourierModel(LOGIN, null);
        utilityCourierAPI.createCourierExpectStatus400BADREQUEST(courier);
    }
}
