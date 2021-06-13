package repository;

import model.Campanha;
import model.Status;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CampanhaRepository extends CrudRepository<Campanha, Long> {

    @Query("SELECT c from Campanha c WHERE c.nome LIKE %:title% AND c.status = :statusProcurado")
    List<Campanha> findByTitleLike(@Param("title") String title, @Param("statusProcurado") int statusProcurado);
}
