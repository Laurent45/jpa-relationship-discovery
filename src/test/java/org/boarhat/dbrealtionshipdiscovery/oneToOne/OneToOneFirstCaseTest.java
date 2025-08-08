package org.boarhat.dbrealtionshipdiscovery.oneToOne;

import org.boarhat.dbrealtionshipdiscovery.oneToOne.firstCase.Post;
import org.boarhat.dbrealtionshipdiscovery.oneToOne.firstCase.PostDetails;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@EntityScan(basePackages = "org.boarhat.dbrealtionshipdiscovery.oneToOne.firstCase")
public class OneToOneFirstCaseTest {

    @Autowired
    TestEntityManager entityManager;

    /* This test verifies that in a one-to-one relationship where both Post and PostDetails have their own primary keys,
       persisting a Post with required PostDetails results in Hibernate generating two SQL queries when retrieving a Post.
       This happens even with LAZY fetch, because the relationship is required and Hibernate cannot infer the PostDetails ID from the Post.
     */
    @Test
    void shouldPersistPostWithDetails() {
        var post = new Post("First Post");

        PostDetails details = new PostDetails("admin");
        post.setDetails(details);

        Post postSaved = entityManager.persistAndFlush(post);
        assertThat(postSaved.getDetails()).isNotNull();

        entityManager.detach(postSaved);

        entityManager.find(Post.class, postSaved.getId());
    }
}
