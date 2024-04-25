package com.coolvetclinicpumb.vetclinicapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity(name = "animals")
@Getter
@Setter
@SQLDelete(sql = "UPDATE users SET is_deleted = TRUE WHERE id = ?")
@SQLRestriction(value = "is_deleted = FALSE")
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "type", nullable = false)
    private String type;
    @Enumerated(EnumType.STRING)
    @Column(name = "sex", nullable = false)
    private Sex sex;
    @Column(name = "weight", nullable = false)
    private Integer weight;
    @Column(name = "cost", nullable = false)
    private Integer cost;
    @JoinColumn(name = "categories_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Category category;
    private boolean isDeleted = false;

    public Animal() {
    }

    public Animal(Long id, String name, String type, Sex sex,
                  Integer weight, Integer cost, Category category) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.sex = sex;
        this.weight = weight;
        this.cost = cost;
        this.category = category;
    }

    @XmlEnum
    public enum Sex {
        @XmlEnumValue("male")
        MALE,
        @XmlEnumValue("female")
        FEMALE;
    }
}
