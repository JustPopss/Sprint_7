import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;

import model.OrderModel;
import model.UtilityOrderAPI;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class OrderWithoutColorTest {


    private UtilityOrderAPI utilityOrderAPI;
    private OrderModel orderWithoutColor;

    @Before
    public void setUp() {
        this.orderWithoutColor = new OrderModel(
                UtilityOrderAPI.FIRSTNAME,
                UtilityOrderAPI.LASTNAME,
                UtilityOrderAPI.ADDRES,
                UtilityOrderAPI.METROSTATION,
                UtilityOrderAPI.PHONE,
                UtilityOrderAPI.RENTTIME,
                UtilityOrderAPI.DELIVERYDATE,
                UtilityOrderAPI.COMMENT,
                null);
        this.utilityOrderAPI = new UtilityOrderAPI();
    }

    @Test
    @DisplayName("Create order Without colors")
    @Step("Create order without color field")
    public void createOrderWithoutColorTest() {
        utilityOrderAPI.createOrder(orderWithoutColor);
    }

    @After
    @DisplayName("Cancel order Without colors")
    @Step("Delete order without color field")
    public void deleteOrderWithoutColorTest() {
        utilityOrderAPI.deleteOrder();
    }
}