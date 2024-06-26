package com.zhmenko.book.data.model;

import com.zhmenko.author.data.model.AuthorEntity;
import com.zhmenko.user.data.model.UserEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "books")
@NamedEntityGraph(name = "graph.Books",
        attributeNodes = {
                @NamedAttributeNode("userEntities"),
                @NamedAttributeNode("author")
        })
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "books_users",
            joinColumns = {@JoinColumn(name = "book_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<UserEntity> userEntities;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private AuthorEntity author;

    public BookEntity() {
    }

    public BookEntity(final Long id) {
        this.id = id;
    }

    public BookEntity(final Long id, final String name) {
        this.id = id;
        this.name = name;
        this.userEntities = new HashSet<>();
    }

    public BookEntity(final Long id, final String name, final AuthorEntity author) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.userEntities = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AuthorEntity getAuthor() {
        return author;
    }

    public void setAuthor(AuthorEntity author) {
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserEntities(Set<UserEntity> userEntities) {
        this.userEntities = userEntities;
    }

    public Set<UserEntity> getUserEntities() {
        return userEntities;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final BookEntity that = (BookEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public String toShortString() {
        return "BookEntity{" +
               "id=" + id +
               ", name='" + name +
               '}';
    }

    @Override
    public String toString() {
        return "BookEntity{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", userBookEntities=" +
               userEntities.stream()
                       .map(UserEntity::toShortString)
                       .collect(Collectors.joining(", ")) +
               ", author=" + author.toShortString() +
               '}';
    }
}
