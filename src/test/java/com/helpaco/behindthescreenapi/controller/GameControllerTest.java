package com.helpaco.behindthescreenapi.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.helpaco.behindthescreenapi.model.Game;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


@SpringBootTest
@AutoConfigureMockMvc
public class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetGames() throws Exception {
        mockMvc.perform(get("/games"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].name", is("Donjon & Dragons")));
    }

    @Test
    public void testGetGameByName() throws Exception {
        String gameName = "Donjon & Dragons";

        mockMvc.perform(get("/games")
                       .param("name", gameName))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].name", is(gameName)));
    }

    @Test
    public void testGetGameById() throws Exception {
        mockMvc.perform(get("/game/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name", is("Donjon & Dragons")));
    }

    @Test
    public void testPostGame() throws Exception {
        Game newGame = new Game();
        newGame.setName("Tales from the Loop");
        newGame.setType("Contemporain");
        newGame.setDescription("En 1954, le gouvernement suédois mit en chantier la construction du plus grand accélérateur de particules au monde dans les profondeurs du lac Mälar près de Stockholm. Les travaux s’achevèrent en 1969, et rapidement une communauté de scientifiques et de chercheurs se regroupa en créant un centre de recherche avancé au milieu du décor pastoral de l’île de Munsö. Les habitants de l’île appellent ce centre: le Loop.");
        newGame.setReleaseDate(LocalDate.parse("2017-03-10"));
        newGame.setVersion("1");

        System.out.println(asJsonString(newGame));
        
        mockMvc.perform(post("/games", newGame)
                .content(asJsonString(newGame))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name", is("Tales from the Loop")));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
}
