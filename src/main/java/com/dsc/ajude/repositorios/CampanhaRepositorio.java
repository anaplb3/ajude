package com.dsc.ajude.repositorios;

import com.dsc.ajude.modelos.Campanha;
import com.dsc.ajude.modelos.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampanhaRepositorio extends JpaRepository<Campanha, Long> {

    @Query("SELECT c from Campanha c WHERE c.nome LIKE %:nomeProcurado% AND c.status IN :statusProcurado")
    List<Campanha> findByNomeLike(@Param("nomeProcurado") String nomeProcurado, @Param("statusProcurado") List<Status> statusProcurado);

    @Query("SELECT c from Campanha c " +
            "WHERE c.status = :status order by c.valorRestanteMeta ASC")
    List<Campanha> findAllByMetaRestante(@Param("status") Status status);

    @Query("SELECT c from Campanha c " +
            "WHERE c.status = :status order by c.deadline ASC")
    List<Campanha> findAllByDeadLine(@Param("status") Status status);

    @Query("SELECT c from Campanha c " +
            "WHERE c.status = :status order by c.quantidadeLikes DESC")
    List<Campanha> findAllByLikes(@Param("status") Status status);

    Campanha findByCampanhaIdAndStatus(Long campanhaId, Status status);
}
