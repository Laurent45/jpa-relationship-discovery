package org.boarhat.dbrealtionshipdiscovery.oneToMany.secondCase;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity(name = "PostComment")
@Table(name = "post_comment")
public class PostComment {
    @Id
    @GeneratedValue
    private Long id;

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

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PostComment that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

