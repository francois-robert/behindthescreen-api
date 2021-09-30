package com.helpaco.behindthescreenapi.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.helpaco.behindthescreenapi.model.Game;
import com.helpaco.behindthescreenapi.service.GameService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;


@WebMvcTest(GameController.class)
public class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    GameService gameService;

    Game GAME_1 = new Game(Long.valueOf(1), "Donjon & Dragons", "Mediéval Fantastique", "Plus connus des jeurs de rôle", "5", LocalDate.parse("2014-07-03"));
    Game GAME_2 = new Game(Long.valueOf(2), "L'Appel de Cthulhu", "Horreur/Enquête", "À la frontière de la folie des hommes, L’Appel de Cthulhu plonge les joueurs dans les écrits et les mondes torturés de l’auteur américain Howard Phillips Lovecraft : le cosmos est régi par des entités élevées au rang de dieux, conscience pure ou monstruosité maligne, dont les intentions nous échappent. À la recherche de secrets perdus et confronté à l’innommable, basculerez-vous dans la folie ou parviendrez-vous à repousser les horreurs sans nom ? Voici le genre d’enjeux auxquels sont confrontés les courageux investigateurs !", "7", LocalDate.parse("2016-05-16"));
    Game GAME_3 = new Game(Long.valueOf(3), "Pathfinder", "Mediéval Fantastique", "Ce jeu de rôle se fonde sur les règles de Donjons et Dragons édition 3.5 qu'il enrichit et améliore. Il est largement compatible avec Donjons et Dragons 3.5, et constitue pour ses fans une alternative à la quatrième édition de Donjons et Dragons, laquelle n'est pas compatible avec l'édition précédente.", "2", LocalDate.parse("2019-08-01"));

    @Test
    public void testGetGames() throws Exception {
        List<Game> records = new ArrayList<>(Arrays.asList(GAME_1, GAME_2, GAME_3));
    
        Mockito.when(gameService.getGames()).thenReturn(records);    

        mockMvc.perform(get("/games"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].name", is(GAME_1.getName())));
    }

    @Test
    public void testGetGameByName() throws Exception {
        List<Game> records = new ArrayList<>(Arrays.asList(GAME_1));

        Mockito.when(gameService.getGamesByName(GAME_1.getName())).thenReturn(records);

        mockMvc.perform(get("/games")
                       .param("name", GAME_1.getName()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].name", is(GAME_1.getName())));
    }

    @Test
    public void testGetGameById() throws Exception {
        Mockito.when(gameService.getGame(GAME_1.getId())).thenReturn(java.util.Optional.of(GAME_1));

        mockMvc.perform(get("/game/" + GAME_1.getId()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name", is(GAME_1.getName())));
    }

    @Test
    public void testPostGame() throws Exception {
        Game newGame = new Game(Long.valueOf(4), "Tales from the Loop", "Contemporain", "En 1954, le gouvernement suédois mit en chantier la construction du plus grand accélérateur de particules au monde dans les profondeurs du lac Mälar près de Stockholm. Les travaux s’achevèrent en 1969, et rapidement une communauté de scientifiques et de chercheurs se regroupa en créant un centre de recherche avancé au milieu du décor pastoral de l’île de Munsö. Les habitants de l’île appellent ce centre: le Loop.", "1", LocalDate.parse("2017-03-10"));
        
        Mockito.when(gameService.saveGame(newGame)).thenReturn(newGame);

        MockHttpServletRequestBuilder mockRequest = post("/games", newGame)
                                                        .content(asJsonString(newGame))
                                                        .contentType(MediaType.APPLICATION_JSON)
                                                        .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest)
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name", is(newGame.getName())));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
}
