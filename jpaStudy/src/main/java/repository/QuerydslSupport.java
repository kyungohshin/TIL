package repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.querydsl.jpa.impl.JPAQueryFactory;

public abstract class QuerydslSupport {

    @PersistenceContext
    private EntityManager entityManager;

    private JPAQueryFactory queryFactory;

    protected JPAQueryFactory getQueryFactory() {
        if (queryFactory == null) {
            queryFactory = new JPAQueryFactory(entityManager);
        }
        return queryFactory;
    }

    protected EntityManager getEntityManager() {
        return entityManager;
    }
}
