package model;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static model.UtilityCourierPOM.faker;

public class UtilityOrderPOM {

    public static String orderEndpoint = "/api/v1/orders";
    public static String trackOrderEndpoint = "/api/v1/orders/track?t=";
    public static String deleteOrderEndpoint = "/api/v1/orders/cancel";


    static Faker faker = new Faker();

    public static final String FIRSTNAME = faker.name().firstName() + System.currentTimeMillis();
    public static final String LASTNAME = faker.name().lastName() + System.currentTimeMillis();
    public static final String ADDRES = faker.address().cityName();
    public static final String METROSTATION = faker.regexify("[1-2]{2}");
    public static final String PHONE = faker.numerify("+7 9## ### ####");
    public static final Integer RENTTIME = Integer.valueOf(faker.regexify("[0-9]{1}"));
    public static final String DELIVERYDATE = LocalDate.now().plusDays(new Random().nextInt(10) + 1).format(DateTimeFormatter.ISO_DATE);
    public static final String COMMENT = faker.lorem().sentence(3, 5);
    public static final List<String> COLORBLACK = Arrays.asList("BLACK");
    public static final List<String> COLORGREY = Arrays.asList("GREY");
    public static final List<String> COLORBOTH = Arrays.asList("BLACK", "GREY");
    public static final List<String> COLOREMPTY = Arrays.asList();
}

