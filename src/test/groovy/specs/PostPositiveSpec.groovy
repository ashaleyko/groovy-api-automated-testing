package specs

import entities.Post
import groovy.json.JsonBuilder
import io.restassured.response.Response
import services.JSONPlaceholderService
import spock.lang.Specification

import java.nio.charset.StandardCharsets

import static entities.Post.newRandomPost
import static io.restassured.http.ContentType.JSON
import static org.apache.http.HttpStatus.SC_CREATED
import static org.apache.http.HttpStatus.SC_OK

class PostPositiveSpec extends Specification {

    def 'should return all posts'() {
        when: 'request to get all posts sends'
            Response response = JSONPlaceholderService.getAllPosts()
            List<Post> responsePosts = response.body.jsonPath().getList('.') as ArrayList<Post>

        then: 'response status code is 200 with JSON content type'
            with(response) {
                statusCode == SC_OK
                contentType == JSON
                        .withCharset(StandardCharsets.UTF_8)
                        .toLowerCase()
            }

        and: 'one hundred posts are returned'
            responsePosts.size == 100

        and: 'each post title is not empty'
            responsePosts.every { !it.title.isEmpty() }

        and: 'each post body is not empty'
            responsePosts.every { !it.body.isEmpty() }
    }

    def 'should create new post'() {
        given: 'new random post'
            Post newPost = newRandomPost()

        when: 'request to create new post sends'
            Response response = JSONPlaceholderService.createPost(new JsonBuilder(newPost).toString())
            Post responsePost = response.body.jsonPath().get() as Post

        then: 'response status code is 201 with JSON content type'
            with(response) {
                statusCode == SC_CREATED
                contentType == JSON
                    .withCharset(StandardCharsets.UTF_8)
                    .toLowerCase()
        }

        and: 'returned post is the same as created one'
            with(responsePost) {
                userId == newPost.userId
                id == 101
                title == newPost.title
                body == newPost.body
            }
    }

    def 'should modify existing post'() {
        given: 'existing post with given id'

        and: 'new random post'
            Post newPost = newRandomPost()

        when: 'request to modify existing post with given id sends'
            Response response = JSONPlaceholderService.modifyPost(5, new JsonBuilder(newPost).toString())
            Post responsePost = response.body.jsonPath().get() as Post

        then: 'response status code is 200 with JSON content type'
            with(response) {
                statusCode == SC_OK
                contentType == JSON
                        .withCharset(StandardCharsets.UTF_8)
                        .toLowerCase()
            }

        and: 'returned post with given id is modified'
            with(responsePost) {
                userId == newPost.userId
                id == 5
                title == newPost.title
                body == newPost.body
            }
    }

    def 'should delete existing post'() {
        given: 'existing post with given id'

        when: 'request to delete existing post with given id sends'
            Response response = JSONPlaceholderService.deletePost(5)

        then: 'response status code is 200'
            with(response) {
                statusCode == SC_OK
            }
    }
}
