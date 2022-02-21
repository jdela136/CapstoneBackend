package com.tracker.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.tracker.entity.Player;
import com.tracker.entity.Stats;
import com.tracker.repository.PlayerRepository;

@Service
public class PlayerService {

	@Autowired
	PlayerRepository repository;
	
	public void savePlayer(Player player) {
		Stats stats = new Stats();
		player.setStats(stats);
		player.getStats().setAtBats(0);
		player.getStats().setDoubles(0);
		player.getStats().setHits(0);
		player.getStats().setHomeruns(0);
		player.getStats().setPaCount(0);
		player.getStats().setRbis(0);
		player.getStats().setRuns(0);
		player.getStats().setSingles(0);
		player.getStats().setStrikeouts(0);
		player.getStats().setTriples(0);
		player.getStats().setWalks(0);
		repository.save(player);
	}
	
	public Optional<Player> getPlayerById(Integer id) {
		return repository.findById(id);
	}
	
	public Optional<Player> getPlayerByFirstAndLast(String first, String last) {
		return repository.getPlayerByFirstAndLast(first, last);
	}
	
	public List<Player> getPlayers() {
		return repository.findAll();
	}
	
	public void addToLineUp(Player player) {
		Player dbPlayer = repository.getById(player.getId());
		dbPlayer.setLineupId(player.getLineupId());
		repository.save(dbPlayer);
	}
	
	public void removeFromLineUp(Player player) {
		Player dbPlayer = repository.getById(player.getId());
		dbPlayer.setLineupId(null);
		repository.save(dbPlayer);
	}
	
//	public void setLineUp(List<Optional<Player>> players, List<Integer> lineUpPositions) {
//		
//	}
	
	public List<Player> findTeamRoster(Integer teamId) {
		return repository.findTeamRoster(teamId);
	}
	
	public List<Player> findTeamLineUp(Integer teamId) {
		return repository.findLineUp(teamId);
	} 
	
	public List<Player> findAllPlayersByLeagueId(Integer leagueId) {
		return repository.findAllPlayersByLeagueId(leagueId);
	}
	
	public Player findPlayerByTeamIdLineUpId(Integer teamId, Integer lineupId) {
		return repository.findPlayerByTeamIdLineUpId(teamId, lineupId).get(0);
	}
	
	public List<Player> findFullLineUp(Integer teamId) {
		return repository.findFullLineUp(teamId);
	}
}
