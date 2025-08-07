package org.boarhat.dbrealtionshipdiscovery.manyToMany;

import org.boarhat.dbrealtionshipdiscovery.manyToMany.firstCase.Post;
import org.boarhat.dbrealtionshipdiscovery.manyToMany.firstCase.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@EntityScan(basePackages = "org.boarhat.dbrealtionshipdiscovery.manyToMany.firstCase")
public class ManyToManyFirstCaseTest {

    @Autowired
    private TestEntityManager entityManager;

    /**
     * This test demonstrates how JPA/Hibernate handles a many-to-many relationship with a List.
     *
     * - Hibernate performs INSERTs for each Post and Tag entity.
     * - Posts and Tags are associated and persisted.
     * - We detach post1 from the persistence context to force a SELECT when it is accessed again.
     * - When removing a Tag from the Post and flushing, Hibernate deletes all entries for the Post in the association table,
     *   then re-inserts the remaining associations. This inefficiency is due to using a List for the relationship.
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
