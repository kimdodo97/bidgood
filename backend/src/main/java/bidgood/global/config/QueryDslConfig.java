package bidgood.global.config;

import com.querydsl.core.annotations.Config;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueryDslConfig {
    @PersistenceContext
    public EntityManager em;

    /**
     * Creates a JPAQueryFactory bean configured with the injected EntityManager.
     *
     * <p>This method instantiates a new JPAQueryFactory, enabling type-safe query construction via QueryDSL in a JPA context.</p>
     *
     * @return a newly created JPAQueryFactory instance
     */
    @Bean
    public JPAQueryFactory jpaQueryFactory(){
        return new JPAQueryFactory(em);
    }
}
