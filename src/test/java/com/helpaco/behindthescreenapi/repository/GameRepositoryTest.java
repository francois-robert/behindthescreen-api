package com.helpaco.behindthescreenapi.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Optional;

import com.helpaco.behindthescreenapi.model.Game;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GameRepositoryTest {

    @Autowired
    private GameRepository gameRepository;

    @Test
    public void testGetGameByName() throws Exception {
        String expected = "Donjon & Dragons";
        int nbGame = 0;

        Iterable<Game> games = gameRepository.findByName(expected);

        for (Game game : games) {
            assertEquals(game.getName(), expected);
            nbGame++;
        }

        if (nbGame == 0) {
            fail("Game "+expected+" was not found");
        }
    }

    @Test
    public void testGetGame() throws Exception {
        String expected = "Donjon & Dragons";

        Optional<Game> game = gameRepository.findById(Long.valueOf(1));

        if (game.isPresent()) {
            assertEquals(game.get().getName(), expected);
        } else {
            fail("Game was not found");
        }
    }
    
}
