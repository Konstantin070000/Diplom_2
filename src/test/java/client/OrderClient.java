package client;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.Order;

public class OrderClient extends BaseClient {

    private static final String ORDERS_PATH = "/api/orders";

    @Step("Создание заказа без авторизации")
    public Response createOrder(Order order) {
        return getBaseSpec()
                .body(order)
                .when()
                .post(ORDERS_PATH);
    }

    @Step("Создание заказа с авторизацией")
    public Response createOrderWithAuth(Order order, String accessToken) {
        return getBaseSpec()
                .header("Authorization", accessToken)
                .body(order)
                .when()
                .post(ORDERS_PATH);
    }
}