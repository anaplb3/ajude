package com.dsc.ajude.repositorios;


import com.dsc.ajude.modelos.Like;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepositorio extends CrudRepository<Like, Long> {

}
