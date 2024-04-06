package com.energym.backend.reservas.repository;

import com.energym.backend.reservas.model.Professionals;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProfessionalsRepository extends CrudRepository<Professionals, Integer> {

    List<Professionals> findProfessionalsByArea(String area);

    @Modifying
    @Transactional
    @Query("update Professionals p set p.name=:name, p.telephone=:telephone, p.area=:area where p.id=:id")
    void updateProfessional(@Param("name") String name, @Param("telephone") String telephone, @Param("area") String area, @Param("id") Integer id);
}
