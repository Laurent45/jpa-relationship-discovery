package org.boarhat.dbrealtionshipdiscovery.manyToMany.firstCase;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import org.hibernate.annotations.NaturalId;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Table(name = "tag")
@Entity
public class Tag {

    @Id
    @GeneratedValue
    private Long id;

    @NaturalId
    private String name;

    @ManyToMany(mappedBy = "tags")
    private List<Post> posts = new ArrayList<>();

    protected Tag() {}

    public Tag(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Post> getPosts() {
        return posts;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Tag tag)) return false;
        return Objects.equals(name, tag.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
