import io.qameta.allure.Step;
import model.OrderModel;
import model.UtilityOrderAPI;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;


@RunWith(Parameterized.class)
public class OrderColorParameterizedTest {

    private final UtilityOrderAPI utilityOrderAPI;
    private final OrderModel order;

    public OrderColorParameterizedTest(String description, OrderModel order) {
        this.order = order;
        this.utilityOrderAPI = new UtilityOrderAPI();
    }


    @Parameterized.Parameters(name = "{0}")
    public static Object[][] getTestData() {
        return new Object[][]{
                {"Тест с цветом: BLACK", new OrderModel(
                        UtilityOrderAPI.FIRSTNAME,
                        UtilityOrderAPI.LASTNAME,
                        UtilityOrderAPI.ADDRES,
                        UtilityOrderAPI.METROSTATION,
                        UtilityOrderAPI.PHONE,
                        UtilityOrderAPI.RENTTIME,
                        UtilityOrderAPI.DELIVERYDATE,
                        UtilityOrderAPI.COMMENT,
                        UtilityOrderAPI.COLORBLACK
                )},
                {"Тест с цветом: GREY", new OrderModel(
                        UtilityOrderAPI.FIRSTNAME,
                        UtilityOrderAPI.LASTNAME,
                        UtilityOrderAPI.ADDRES,
                        UtilityOrderAPI.METROSTATION,
                        UtilityOrderAPI.PHONE,
                        UtilityOrderAPI.RENTTIME,
                        UtilityOrderAPI.DELIVERYDATE,
                        UtilityOrderAPI.COMMENT,
                        UtilityOrderAPI.COLORGREY
                )},
                {"Тест с обоими цветами: BLACK + GREY", new OrderModel(
                        UtilityOrderAPI.FIRSTNAME,
                        UtilityOrderAPI.LASTNAME,
                        UtilityOrderAPI.ADDRES,
                        UtilityOrderAPI.METROSTATION,
                        UtilityOrderAPI.PHONE,
                        UtilityOrderAPI.RENTTIME,
                        UtilityOrderAPI.DELIVERYDATE,
                        UtilityOrderAPI.COMMENT,
                        UtilityOrderAPI.COLORBOTH
                )}
        };
    }

    @Test
    @Step("Create orders")
    public void createOrderTest() {
        utilityOrderAPI.createOrder(this.order);
    }

    @After
    @Step("Delete order by track")
    public void deleteOrderStep() {
        utilityOrderAPI.deleteOrder();
    }
}