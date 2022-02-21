package com.tracker.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tracker.entity.Game;
import com.tracker.entity.PlateAppearance;
import com.tracker.entity.Player;
import com.tracker.entity.Team;
import com.tracker.repository.GameRepository;
import com.tracker.repository.PlateAppearanceRepository;
import com.tracker.repository.PlayerRepository;
import com.tracker.repository.TeamRepository;

@Service
public class GameService {

	@Autowired
	PlateAppearanceRepository paRepository;

	@Autowired
	PlayerRepository playerRepository;

	@Autowired
	TeamRepository teamRepository;

	@Autowired
	GameRepository gameRepository;

	public Game startGame(Game game) {
		Team dbAway = teamRepository.findById(game.getAwayTeam().getId()).get();
		Team dbHome = teamRepository.findById(game.getHomeTeam().getId()).get();
		game.setAwayTeam(dbAway);
		game.setHomeTeam(dbHome);
		game.setAwayScore(0);
		game.setHomeScore(0);
		game.setAwayHits(0);
		game.setHomeHits(0);
		game.setAwayHomeruns(0);
		game.setHomeHomeruns(0);
		game.setEndGame(false);

		Game dbGame = gameRepository.save(game);

		PlateAppearance pa = new PlateAppearance();
		pa.setGame(game);
		pa.setAwayIndex(1);
		pa.setHomeIndex(0);
		pa.setStrikes(1);
		pa.setBalls(1);
		pa.setInningNum(1);
		pa.setOuts(0);
		pa.setBase(0);
		pa.setRuns(0);
		pa.setIsSacFly(false);
		pa.setInPlay(false);

//		playerRepository.findTopByOrderByIdDesc();
		pa.setPlayer(playerRepository.findTopPlayerByTeamIdAndLineUpId(game.getAwayTeam().getId(), 1).get());
		pa.getPlayer().getStats().setPaCount(pa.getPlayer().getStats().getPaCount() + 1);
		paRepository.save(pa);

		return dbGame;
	}

	public void startPA(Integer gameId) {
		List<PlateAppearance> pas = paRepository.getPAsByGameIdDesc(gameId);
		PlateAppearance lastPA = pas.get(0);

		List<Player> away = playerRepository.findLineUp(lastPA.getGame().getAwayTeam().getId());
		List<Player> home = playerRepository.findLineUp(lastPA.getGame().getHomeTeam().getId());

		int totalOuts = lastPA.getOuts();
		PlateAppearance newAtBat = new PlateAppearance();

		newAtBat.setGame(lastPA.getGame());
		newAtBat.setBalls(1);
		newAtBat.setStrikes(1);
		newAtBat.setBase(0);
		newAtBat.setInPlay(false);
		newAtBat.setIsSacFly(false);

		newAtBat.getGame().setAwayHits(lastPA.getGame().getAwayHits());
		newAtBat.getGame().setHomeHits(lastPA.getGame().getHomeHits());
		newAtBat.setRuns(lastPA.getRuns());

		if (totalOuts < 3) {
			newAtBat.setHomeIndex(lastPA.getHomeIndex());
			if (lastPA.getAwayIndex() == away.size()) {
				newAtBat.setAwayIndex(1);
			} else {
				newAtBat.setAwayIndex(lastPA.getAwayIndex() + 1);
			}
			newAtBat.setPlayer(away.get(newAtBat.getAwayIndex() - 1));
			newAtBat.getPlayer().getStats().setPaCount(newAtBat.getPlayer().getStats().getPaCount() + 1);
			newAtBat.setOuts(lastPA.getOuts());
			newAtBat.setInningNum(lastPA.getInningNum());
		}
		if (totalOuts == 3) {
//			if (lastPA.getInningNum() == 1) {
//				newAtBat.setHomeIndex(1);
//			} else {
			if (lastPA.getHomeIndex() == home.size()) {
				newAtBat.setHomeIndex(1);
			} else {
				newAtBat.setHomeIndex(lastPA.getHomeIndex() + 1);
			}
//			}
			newAtBat.setAwayIndex(lastPA.getAwayIndex());
			newAtBat.setPlayer(home.get(newAtBat.getHomeIndex() - 1));
			newAtBat.getPlayer().getStats().setPaCount(newAtBat.getPlayer().getStats().getPaCount() + 1);
			newAtBat.setOuts(lastPA.getOuts());
			newAtBat.setRuns(0);
			newAtBat.setInningNum(lastPA.getInningNum());
		}
		if (totalOuts == 4 || totalOuts == 5) {

			if (lastPA.getHomeIndex() == home.size()) {
				newAtBat.setHomeIndex(1);
			} else {
				newAtBat.setHomeIndex(lastPA.getHomeIndex() + 1);
			}
			newAtBat.setAwayIndex(lastPA.getAwayIndex());
			newAtBat.setPlayer(home.get(newAtBat.getHomeIndex() - 1));
			newAtBat.getPlayer().getStats().setPaCount(newAtBat.getPlayer().getStats().getPaCount() + 1);
			newAtBat.setOuts(lastPA.getOuts());
			newAtBat.setInningNum(lastPA.getInningNum());
		}
		if (totalOuts == 6) {
			clearBasePath(gameId);
			newAtBat.setHomeIndex(lastPA.getHomeIndex());
			if (lastPA.getAwayIndex() == away.size()) {
				newAtBat.setAwayIndex(1);
			} else {
				newAtBat.setAwayIndex(lastPA.getAwayIndex() + 1);
			}
			newAtBat.setPlayer(away.get(newAtBat.getAwayIndex() - 1));
			newAtBat.getPlayer().getStats().setPaCount(newAtBat.getPlayer().getStats().getPaCount() + 1);
			newAtBat.setOuts(0);
			newAtBat.setRuns(0);
			newAtBat.setInningNum(lastPA.getInningNum() + 1);
		}
		paRepository.save(newAtBat);
	}

