package com.hrmtool.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

@NoRepositoryBean
public interface GenericRepo<T, ID extends Serializable> extends JpaRepository<T, ID> {

    @Override
    @Query("update #{#entityName} e set e.isActive=false where e.id =  #{#entityId}")
    @Transactional
    @Modifying
    void delete(T entity);
}
