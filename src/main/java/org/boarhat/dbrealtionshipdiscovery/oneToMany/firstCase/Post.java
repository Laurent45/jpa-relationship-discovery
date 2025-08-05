package org.boarhat.dbrealtionshipdiscovery.oneToMany.firstCase;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(name = "Post")
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue
    private Long id;

    private String title;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostComment> comments = new ArrayList<>();

    protected Post() {}

    public Post(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public Post(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public List<PostComment> getComments() {
        return comments;
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
