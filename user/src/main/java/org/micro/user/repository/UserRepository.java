package org.micro.user.repository;

import org.micro.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Override
    Page<User> findAll(Pageable pageable);

    @Override
    List<User> findAllById(Iterable<Long> usersIds);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update User u set u.status = 1 where u.id IN :ids")
    void softDeleteAllById(@Param("ids") Iterable<Long> usersIds);

}
