package entities

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric

class Post {

    Long userId
    Long id
    String title
    String body

    static Post newRandomPost() {
        return new Post(
                userId: randomNumeric(3) as Long,
                title: randomAlphabetic(10),
                body: randomAlphabetic(30)
        )
    }
}
