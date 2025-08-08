package org.boarhat.dbrealtionshipdiscovery.oneToOne.firstCase;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.util.Objects;

@Table(name = "post")
@Entity
public class Post {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    @OneToOne(mappedBy = "post", cascade = CascadeType.ALL, optional = false, orphanRemoval = true, fetch = FetchType.LAZY)
    private PostDetails details;

    protected Post() {}

    public Post(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public PostDetails getDetails() {
        return details;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDetails(PostDetails details) {
        if (details == null) {
            if (this.details != null) {
                this.details.setPost(null);
            }
        } else {
            details.setPost(this);
        }
        this.details = details;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Post post)) return false;
        return Objects.equals(id, post.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
