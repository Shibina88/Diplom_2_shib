import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import order.OrderRequest;
import user.UserCreds;

import static io.restassured.RestAssured.given;

public class OrderClient {
    private static final String ORDER_PATH = "api/orders";

    public OrderClient() {
        RestAssured.baseURI = Constants.BASE_URI;
    }

    @Step("Create order")
    public Response orderCreate(OrderRequest ingredients, UserCreds userCreds) {
        return given()
                .header("Content-type", "application/json")
                .header("Authorization", userCreds.getAccessToken())
                .body(ingredients)
                .when()
                .post(ORDER_PATH);
    }

    @Step("Create order without authorization")
    public Response orderCreateWithoutAuthorization(OrderRequest ingredients) {
        return given()
                .header("Content-type", "application/json")
                .body(ingredients)
                .when()
                .post(ORDER_PATH);
    }

    @Step("Get orders user")
    public Response getOrdersUser(UserCreds userCreds) {
        return given()
                .header("Content-type", "application/json")
                .header("Authorization", userCreds.getAccessToken())
                .when()
                .get(ORDER_PATH);
    }

    @Step("Get orders user")
    public Response getOrdersUserWithoutAuthorization() {
        return given()
                .header("Content-type", "application/json")
                .when()
                .get(ORDER_PATH);
    }
}
