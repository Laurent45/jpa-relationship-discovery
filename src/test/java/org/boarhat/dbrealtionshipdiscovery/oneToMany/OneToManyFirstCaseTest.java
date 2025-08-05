package org.boarhat.dbrealtionshipdiscovery.oneToMany;

import org.boarhat.dbrealtionshipdiscovery.oneToMany.firstCase.Post;
import org.boarhat.dbrealtionshipdiscovery.oneToMany.firstCase.PostComment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@EntityScan(basePackages = "org.boarhat.dbrealtionshipdiscovery.oneToMany.firstCase")
public class OneToManyFirstCaseTest {

    @Autowired
    private TestEntityManager entityManager;

    /* The following SQL statements are executed:
        1. Insert a post into the `post` table.
        2. Insert three comments into the `post_comment` table.
        3. Link each comment to the post using the `post_post_comment` join table.
        This schema uses a join table (`post_post_comment`) to associate posts and comments,
        which resembles a many-to-many relationship rather than a typical one-to-many.
        In a standard one-to-many, the `post_comment` table would have a `post_id` foreign key,
         making the join table unnecessary and more efficient.
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

