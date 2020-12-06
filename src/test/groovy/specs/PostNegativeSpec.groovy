package specs

import groovy.json.JsonBuilder
import io.restassured.response.Response
import services.JSONPlaceholderService
import spock.lang.Ignore
import spock.lang.Issue
import spock.lang.Specification
import spock.lang.Unroll

import java.nio.charset.StandardCharsets

import static io.restassured.http.ContentType.HTML
import static org.apache.http.HttpStatus.SC_INTERNAL_SERVER_ERROR
import static org.apache.http.HttpStatus.SC_NOT_FOUND

@Unroll
class PostNegativeSpec extends Specification {

    def 'should fail creating new post with #description body'() {
        when: 'request to create new post with #description body sends'
            Response response = JSONPlaceholderService.createPost(new JsonBuilder(body).toString())

        then: 'response status code is 500 with HTML content type'
            with(response) {
                statusCode == SC_INTERNAL_SERVER_ERROR
                contentType == HTML
                        .withCharset(StandardCharsets.UTF_8)
                        .toLowerCase()
            }

        where:
            description | body
            'empty'     | ''
            'invalid'   | '{\"null\": null}'
    }

    def 'should fail modifying existing post with #description body'() {
        given: 'existing post with given id'

        when: 'request to modify existing post with given id sends'
            Response response = JSONPlaceholderService.modifyPost(5, new JsonBuilder(body).toString())

        then: 'response status code is 500 with HTML content type'
            with(response) {
                statusCode == SC_INTERNAL_SERVER_ERROR
                contentType == HTML
                        .withCharset(StandardCharsets.UTF_8)
                        .toLowerCase()
            }

        where:
            description | body
            'empty'     | ''
            'invalid'   | '{\"null\": null}'
    }

    @Ignore
    @Issue('Jira ticket number and title here. P.S. such issue is actually expected since API under test is a fake one')
    def 'should fail deleting existing post with incorrect post id'() {
        when: 'request to delete existing post with incorrect id sends'
            Response response = JSONPlaceholderService.deletePost(null)

        then: 'response status code is 404 with HTML content type'
            with(response) {
                statusCode == SC_NOT_FOUND
                contentType == HTML
                        .withCharset(StandardCharsets.UTF_8)
                        .toLowerCase()
            }
    }
}
