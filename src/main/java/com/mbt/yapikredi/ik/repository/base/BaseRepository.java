package com.mbt.yapikredi.ik.repository.base;

import com.mbt.yapikredi.ik.entity.base.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface BaseRepository<E extends BaseEntity> extends JpaRepository<E, Long>, QuerydslPredicateExecutor<E> {
}
