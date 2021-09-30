package com.helpaco.behindthescreenapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.helpaco.behindthescreenapi.model.Game;
import com.helpaco.behindthescreenapi.repository.GameRepository;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class GameServiceTest {

    @MockBean
    private GameRepository gameRepository;

    Game GAME_1 = new Game(Long.valueOf(1), "Donjon & Dragons", "Mediéval Fantastique", "Plus connus des jeurs de rôle", "5", LocalDate.parse("2014-07-03"));
    Game GAME_2 = new Game(Long.valueOf(2), "L'Appel de Cthulhu", "Horreur/Enquête", "À la frontière de la folie des hommes, L’Appel de Cthulhu plonge les joueurs dans les écrits et les mondes torturés de l’auteur américain Howard Phillips Lovecraft : le cosmos est régi par des entités élevées au rang de dieux, conscience pure ou monstruosité maligne, dont les intentions nous échappent. À la recherche de secrets perdus et confronté à l’innommable, basculerez-vous dans la folie ou parviendrez-vous à repousser les horreurs sans nom ? Voici le genre d’enjeux auxquels sont confrontés les courageux investigateurs !", "7", LocalDate.parse("2016-05-16"));
    Game GAME_3 = new Game(Long.valueOf(3), "Pathfinder", "Mediéval Fantastique", "Ce jeu de rôle se fonde sur les règles de Donjons et Dragons édition 3.5 qu'il enrichit et améliore. Il est largement compatible avec Donjons et Dragons 3.5, et constitue pour ses fans une alternative à la quatrième édition de Donjons et Dragons, laquelle n'est pas compatible avec l'édition précédente.", "2", LocalDate.parse("2019-08-01"));

    @Test
    public void testGetGameByName() throws Exception {
        List<Game> records = new ArrayList<>(Arrays.asList(GAME_1));

        Mockito.when(gameRepository.findByName(GAME_1.getName())).thenReturn(records);

        int nbGame = 0;

        Iterable<Game> games = gameRepository.findByName(GAME_1.getName());

        for (Game game : games) {
            assertEquals(game.getName(), GAME_1.getName());
            nbGame++;
        }

        if (nbGame == 0) {
            fail("Game "+GAME_1.getName()+" was not found");
        }
    }

    @Test
    public void testGetGame() throws Exception {
        Mockito.when(gameRepository.findById(GAME_1.getId())).thenReturn(java.util.Optional.of(GAME_1));

        Optional<Game> game = gameRepository.findById(Long.valueOf(1));

        if (game.isPresent()) {
            assertEquals(game.get().getName(), GAME_1.getName());
        } else {
            fail("Game was not found");
        }
    }
    
}
