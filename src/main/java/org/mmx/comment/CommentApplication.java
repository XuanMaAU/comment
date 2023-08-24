package org.mmx.comment;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Entry point for the comments application
 */
@SpringBootApplication
public class CommentApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommentApplication.class, args);
    }

    /**
     * The model mapper for comments
     */
    @Bean
    public ModelMapper commentsModelMapper() {
        return new ModelMapper();
    }
}
