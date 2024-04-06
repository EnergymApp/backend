package com.energym.backend.reservas.repository;

import com.energym.backend.reservas.model.Classes;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ClassesRepository  extends CrudRepository<Classes, String> {

    Optional<Classes> findById(String id);

    List<Classes> findClassesByNameContainsIgnoreCase(String name);

    @Modifying
    @Transactional
    @Query("update Classes u set u.name=:name, u.description=:description, u.duration=:duration, u.image=:image, u.price=:price, u.rating=:rating where u.code=:code")
    void updateClass(@Param("code") String code, @Param("name") String name, @Param("description") String description, @Param("duration") Integer duration, @Param("image") String image, @Param("price") Double price, @Param("rating") Float rating);
}
