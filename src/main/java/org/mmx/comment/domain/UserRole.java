package org.mmx.comment.domain;

import org.mmx.comment.security.SecurityRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User roles
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_role")
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_role_SEQ")
    private Long id;

    /**
     * The foreign key for the user
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * The user role name
     */
    @Enumerated(EnumType.STRING)
    private SecurityRole role;
}
