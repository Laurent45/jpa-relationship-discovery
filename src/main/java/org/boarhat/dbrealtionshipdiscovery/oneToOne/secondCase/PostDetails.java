package org.boarhat.dbrealtionshipdiscovery.oneToOne.secondCase;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.util.Date;
import java.util.Objects;

@Table(name = "post_details")
@Entity
public class PostDetails {

    @Id
    private Long id;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @Column(name = "created_by", nullable = false, updatable = false)
    private String createdBy;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Post post;

    public PostDetails() {}

    public PostDetails(String createdBy) {
        this.createdAt = new Date();
        this.createdBy = createdBy;
    }

    public Post getPost() {
        return post;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PostDetails that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(post, that.post);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, post);
    }
}