	public void moveRunner(Integer gameId, Integer startingBase, Integer endingBase) {
		PlateAppearance pa = paRepository.getPAByBase(gameId, startingBase).get(0).get();

		if (startingBase == 0) {
			if (endingBase > 3) {
				pa.setBase(4);
				pa.getPlayer().getStats().setRbis(pa.getPlayer().getStats().getRbis() + 1);
				pa.getPlayer().getStats().setRuns(pa.getPlayer().getStats().getRuns() + 1);
				if (pa.getOuts() < 3) {
					pa.getGame().setAwayScore(pa.getGame().getAwayScore() + 1);
					pa.setRuns(pa.getGame().getAwayScore());
				} else {
					pa.getGame().setHomeScore(pa.getGame().getHomeScore() + 1);
					pa.setRuns(pa.getGame().getHomeScore());
				}
			} else {
				if (endingBase == -1) {
					pa.setOuts(pa.getOuts() + 1);
				}
				pa.setBase(endingBase);
			}
		} else {
			List<PlateAppearance> pas = paRepository.getPAsByGameIdDesc(gameId);
			PlateAppearance batter = pas.get(0);
			if (endingBase > 3) {
				pa.setBase(4);
				pa.getPlayer().getStats().setRuns(pa.getPlayer().getStats().getRuns() + 1);
				batter.getPlayer().getStats().setRbis(batter.getPlayer().getStats().getRbis() + 1);
				if (pa.getOuts() < 3) {
					pa.getGame().setAwayScore(pa.getGame().getAwayScore() + 1);
					batter.setRuns(pa.getGame().getAwayScore());
				} else {
					pa.getGame().setHomeScore(pa.getGame().getHomeScore() + 1);
					pa.setRuns(pa.getGame().getHomeScore());
				}
				paRepository.save(batter);
			} else {
				if (endingBase == -1) {
					batter.setOuts(pa.getOuts() + 1);
					if (batter.getOuts() == 3) {
						clearBasePath(gameId);
						batter.getPlayer().getStats().setPaCount(batter.getPlayer().getStats().getPaCount() - 1);
						batter.setAwayIndex(batter.getAwayIndex() - 1);
						startPA(gameId);
					} else if (batter.getOuts() == 6) {
						clearBasePath(gameId);
						batter.getPlayer().getStats().setPaCount(batter.getPlayer().getStats().getPaCount() - 1);
						batter.setHomeIndex(batter.getHomeIndex() - 1);
						startPA(gameId);
					}
					paRepository.save(batter);
				}
				pa.setBase(endingBase);
			}
		}
		paRepository.save(pa);
	}

	public void moveAllRunners(Integer gameId, Integer bases) {
		List<PlateAppearance> onBase = paRepository.getPAsOnBase(gameId);

		for (int i = 0; i < onBase.size(); i++) {
			PlateAppearance pa = onBase.get(i);
			if (i == 0) {
				moveRunner(gameId, 0, bases);
			} else if (onBase.get(i - 1).getBase() >= onBase.get(i).getBase()) {
				moveRunner(gameId, onBase.get(i).getBase(), onBase.get(i - 1).getBase() + 1);
			}
			paRepository.save(pa);
		}
	}

