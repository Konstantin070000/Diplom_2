package tests;

import client.OrderClient;
import client.UserClient;
import io.restassured.response.Response;
import model.Order;
import model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class OrderTest {

    private final OrderClient orderClient = new OrderClient();
    private final UserClient userClient = new UserClient();
    private String accessToken;
    private User user;

    @Before
    public void setUp() {
        String email = "test" + System.currentTimeMillis() + "@mail.com";
        user = new User(email, "password123", "TestUser");

        Response createUserResponse = userClient.createUser(user);
        accessToken = createUserResponse.jsonPath().getString("accessToken");
    }

    @Test
    public void createOrderWithAuthShouldReturnSuccess() {
        Order order = new Order(Arrays.asList(
                "61c0c5a71d1f82001bdaaa6d",
                "61c0c5a71d1f82001bdaaa6f"
        ));

        Response response = orderClient.createOrderWithAuth(order, accessToken);

        assertEquals(200, response.statusCode());
    }

    @Test
    public void createOrderWithoutAuthShouldReturnSuccess() {
        Order order = new Order(Arrays.asList(
                "61c0c5a71d1f82001bdaaa6d",
                "61c0c5a71d1f82001bdaaa6f"
        ));

        Response response = orderClient.createOrder(order);

        assertEquals(200, response.statusCode());
    }

    @Test
    public void createOrderWithoutIngredientsShouldReturnError() {
        Order order = new Order(Collections.emptyList());

        Response response = orderClient.createOrder(order);

        assertEquals(400, response.statusCode());
        assertEquals("Ingredient ids must be provided", response.jsonPath().getString("message"));
    }

    @Test
    public void createOrderWithWrongIngredientHashShouldReturnError() {
        Order order = new Order(Collections.singletonList("wrong_hash"));

        Response response = orderClient.createOrder(order);

        assertEquals(500, response.statusCode());
    }

    @After
    public void cleanUp() {
        if (accessToken != null) {
            userClient.deleteUser(accessToken);
        }
    }
}