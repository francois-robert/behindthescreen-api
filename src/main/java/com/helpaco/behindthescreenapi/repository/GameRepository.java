package com.helpaco.behindthescreenapi.repository;

import org.springframework.stereotype.Repository;

import com.helpaco.behindthescreenapi.model.Game;
import org.springframework.data.repository.CrudRepository;


@Repository
public interface GameRepository extends CrudRepository<Game, Long> {

    Iterable<Game> findByName(String name);

    Iterable<Game> findByNameAndVersion(String name, String version);
    
}
