package com.mbt.yapikredi.ik.repository.base;

import com.mbt.yapikredi.ik.entity.base.BaseEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.QuerydslJpaRepository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.EntityManager;


public class BaseRepositoryImpl<E extends BaseEntity>
        extends QuerydslJpaRepository<E, Long>
        implements BaseRepository<E>{

    private final EntityManager entityManager;

    public BaseRepositoryImpl(JpaEntityInformation<E, Long> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager=entityManager;
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public JPAQueryFactory getQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }
}
