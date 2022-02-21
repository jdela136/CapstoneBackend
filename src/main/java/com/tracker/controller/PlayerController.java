package com.tracker.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.tracker.entity.Player;
import com.tracker.entity.Team;
import com.tracker.service.PlayerService;
import org.springframework.http.ResponseEntity;

@CrossOrigin
@RestController
public class PlayerController {

	@Autowired
	PlayerService service;
	
	@RequestMapping(value = "/save-player", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public void submitPlayer(@RequestBody Player player) {
		service.savePlayer(player);
	}

	@RequestMapping(value = "/add-to-lineup", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public void addToLineUp(@RequestBody Player player) {
		//Optional<Player> dbPlayer = service.getPlayerByFirstAndLast(player.getFirstName(), player.getLastName());
		service.addToLineUp(player);
	}
	
	@RequestMapping(value = "/remove-from-lineup", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public void removeFromLineUp(@RequestBody Player player) {
		//Optional<Player> dbPlayer = service.getPlayerByFirstAndLast(player.getFirstName(), player.getLastName());
		service.removeFromLineUp(player);
	}

//	@RequestMapping(value = "/set-line-up", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
//	public void setLineUp(@RequestBody List<Player> players) {
//		// List<Optional<Player>> dbPlayers = new ArrayList<Optional<Player>>();
//		Integer lineUpPosition = 1;
//		for (Player player : players) {
//			if (player != null) {
//				//Optional<Player> dbPlayer = service.getPlayerByFirstAndLast(player.getFirstName(), player.getLastName());
//				service.addToLineUp(dbPlayer, lineUpPosition);
//			}
//			if (lineUpPosition == 12) {
//				lineUpPosition = 1;
//			} else {
//				lineUpPosition++;
//			}
//		}
//	}
	
	@RequestMapping(value = "/roster", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ResponseEntity<List<Player>> findTeamRoster(Integer teamId) {
		return new ResponseEntity<>(service.findTeamRoster(teamId), HttpStatus.OK);		
	}
	
	@RequestMapping(value = "/line-up", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ResponseEntity<List<Player>> findTeamLineUp(Integer teamId) {
		return new ResponseEntity<>(service.findTeamLineUp(teamId), HttpStatus.OK);		
	}
	
	@RequestMapping(value = "/players", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ResponseEntity<List<Player>> findAllPlayersByLeagueId(Integer leagueId) {
		return new ResponseEntity<>(service.findAllPlayersByLeagueId(leagueId), HttpStatus.OK);		
	}
	
	@RequestMapping(value = "/batter", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ResponseEntity<Player> findBatter(Integer leagueId, Integer lineupId) {
		return new ResponseEntity<>(service.findPlayerByTeamIdLineUpId(leagueId, lineupId), HttpStatus.OK);		
	}
	
	@RequestMapping(value = "/full-line-up", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ResponseEntity<List<Player>> findFullTeamLineUp(Integer teamId) {
		return new ResponseEntity<>(service.findFullLineUp(teamId), HttpStatus.OK);		
	}
}
