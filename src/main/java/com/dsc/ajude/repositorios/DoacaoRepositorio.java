package com.dsc.ajude.repositorios;

import com.dsc.ajude.modelos.Doacao;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoacaoRepositorio extends CrudRepository<Doacao, Long> {

}
