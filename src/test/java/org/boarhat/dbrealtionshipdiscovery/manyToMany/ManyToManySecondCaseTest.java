package org.boarhat.dbrealtionshipdiscovery.manyToMany;

import org.boarhat.dbrealtionshipdiscovery.manyToMany.secondCase.Post;
import org.boarhat.dbrealtionshipdiscovery.manyToMany.secondCase.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@EntityScan(basePackages = "org.boarhat.dbrealtionshipdiscovery.manyToMany.secondCase")
public class ManyToManySecondCaseTest {

    @Autowired
    private TestEntityManager entityManager;

    /**
     * This test demonstrates how JPA/Hibernate handles a many-to-many relationship with a Set.
     * <p>
     * - Hibernate performs INSERTs for each Post and Tag entity.
     * - Posts and Tags are associated and persisted.
     * - We detach post1 from the persistence context to force a SELECT when it is accessed again.
     * - When removing a Tag from the Post and flushing, Hibernate deletes only the specific entry in the association table
     *   using the post id and tag id, making the operation more efficient compared to using a List.
     */
    @Test
    void shouldPersistPostWithTags() {
        Post post1 = new Post("My First Post");
        Post post2 = new Post("My Second Post");

        Tag tag1 = new Tag("Java");
        Tag tag2 = new Tag("Spring");

        post1.addTag(tag1);
        post1.addTag(tag2);

        post2.addTag(tag1);

        Post post1Saved = entityManager.persistAndFlush(post1);
        Post post2Saved = entityManager.persistAndFlush(post2);

        assertThat(post1Saved.getId()).isNotNull();
        assertThat(post2Saved.getId()).isNotNull();

        entityManager.detach(post1Saved);

        Post postRetrieved = entityManager.find(Post.class, post1Saved.getId());
        postRetrieved.removeTag(tag1);
        entityManager.flush();
    }
}
