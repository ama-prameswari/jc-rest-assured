package com.juaracoding;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class UserTest {
//    String baseUrl = "https://reqres.in/api";

    @BeforeClass
    public void setUp(){
        RestAssured.baseURI = "https://reqres.in/api";
    }

    @Test
    public void testGetListUsers() {
        Response response = RestAssured.get("/users?page=2");
        System.out.println(response.getBody().asString());
        System.out.println(response.getStatusCode());
        System.out.println(response.getStatusLine());
        System.out.println(response.getHeader("content-type"));
        System.out.println(response.getTime());
        int actual = response.getStatusCode();
        Assert.assertEquals(actual, 200);
        }

    @Test
    public void testGetSingleUser() {
        given()
                .when() // Send request
                    .get("/users/2")
                .then()
                    .statusCode(200) // validate status code
                    .body("data.id",equalTo(2)); // validate response body
    }

    @Test
    public void testUserNotFound() {
        given()
                .when() // Send request
                    .get("/users/23")
                .then()
                    .statusCode(404); // validate status code
    }

    @Test
    public void testPostCreateUser() {
        JSONObject request = new JSONObject();
        request.put("name", "morpheus");
        request.put("job", "leader");
        System.out.println(request.toJSONString());

        given()
                .header("Content-Type", "application/json")
                .body(request.toJSONString())
                .when() // Send request
                    .post( "/users")
                .then()
                    .statusCode(201) // Validate status code
                    .body("name", equalTo("morpheus")); // validate response body
    }

    @Test
    public void testPutUpdateUser() {
        JSONObject request = new JSONObject();
        request.put("name", "morpheus");
        request.put("job", "zion resident");
        System.out.println(request.toJSONString());

        given()
                .header("Content-Type", "application/json")
                .body(request.toJSONString())
                .when() // Send request
                    .put("/users/2")
                .then()
                    .statusCode(200) // Validate status code
                    .body("name", equalTo("morpheus")) // validate response body
                    .body("job", equalTo("zion resident")) // validate response body
                    .log().all();
    }

    @Test
    public void testDeleteUser() {
        Response response = RestAssured.delete("/users/2");
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 204); // Validate status code
    }

    @Test
    public void testPatchUpdateUser() {
        JSONObject request = new JSONObject();
        request.put("name", "morpheus");
        request.put("job", "zion resident");
        System.out.println(request.toJSONString());

        given()
                .header("Content-Type", "application/json")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(request.toJSONString())
                .when() // Send request
                    .patch("/users/2")
                .then()
                    .statusCode(200) // Validate status code
                    .body("name", equalTo("morpheus")) // validate response body
                    .body("job", equalTo("zion resident")) // validate response body
                    .log().all();
    }


    @Test
    public void testLoginUser(){
        RequestSpecification request = RestAssured.given();
        JSONObject requestBody = new JSONObject();

        requestBody.put("email", "eve.holt@reqres.in");
        requestBody.put("password", "cityslicka");
        request.header("Content-Type", "application/json");
        request.body(requestBody.toJSONString());
        Response response = request.post("/login");
        Assert.assertEquals(response.getStatusCode(), 200);
        String token = response.getBody().jsonPath().getString("token");
        System.out.println(token);
    }



/*
    String endpoint = "https://reqres.in/api/users?page=1";

       @Test
        public void testStatusCode() {
            Response response = RestAssured.get(endpoint);
            System.out.println(response.getBody().asString());
            System.out.println(response.getStatusCode());
            System.out.println(response.getStatusLine());
            System.out.println(response.getHeader("content-type"));
            System.out.println(response.getTime());
            int actual = response.getStatusCode();
            Assert.assertEquals(actual, 200);
        }

       @Test
        public void testIdOne() {
            given()
                    .get(endpoint)
                    .then()
                    .statusCode(200)
                    .body("data.id[0]", equalTo(1));
        }

        @Test
        public void testIdTwo() {
            given()
                    .get(endpoint)
                    .then()
                    .statusCode(200)
                    .body("data.id[1]", equalTo(2));
        }
    */
}
