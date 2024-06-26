package com.zhmenko.user.data.model;

import com.zhmenko.book.data.model.BookEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * User entity
 */
@Entity
@Table(name = "users", indexes = {
        @Index(columnList = "id", name = "idx_users")
})
@NamedEntityGraph(name = "graph.Users",
        attributeNodes = {
                @NamedAttributeNode("bookEntitySet"),
                @NamedAttributeNode("billingDetailsEntitySet")
        })
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "country")
    private String country;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "books_users",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "book_id")})
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<BookEntity> bookEntitySet;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user", fetch = FetchType.LAZY)
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<BillingDetailsEntity> billingDetailsEntitySet;

    public UserEntity() {
    }

    public UserEntity(final Long id) {
        this.id = id;
    }

    public UserEntity(final Long id,
                      final String name,
                      final String email,
                      final String country,
                      final Set<BookEntity> bookEntitySet,
                      final Set<BillingDetailsEntity> billingDetailsEntitySet) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.country = country;
        this.bookEntitySet = bookEntitySet;
        this.billingDetailsEntitySet = billingDetailsEntitySet;
    }

    public UserEntity(Long id, String name, String email, String country) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.country = country;
        this.bookEntitySet = new HashSet<>();
    }

    public Set<BillingDetailsEntity> getBillingDetailsSet() {
        return billingDetailsEntitySet;
    }

    public void setBillingDetailsSet(final Set<BillingDetailsEntity> billingDetailsEntitySet) {
        this.billingDetailsEntitySet = billingDetailsEntitySet;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<BookEntity> getBookEntitySet() {
        return bookEntitySet;
    }

    public void setBookEntitySet(Set<BookEntity> bookEntitySet) {
        this.bookEntitySet = bookEntitySet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public String toShortString() {
        return "UserEntity{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", email='" + email + '\'' +
               ", country='" + country + '\'' +
               '}';
    }

    @Override
    public String toString() {
        return "UserEntity{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", email='" + email + '\'' +
               ", country='" + country + '\'' +
               ", userEntities=" + bookEntitySet +
               '}';
    }
}
