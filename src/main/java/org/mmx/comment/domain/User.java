package org.mmx.comment.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * User Entity
 */
@Getter
@Setter
@ToString
@Entity
@Table(name = "author")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_SEQ")
    private Long id;

    private String name;

    @Column(name = "hashed_password")
    private String hashedPassword;
}
