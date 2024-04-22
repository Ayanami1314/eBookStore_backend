package org.example.ebookstorebackend.book;

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
        return args->{
            log.info("Preloading " + repo.save(new Book()));
            log.info("Preloading " + repo.save(new Book("The Lord of the Rings","Somebody", "A novel", 10,"None", "unknown")));
        };
    }
}
