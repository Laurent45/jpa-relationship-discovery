package org.boarhat.dbrealtionshipdiscovery.oneToMany;

import org.boarhat.dbrealtionshipdiscovery.oneToMany.secondCase.Post;
import org.boarhat.dbrealtionshipdiscovery.oneToMany.secondCase.PostComment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@EntityScan(basePackages = "org.boarhat.dbrealtionshipdiscovery.oneToMany.secondCase")
public class OneToManySecondCaseTest {

    @Autowired
    private TestEntityManager entityManager;

    /* The following SQL statements are executed:
       1. Insert a post into the `post` table.
       2. Insert three comments into the `post_comment` table.
       3. Update each `post_comment` record to set the `post_id` foreign key, linking them to the post.
       This schema uses a direct foreign key (`post_id`) in the `post_comment` table for the unidirectional one-to-many relationship,
       which is more efficient and typical than using a join table.
    */
    @Test
    void shouldAddPostAndPostComment() {
        Post post = new Post("My First Post");

        post.getComments().add(new PostComment("Great post!"));
        post.getComments().add(new PostComment("Very informative!"));
        post.getComments().add(new PostComment("I learned a lot!"));

        Post postSaved = entityManager.persistAndFlush(post);

        assertThat(postSaved.getId()).isNotNull();
        assertThat(postSaved.getTitle()).isEqualTo(post.getTitle());
        assertThat(postSaved.getComments().size()).isEqualTo(3);

    }
}
