package org.boarhat.dbrealtionshipdiscovery.oneToOne;

import org.boarhat.dbrealtionshipdiscovery.oneToOne.secondCase.Post;
import org.boarhat.dbrealtionshipdiscovery.oneToOne.secondCase.PostDetails;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@EntityScan(basePackages = "org.boarhat.dbrealtionshipdiscovery.oneToOne.secondCase")
public class OneToOneSecondCaseTest {

    @Autowired
    TestEntityManager entityManager;

    /* Here we have only one key shared between Post and PostDetails using @MapsId.
       As a result, the index table size is reduced by half, and only one query is needed to retrieve a Post,
       since PostDetails uses the same key and no extra query is required to determine its id.
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

