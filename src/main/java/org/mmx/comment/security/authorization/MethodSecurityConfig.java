package org.mmx.comment.security.authorization;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

/**
 * The method security configurations, which enables the PreAuthorize / PostAuthorize
 */
@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class MethodSecurityConfig {
}
