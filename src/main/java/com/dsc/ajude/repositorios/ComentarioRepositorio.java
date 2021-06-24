package com.dsc.ajude.repositorios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.dsc.ajude.modelos.Comentario;

@Repository
public interface ComentarioRepositorio extends CrudRepository<Comentario, Long> {
	


}