	public void outOnPlay(Game game) {
		Game dbGame = gameRepository.getById(game.getId());
		List<PlateAppearance> pas = paRepository.getPAsByGameIdDesc(dbGame.getId());
		PlateAppearance pa = pas.get(0);
		pa.setBase(-1);
		if (pa.getIsSacFly()) {
			if (pa.getOuts() > 2 && pa.getOuts() < 5) {
				pa.getPlayer().getStats().setObp(pa.getPlayer().getStats().getObp());
			} else {
				pa.setIsSacFly(false);
				pa.endPlateAppearance();
			}
		} else {
			pa.endPlateAppearance();
		}
		pa.setOuts(pa.getOuts() + 1);
		paRepository.save(pa);
		if (pa.getOuts() == 3 || pa.getOuts() == 6) {
			clearBasePath(dbGame.getId());
		}
		// paRepository.save(pa);
		startPA(dbGame.getId());
	}

	public void addStrike(Game game) {
		Game dbGame = gameRepository.getById(game.getId());
		List<PlateAppearance> pas = paRepository.getPAsByGameIdDesc(dbGame.getId());
		PlateAppearance pa = pas.get(0);
		if (pa.getBase() == 0) {
			pa.setStrikes(pa.getStrikes() + 1);
			if (pa.getStrikes() == 3) {
				pa.getPlayer().getStats().setStrikeouts(pa.getPlayer().getStats().getStrikeouts() + 1);
				outOnPlay(dbGame);
			}
			paRepository.save(pa);
		} else {
			startPA(dbGame.getId());
			pas = paRepository.getPAsByGameIdDesc(dbGame.getId());
			pa = pas.get(0);
			pa.setStrikes(2);
			paRepository.save(pa);
		}
	}

	public void addBall(Game game) {
		Game dbGame = gameRepository.getById(game.getId());
		List<PlateAppearance> pas = paRepository.getPAsByGameIdDesc(dbGame.getId());
		PlateAppearance pa = pas.get(0);
		if (pa.getBase() == 0) {
			pa.setBalls(pa.getBalls() + 1);
			if (pa.getBalls() == 4) {
				moveAllRunners(dbGame.getId(), 1);
				pa.getPlayer().getStats().setWalks(pa.getPlayer().getStats().getWalks() + 1);
				startPA(dbGame.getId());
			}
			paRepository.save(pa);
			pa.endPlateAppearance();
		} else {
			startPA(dbGame.getId());
			pas = paRepository.getPAsByGameIdDesc(dbGame.getId());
			pa = pas.get(0);
			pa.setStrikes(2);
			paRepository.save(pa);
		}
	}

	public void endGame(Game game) {
		Game dbGame = gameRepository.getById(game.getId());
		List<PlateAppearance> pas = paRepository.getPAsByGameIdDesc(dbGame.getId());
		PlateAppearance pa = pas.get(0);
		pa.getPlayer().getStats().setPaCount(pa.getPlayer().getStats().getPaCount() + 1);
		if (dbGame.getAwayScore() > dbGame.getHomeScore()) {
			dbGame.getAwayTeam().setWins(dbGame.getAwayTeam().getWins() + 1);
			dbGame.getHomeTeam().setLosses(dbGame.getHomeTeam().getLosses() + 1);
		} else {
			dbGame.getHomeTeam().setWins(dbGame.getHomeTeam().getWins() + 1);
			dbGame.getAwayTeam().setLosses(dbGame.getAwayTeam().getLosses() + 1);
		}
		dbGame.getAwayTeam().setWinPercentage(dbGame.getAwayTeam().getWinPercentage());
		dbGame.getHomeTeam().setWinPercentage(dbGame.getHomeTeam().getWinPercentage());
		dbGame.setEndGame(true);

		paRepository.save(pa);
		gameRepository.save(dbGame);
	}

	public void clearBasePath(Integer gameId) {
		List<PlateAppearance> onBase = paRepository.getPAsOnBase(gameId);
		for (PlateAppearance pa : onBase) {
			pa.setBase(null);
			paRepository.save(pa);
		}
	}

	public void ballInPlay(Game game) {
		Game dbGame = gameRepository.getById(game.getId());
		List<PlateAppearance> pas = paRepository.getPAsByGameIdDesc(dbGame.getId());
		PlateAppearance pa = pas.get(0);
		pa.setInPlay(true);
		paRepository.save(pa);
	}

