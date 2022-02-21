package com.tracker.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.tracker.entity.League;
import com.tracker.entity.Team;
import com.tracker.repository.LeagueRepository;
import com.tracker.repository.TeamRepository;

@Service
public class TeamService {
	
	@Autowired
	TeamRepository teamRepository;
	
	@Autowired
	LeagueRepository leagueRepository;
	
//	public void addTeam(Team team) {
////		League league =  leagueRepository.findById(team.getLeague().getId()).get();
////		team.setLeague(league);
//		team.setWinPercentage(0.0);
//		team.setWins(0);
//		team.setLosses(0);
//		teamRepository.save(team);
//	}
	
	public List<Team> findTeams() {
		return teamRepository.findAll();
	}
	
	public List<Optional<Team>> findTeamsByLeagueId(Integer leagueId) {
		return teamRepository.findTeamByLeagueId(leagueId);
	}
	
	public Optional<Team> findTeamByLeagueIdAndId(Integer leagueId, Integer id) {
		return teamRepository.findTeamByLeagueIdAndId(leagueId, id);
	}
	
	public List<Optional<Team>> findStandings(Integer leagueId) {
		List<Optional<Team>> teams = teamRepository.findStandings(leagueId);
		Team topTeam = teams.get(0).get();
		topTeam.setGamesBack(0.0);
		teamRepository.save(topTeam);
		for(int i = 1; i < teams.size(); i++) {
			int winDifference = topTeam.getWins() - teams.get(i).get().getWins();
			int lossDifference = teams.get(i).get().getLosses() - topTeam.getLosses();
			double gamesBack = (winDifference + lossDifference)/(double) 2;
			teams.get(i).get().setGamesBack(gamesBack);
			teamRepository.save(teams.get(i).get());
		}
		
		List<Optional<Team>> standings = teamRepository.findStandings(leagueId);
		return standings;
	}
	
	public void saveTeam(MultipartFile file, String teamName, String teamAbbr, Integer leagueId) {
		Team team = new Team();
		team.setTeamName(teamName);
		team.setTeamAbbr(teamAbbr);
		try {
			team.setPhoto(file.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		League league =  leagueRepository.findById(leagueId).get();
		team.setLeague(league);
		team.setWinPercentage(0.0);
		team.setWins(0);
		team.setLosses(0);
		teamRepository.save(team);
	}
	
}
