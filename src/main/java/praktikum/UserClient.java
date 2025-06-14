package praktikum;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class UserClient {

    public UserClient() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site/api";
    }

    //регистрируемся
    public Response login(String email, String password) {
        Map<String, String> credentials = new HashMap<>();
        credentials.put("email", email);
        credentials.put("password", password);
        return given()
                .header("Content-type", "application/json")
                .body(credentials)
                .post("/auth/login");
    }

// Создаем пользователя
    public String createUserAndGetToken(String name, String email, String password) {
        Map<String, String> body = new HashMap<>();
        body.put("email", email);
        body.put("password", password);
        body.put("name", name);

        Response response = given()
                .header("Content-Type", "application/json")
                .body(body)
                .post("/auth/register");

        // Извлекаем токен из JSON-ответа нужен для удаления пользователя
        String accessToken = response.jsonPath().getString("accessToken");
        return accessToken;
    }
    //Удаляем пользователя
    public void deleteUser(String accessToken) {
        given()
                .header("Authorization", accessToken)
                .delete("/auth/user")
                .then();

    }

}
