package org.boarhat.dbrealtionshipdiscovery.oneToMany.thirdCase;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.util.Objects;


@Entity(name = "PostComment")
@Table(name = "post_comment")
public class PostComment {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    private String review;

    protected PostComment() {}

    public PostComment(Long id, String review) {
        this.id = id;
        this.review = review;
    }

    public PostComment(String review) {
        this.review = review;
    }

    public Long getId() {
        return id;
    }

    public String getReview() {
        return review;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PostComment that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(post, that.post);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, post);
    }
}
