package com.helpaco.behindthescreenapi.controller;

import java.time.LocalDate;
import java.util.Optional;

import com.helpaco.behindthescreenapi.model.Game;
import com.helpaco.behindthescreenapi.service.GameService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.HttpStatus;

@RestController
public class GameController {
    
    Logger logger = LoggerFactory.getLogger(GameController.class);

    @Autowired
    private GameService gameService;


    /**
    * Read - Get games
    * @param name The id of the game
    * @return - An Iterable object of Games full filled
    */
    @GetMapping("/games")
    public Iterable<Game> getGames(@RequestParam(name="name", required = false) String name) {
        if (name == null) {
            return gameService.getGames();
        } else {
            logger.debug("Searching games with name " + name);
            return gameService.getGamesByName(name);
        }

        
    }

    /**
    * Read - Get one game
    * @param id The id of the game
    * @return A Game object full filled
    */
    @GetMapping("/game/{id}")
    public Game getGame(@PathVariable("id") final Long id) {
        Optional<Game> game = gameService.getGame(id);
        if(game.isPresent()) {
            return game.get();
        } else {
            return null;
        }
 
    }

    /**
	 * Create - Add a new game
	 * @param game An object game
	 * @return The game object saved
	 */
	@PostMapping("/games")
    @ResponseStatus(HttpStatus.CREATED)
	public Game createGame(@RequestBody Game game) {
		return gameService.saveGame(game);
	}

	/**
	 * Delete a game
	 * @param game An object game
	 * @return The game object saved
	 */
	@DeleteMapping("/game/{id}")
	public void deleteEmployee(@PathVariable("id") final Long id) {
		gameService.deleteGame(id);
	}


    /**
	 * Update - Update an existing game
	 * @param id - The id of the game to update
	 * @param game - The game object updated
	 * @return
	 */
	@PutMapping("/game/{id}")
	public Game updateEmployee(@PathVariable("id") final Long id, @RequestBody Game game) {
		Optional<Game> e = gameService.getGame(id);
		if(e.isPresent()) {
			Game currentGame = e.get();
			
			String name = game.getName();
			if(name != null) {
				currentGame.setName(name);
			}
			String type = game.getType();
			if(type != null) {
				currentGame.setType(type);
			}
			String description = game.getDescription();
			if(description != null) {
				currentGame.setDescription(description);
			}
            String version = game.getVersion();
			if(version != null) {
				currentGame.setVersion(version);
			}
            LocalDate releaseDate = game.getReleaseDate();
			if(releaseDate != null) {
				currentGame.setReleaseDate(releaseDate);
			}
			gameService.saveGame(currentGame);
			return currentGame;
		} else {
			return null;
		}
	}

}
