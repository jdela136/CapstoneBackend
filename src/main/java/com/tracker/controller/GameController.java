package com.tracker.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tracker.entity.Game;
import com.tracker.entity.PlateAppearance;
import com.tracker.entity.Team;
import com.tracker.service.GameService;

@CrossOrigin
@RestController
public class GameController {
	
	@Autowired
	GameService service;
	
	@RequestMapping(value = "/start-game", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ResponseEntity<Game> startGame(@RequestBody Game game) {
		return new ResponseEntity<>(service.startGame(game), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/games", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ResponseEntity<List<Game>> findGamesByLeagueid(Integer leagueId) {
		return new ResponseEntity<>(service.findGamesByLeagueId(leagueId), HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/games/{gameId}/start-pa", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public void startPA(@PathVariable Integer gameId) {
		service.startPA(gameId);
	}
	
	@RequestMapping(value = "/game", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ResponseEntity<Optional<Game>> findGameById(Integer gameId) {
		return new ResponseEntity<>(service.findGameById(gameId), HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/strike", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public void addStrike(@RequestBody Game game) {
		service.addStrike(game);
	}
	
	@RequestMapping(value = "/ball", consumes= MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public void addBall(@RequestBody Game game) {
		service.addBall(game);
	}
	
	@RequestMapping(value = "/move", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public void changeRunnerBase(@RequestParam Integer paId, @RequestParam Integer startingBase, @RequestParam Integer endingBase, @RequestParam Boolean isSacFly ) {
		service.changeRunnerBase(paId, startingBase, endingBase, isSacFly);
	}
	
//	@RequestMapping(value = "/move", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
//	public void moveRunner(@RequestBody Game game, Integer startingBase, Integer endingBase) {
//		service.moveRunner(gameId, startingBase, endingBase);
//	}
	
	@RequestMapping(value = "/out", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public void outOnPlay(@RequestBody Game game) {
		service.outOnPlay(game);
	}
	
	@RequestMapping(value = "/fielders-choice", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public void fieldersChoice(@RequestBody Game game) {
		service.fieldersChoice(game);
	}
	
	@RequestMapping(value = "/single", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public void hitSingle(@RequestBody Game game) {
		service.baseHit(game , 1);
	}
	
	@RequestMapping(value = "/double", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public void hitDouble(@RequestBody Game game) {
		service.baseHit(game , 2);
	}
	@RequestMapping(value = "/triple", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public void hitTriple(@RequestBody Game game) {
		service.baseHit(game , 3);
	}
	@RequestMapping(value = "/homerun", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public void hitHomerun(@RequestBody Game game) {
		service.baseHit(game , 4);
	}
	
	@RequestMapping(value = "/in-play", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public void ballInPlay(@RequestBody Game game) {
		service.ballInPlay(game);
	}
	
	@RequestMapping(value = "/end-game", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public void endGame(@RequestBody Game game) {
		service.endGame(game);
	}
	
	@RequestMapping(value = "/last-pa", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ResponseEntity<PlateAppearance> findLastPAByGameId(Integer gameId) {
		return new ResponseEntity<>(service.findLastPAByGameId(gameId), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/plate-appearances", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ResponseEntity<List<PlateAppearance>> findPAsByGameId(Integer gameId) {
		return new ResponseEntity<>(service.findPAsByGameId(gameId), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/home-team", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ResponseEntity<Team> findHomeTeamByGameId(Integer gameId) {
		return new ResponseEntity<>(service.findHomeTeamByGameId(gameId), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/away-team", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ResponseEntity<Team> findAwayTeamByGameId(Integer gameId) {
		return new ResponseEntity<>(service.findAwayTeamByGameId(gameId), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/runners", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ResponseEntity<List<PlateAppearance>> findRunners(Integer gameId) {
		return new ResponseEntity<>(service.findRunners(gameId), HttpStatus.OK);
	}
	
}
