package com.juaracoding;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class TmdbTest {

//    String baseUrl = "https://api.themoviedb.org/3";

    String myToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJkM2UzZjc5ZGVmY2FmMDA0ZTE3NjJhYzM3M2NlYjllOCIsIm5iZiI6MTcyOTg1ODAyNC45NDIwOTcsInN1YiI6IjY3MTkwOGJjNDI3YzVjMTlmMDI1YTI0YSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.8H35Wf1wVGC62QjV0_aj2Li51zhexTVrZ2UM1lJq49o";

    @BeforeClass
    public void setUp(){
        RestAssured.baseURI = "https://api.themoviedb.org/3";
    }

    @Test
    public void testMovieTopRated() {
        /* Cara Static given() */
        /* given()
        given()
                .queryParam("language","en-US")
                .queryParam("page","2")
                .header("Authorization",myToken)
                .when()
                    .get(baseUrl+"/movie/top_rated")
                .then()
                    .statusCode(200)
                    .body("page",equalTo(2))
                    .body("results.title[0]",equalTo("Life Is Beautiful"))
                    .log().all();
    }*/

        /* Cara Response Json Path */
        RequestSpecification request = RestAssured.given();
        request.queryParam("language", "en-US");
        request.queryParam("page", "2");
        request.header("Authorization", myToken);
        Response response = request.get("/movie/top_rated");

        int statusCode = response.statusCode();
        int page = response.getBody().jsonPath().getInt("page");
        String title = response.getBody().jsonPath().getString("results.title[0]");
        System.out.println(statusCode);
        System.out.println(page);
        System.out.println(title);
        Assert.assertEquals(statusCode, 200);
        Assert.assertEquals(page, 2);
        Assert.assertEquals(title, "Life Is Beautiful");
    }
}
