package praktikum;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserClient {

    public UserClient() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site/api";
    }

    public Response login(String email, String password) {
        return given()
                .header("Content-type", "application/json")
                .body("{\"email\": \"" + email + "\", \"password\": \"" + password + "\"}")
                .post("/auth/login");
    }

    public void deleteUser(String accessToken) {
        given()
                .header("Authorization", accessToken)
                .delete("/auth/user")
                .then()
                .statusCode(202);
    }
}
