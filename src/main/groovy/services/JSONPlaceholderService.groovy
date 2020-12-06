package services

import io.restassured.builder.RequestSpecBuilder
import io.restassured.filter.log.RequestLoggingFilter
import io.restassured.filter.log.ResponseLoggingFilter
import io.restassured.http.ContentType
import io.restassured.response.Response
import io.restassured.specification.RequestSpecification

import static config.Config.envConfig
import static io.restassured.RestAssured.given

class JSONPlaceholderService {

    private static final String POSTS_PATH = '/posts/'

    static Response getAllPosts() {
        given()
                .spec(RequestSpec.standardSpec())
        .when()
                .get(POSTS_PATH)
    }

    static Response createPost(String requestBody) {
        given()
                .spec(RequestSpec.standardSpec())
                .body(requestBody)
        .when()
                .post(POSTS_PATH)
    }

    static Response modifyPost(Integer postId, String requestBody) {
        given()
                .spec(RequestSpec.standardSpec())
                .body(requestBody)
        .when()
                .put(POSTS_PATH + postId)
    }

    static Response deletePost(Integer postId) {
        given()
                .spec(RequestSpec.standardSpec())
        .when()
                .delete(POSTS_PATH + postId)
    }

    static class RequestSpec {

        static RequestSpecification standardSpec() {
            return new RequestSpecBuilder()
                .setBaseUri(envConfig.baseURL())
                .setContentType(ContentType.JSON)
                .addFilters([new RequestLoggingFilter(), new ResponseLoggingFilter()])
                .build()
        }
    }
}
