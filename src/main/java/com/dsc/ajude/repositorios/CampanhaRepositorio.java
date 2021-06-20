package com.dsc.ajude.repositorios;

import com.dsc.ajude.modelos.Campanha;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampanhaRepositorio extends CrudRepository<Campanha, Long> {

    @Query("SELECT c from Campanha c WHERE c.nome LIKE %:title% AND c.status = :statusProcurado")
    List<Campanha> findByTitleLike(@Param("title") String title, @Param("statusProcurado") int statusProcurado);
}
