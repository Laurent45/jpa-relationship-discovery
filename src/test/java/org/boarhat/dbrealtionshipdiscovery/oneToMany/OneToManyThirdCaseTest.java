package org.boarhat.dbrealtionshipdiscovery.oneToMany;

import org.boarhat.dbrealtionshipdiscovery.oneToMany.thirdCase.Post;
import org.boarhat.dbrealtionshipdiscovery.oneToMany.thirdCase.PostComment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@EntityScan(basePackages = "org.boarhat.dbrealtionshipdiscovery.oneToMany.thirdCase")
public class OneToManyThirdCaseTest {

    @Autowired
    private TestEntityManager entityManager;

  /*
    In a bidirectional one-to-many relationship:

    - If the `PostComment` entity owns the relationship (i.e., it has the `@ManyToOne` annotation with a `post` field), and you set the `post` reference in each `PostComment` before persisting,
    - Only `INSERT` statements are executed for both `post` and `post_comment` tables.
    - No additional `UPDATE` statements are needed to set the foreign key, because it is already set during the initial insert.

    This is more efficient than the unidirectional case, where the foreign key is set after the initial insert, requiring extra update queries.
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