	public void baseHit(Game game, Integer bases) {
		Game dbGame = gameRepository.getById(game.getId());
		List<PlateAppearance> pas = paRepository.getPAsByGameIdDesc(dbGame.getId());
		PlateAppearance pa = pas.get(0);
		Player batter = pa.getPlayer();

		if (pa.getGame().getInningEnding() != null && bases == 4 && pa.getOuts() < 3
				&& pa.getGame().getAwayHomeruns() == pa.getGame().getHomerunLimit()) {
			if (pa.getGame().getInningEnding()) {
				pa.setOuts(2);
			}
			outOnPlay(pa.getGame());
		} else if (pa.getGame().getInningEnding() != null && bases == 4 && pa.getOuts() >= 3
				&& pa.getGame().getHomeHomeruns() == pa.getGame().getHomerunLimit()) {
			if (pa.getGame().getInningEnding()) {
				pa.setOuts(5);
			}
			outOnPlay(pa.getGame());
		} else {
			switch (bases) {
			case 1:
				moveAllRunners(game.getId(), 1);
				batter.getStats().setSingles(batter.getStats().getSingles() + 1);
				break;
			case 2:
				moveAllRunners(game.getId(), 2);
				batter.getStats().setDoubles(batter.getStats().getDoubles() + 1);
				break;
			case 3:
				moveAllRunners(game.getId(), 3);
				batter.getStats().setTriples(batter.getStats().getTriples() + 1);
				break;
			case 4:
				if (pa.getOuts() < 3) {
					pa.getGame().setAwayHomeruns(pa.getGame().getAwayHomeruns() + 1);
				} else {
					pa.getGame().setHomeHomeruns(pa.getGame().getHomeHomeruns() + 1);
				}
				if(pa.getGame().getAwayHomeruns() == pa.getGame().getHomeHomeruns() && pa.getGame().getAwayHomeruns() == pa.getGame().getHomerunLimit()) {
					pa.getGame().setHomerunLimit(pa.getGame().getHomerunLimit() +1);
				}
				moveAllRunners(game.getId(), 4);
				batter.getStats().setHomeruns(batter.getStats().getHomeruns() + 1);
				break;
			}

			batter.getStats().setHits(batter.getStats().getHits() + 1);
			if (pa.getOuts() < 3) {
				pa.setRuns(pa.getGame().getAwayScore());
				if (pas.size() == 1) {
					pa.getGame().setAwayHits(1);
				} else {
					pa.getGame().setAwayHits(pas.get(1).getGame().getAwayHits() + 1);
				}
			} else {
				pa.setRuns(pa.getGame().getHomeScore());
				if (pas.size() == 1) {
					pa.getGame().setHomeHits(1);
				} else {
					pa.getGame().setHomeHits(pas.get(1).getGame().getHomeHits() + 1);
				}
			}
			pa.endPlateAppearance();
			startPA(game.getId());
		}
		paRepository.save(pa);
	}

	public void fieldersChoice(Game game) {
		Game dbGame = gameRepository.getById(game.getId());
		List<PlateAppearance> pas = paRepository.getPAsByGameIdDesc(dbGame.getId());
		PlateAppearance pa = pas.get(0);

		moveAllRunners(game.getId(), 1);
		pa.endPlateAppearance();
		startPA(game.getId());
		paRepository.save(pa);

	}

	public PlateAppearance findLastPAByGameId(Integer gameId) {
		List<PlateAppearance> pas = paRepository.getPAsByGameIdDesc(gameId);
		return pas.get(0);
	}

	public Optional<Game> findGameById(Integer id) {
		return gameRepository.findById(id);
	}

	public List<Game> findGames() {
		return gameRepository.findAll();
	}

	public List<Game> findGamesByLeagueId(Integer leagueId) {
		return gameRepository.findGamesByLeagueId(leagueId);
	}

	public Team findHomeTeamByGameId(Integer gameId) {
		Game game = gameRepository.getById(gameId);
		return game.getHomeTeam();
	}

	public Team findAwayTeamByGameId(Integer gameId) {
		Game game = gameRepository.getById(gameId);
		return game.getAwayTeam();
	}

	public List<PlateAppearance> findPAsByGameId(Integer gameId) {
		return paRepository.getPAsByGameIdDesc(gameId);
	}

	public List<PlateAppearance> findRunners(Integer gameId) {
		return paRepository.getPAsOnBase(gameId);
	}

	public void changeRunnerBase(Integer paId, Integer startingBase, Integer endingBase, Boolean isSacFly) {
		PlateAppearance dbPA = paRepository.getById(paId);
		moveRunner(dbPA.getGame().getId(), startingBase, endingBase);
		List<PlateAppearance> pas = paRepository.getPAsByGameIdDesc(dbPA.getGame().getId());
		PlateAppearance batter = pas.get(0);
		if (isSacFly) {
			batter.setIsSacFly(true);
		}
		paRepository.save(batter);
		paRepository.save(dbPA);
	}

}
