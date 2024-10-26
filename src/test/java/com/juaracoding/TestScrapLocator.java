package com.juaracoding;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestScrapLocator {

    @Test
    public void testMessageBody() {
        RestAssured.baseURI = "https://reqres.in/api";
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.queryParam("page", 2).get("/users");

        ResponseBody body = response.getBody();

        System.out.println("Response Body is: " + body.asString());

        JsonPath jsonPathEvaluator = response.jsonPath();

        String page = jsonPathEvaluator.getString("page");
        System.out.println(page);
    }

    @Test
    public void testLoginSuccessful() {
        RestAssured.baseURI = "https://reqres.in/api";
        RequestSpecification request = RestAssured.given();

        JSONObject requestBody = new JSONObject();
        requestBody.put("email", "eve.holt@reqres.in");
        requestBody.put("password", "cityslicka");

        request.header("Content-Type", "application/json");
        request.body(requestBody.toJSONString());

        request.body(requestBody.toJSONString());
        Response response = request.post("/login");

        Assert.assertEquals(response.getStatusCode(), 200);

        String token = response.getBody().jsonPath().getString("token");
        System.out.println(token);
    }

}
