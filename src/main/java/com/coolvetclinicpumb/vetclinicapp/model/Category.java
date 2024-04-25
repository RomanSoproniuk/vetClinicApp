package com.coolvetclinicpumb.vetclinicapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity(name = "categories")
@Getter
@Setter
@SQLDelete(sql = "UPDATE users SET is_deleted = TRUE WHERE id = ?")
@SQLRestriction(value = "is_deleted = FALSE")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;
    @Enumerated(EnumType.STRING)
    @Column(name = "type_category", nullable = false)
    private TypeCategory typeCategory;
    private boolean isDeleted = false;

    public Category() {
    }

    public Category(Short id, TypeCategory typeCategory) {
        this.id = id;
        this.typeCategory = typeCategory;
    }

    public enum TypeCategory {
        FIRST,
        SECOND,
        THIRD,
        FOURTH
    }
}
