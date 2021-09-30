package com.helpaco.behindthescreenapi.service;

import java.util.Optional;

import com.helpaco.behindthescreenapi.model.Game;
import com.helpaco.behindthescreenapi.repository.GameRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.Data;

@Data
@Service
public class GameService {
    
    @Autowired
    private GameRepository gameRepository;

    public Iterable<Game> getGames() {
        return gameRepository.findAll();
    }

    public Optional<Game> getGame(final Long id) {
        return gameRepository.findById(id);
    }

    public Iterable<Game> getGamesByName(String name) {
        return gameRepository.findByName(name);
    }

    public void deleteGame(final Long id) {
        gameRepository.deleteById(id);
    }

    public Game saveGame(Game game) {
        Game savedGame = gameRepository.save(game);
        return savedGame;
    }


}
