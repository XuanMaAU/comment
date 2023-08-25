package org.mmx.comment.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User Entity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
// user is a reserved keyword, can not be used as the table name directly
@Table(name = "author")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_SEQ")
    private Long id;

    private String name;

    @Column(name = "hashed_password")
    private String hashedPassword;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private List<UserRole> roles;
}
