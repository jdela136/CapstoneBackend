package com.tracker.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tracker.entity.Team;
import com.tracker.service.TeamService;

@CrossOrigin
@RestController
public class TeamController {
	
	@Autowired
	TeamService service;
	
//	@RequestMapping(value = "save-team", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
//	public void addTeam(@RequestBody Team team) {
//		service.addTeam(team);
//	}
	
	@RequestMapping(value = "/leagues/teams", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ResponseEntity<List<Team>> findTeams() {
		return new ResponseEntity<>(service.findTeams(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/teams", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ResponseEntity<List<Optional<Team>>> findTeamsByLeagueId(Integer leagueId) {
		return new ResponseEntity<>(service.findTeamsByLeagueId(leagueId), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/leagues/{leagueId}/teams/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ResponseEntity<Optional<Team>> findTeamByLeagueIdAndId(Integer leagueId, Integer id) {
		return new ResponseEntity<>(service.findTeamByLeagueIdAndId(leagueId , id), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/standings", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ResponseEntity<List<Optional<Team>>> findStandings(Integer leagueId) {
		return new ResponseEntity<>(service.findStandings(leagueId), HttpStatus.OK);
	}
	
	@PostMapping(value = "/save-team", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<HttpStatus> saveTeam(@RequestParam MultipartFile file, @RequestParam String teamName, @RequestParam String teamAbbr, @RequestParam Integer leagueId) {
		service.saveTeam(file, teamName, teamAbbr, leagueId);
        return ResponseEntity.ok().build();
    }
}
