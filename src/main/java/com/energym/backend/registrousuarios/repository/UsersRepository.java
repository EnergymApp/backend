package com.energym.backend.registrousuarios.repository;

import com.energym.backend.registrousuarios.model.Roles;
import com.energym.backend.registrousuarios.model.Users;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UsersRepository extends CrudRepository<Users, Integer> {
    Users findUserByEmail(String email);

    Users findUserByEmailAndPassword(String email, String password);

    @Modifying
    @Transactional
    @Query("update Users u SET u.password=:password WHERE u.id=:id")
    void updatePassword(@Param("id") Integer id, @Param("password") String password);

    @Modifying
    @Transactional
    @Query("update Users u SET u.name=:name, u.email=:email, u.telephone=:telephone, u.role=:role WHERE u.id=:id")
    void updateUser(@Param("id") Integer id, @Param("name") String name, @Param("email") String email, @Param("telephone") String telephone, @Param("role") Roles role);
}
