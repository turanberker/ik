package com.mbt.yapikredi.ik.repository.base;

import com.mbt.yapikredi.ik.entity.base.BaseEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.EntityManager;

@NoRepositoryBean
public interface BaseRepository<E extends BaseEntity>
        extends JpaRepository<E, Long>,
        QuerydslPredicateExecutor<E>
{
    EntityManager getEntityManager();

    JPAQueryFactory getQueryFactory();
}
