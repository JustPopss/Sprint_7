import io.qameta.allure.junit4.DisplayName;
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
    @DisplayName("Try to login courier with invalid Login")
    public void loginCourierWithInvalidLoginTest404() {
        CourierModel courier = new CourierModel(utilityCourierAPI.WRONGLOGIN, PASSWORD);
        utilityCourierAPI.loginCourierExpectStatus_404_NOT_FOUND(courier);
    }

    @Test   //Login. Система вернёт ошибку, если неправильно указать пароль;
    @DisplayName("Try to login courier with invalid Password")
    public void loginCourierWithInvalidPasswordTest404() {
        CourierModel courier = new CourierModel(LOGIN, utilityCourierAPI.WRONGPASSWORD);
         utilityCourierAPI.loginCourierExpectStatus_404_NOT_FOUND(courier);
    }

    @Test //Login. Если авторизоваться под несуществующим пользователем, запрос возвращает ошибку;
    @DisplayName("Try to login courier without created")
    public void loginCourierWithValidValuesButNotInDBTest404() {
        CourierModel courier = new CourierModel(LOGIN, PASSWORD);
        utilityCourierAPI.loginCourierExpectStatus_404_NOT_FOUND(courier);
    }

    @Test //Login. Если какого-то поля нет (логин), запрос возвращает ошибку;
    @DisplayName("Try to login courier without Login field")
    public void loginCourierWithoutLoginTest400() {
        CourierModel courier = new CourierModel(null, PASSWORD);
        utilityCourierAPI.createCourierExpectStatus_400_BAD_REQUEST(courier);
    }

    @Test //Login. Если какого-то поля нет (пароль), запрос возвращает ошибку;
    //баг? В какие-то моменты ответ сервера 504 Gateway time out
    @DisplayName("Try to login courier without Password field")
    public void loginCourierWithoutPasswordTest400() {
        CourierModel courier = new CourierModel(LOGIN, null);
        utilityCourierAPI.createCourierExpectStatus_400_BAD_REQUEST(courier);
    }
}
