package com.example.ebookstorebackend.book;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);
    @Bean
    CommandLineRunner initDatabase(BookRepo repo){
        return args->{};
//        return args->{
//            BookEntity book = BookEntity.builder()
//                    .title("Title")
//                    .description("Description")
//                    .author("Author")
//                    .price(19.99)
//                    .cover("Cover URL")
//                    .sales(100)
//                    .isbn("ISBN")
//                    .build();
//            log.info("Preloading " + repo.save(book));
//        };
    }
}
