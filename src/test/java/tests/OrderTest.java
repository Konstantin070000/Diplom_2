package tests;

import client.OrderClient;
import client.UserClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.Order;
import model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import utils.UserGenerator;

import java.util.Arrays;
import java.util.Collections;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;

public class OrderTest {

    private final OrderClient orderClient = new OrderClient();
    private final UserClient userClient = new UserClient();
    private String accessToken;
    private User user;

    @Before
    public void setUp() {
        user = UserGenerator.getRandomUser();

        Response createUserResponse = userClient.createUser(user);
        accessToken = createUserResponse.jsonPath().getString("accessToken");
    }

    @Test
    @DisplayName("Создание заказа с авторизацией")
    @Description("Проверяем успешное создание заказа авторизованным пользователем")
    public void createOrderWithAuthShouldReturnSuccess() {
        Order order = new Order(Arrays.asList(
                "61c0c5a71d1f82001bdaaa6d",
                "61c0c5a71d1f82001bdaaa6f"
        ));

        Response response = orderClient.createOrderWithAuth(order, accessToken);

        assertEquals(SC_OK, response.statusCode());
    }

    @Test
    @DisplayName("Создание заказа без авторизации")
    @Description("Проверяем, что заказ можно создать без токена")
    public void createOrderWithoutAuthShouldReturnSuccess() {
        Order order = new Order(Arrays.asList(
                "61c0c5a71d1f82001bdaaa6d",
                "61c0c5a71d1f82001bdaaa6f"
        ));

        Response response = orderClient.createOrder(order);

        assertEquals(SC_OK, response.statusCode());
    }

    @Test
    @DisplayName("Создание заказа без ингредиентов")
    @Description("Проверяем, что при отсутствии ингредиентов возвращается ошибка 400")
    public void createOrderWithoutIngredientsShouldReturnError() {
        Order order = new Order(Collections.emptyList());

        Response response = orderClient.createOrder(order);

        assertEquals(SC_BAD_REQUEST, response.statusCode());
        assertEquals("Ingredient ids must be provided", response.jsonPath().getString("message"));
    }

    @Test
    @DisplayName("Создание заказа с неверным хешем ингредиента")
    @Description("Проверяем, что при неверном хеше ингредиента возвращается ошибка сервера")
    public void createOrderWithWrongIngredientHashShouldReturnError() {
        Order order = new Order(Collections.singletonList("wrong_hash"));

        Response response = orderClient.createOrder(order);

        assertEquals(SC_INTERNAL_SERVER_ERROR, response.statusCode());
    }

    @After
    public void cleanUp() {
        if (accessToken != null) {
            userClient.deleteUser(accessToken);
        }
    }
}