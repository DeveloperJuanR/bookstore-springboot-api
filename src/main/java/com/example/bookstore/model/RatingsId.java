package com.example.bookstore.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class RatingsId implements Serializable {

    @Column(name = "isbn")
    private String ISBN;

    @Column(name = "user_id")
    private Long userId;

    public RatingsId() {
    }

    public RatingsId(String ISBN, Long userId) {
        this.ISBN = ISBN;
        this.userId = userId;
    }

    public String getIsbn() {
        return ISBN;
    }

    public void setIsbn(String ISBN) {
        this.ISBN = ISBN;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    // Override equals() and hashCode() for composite key comparison

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RatingsId that = (RatingsId) o;
        return (Objects.equals(ISBN, that.ISBN) && Objects.equals(userId, that.userId));
    }

    @Override
    public int hashCode() {
        return Objects.hash(ISBN, userId);
    }
}
