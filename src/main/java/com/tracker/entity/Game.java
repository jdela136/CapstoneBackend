package com.tracker.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="game")
public class Game {
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
    @Column(name = "id")
	private Integer id;
	
//	@Column(name = "game_id")
//	private Integer gameId;
	
	@ManyToOne
	@JoinColumn(name = "home_team_id")
	private Team homeTeam;
	
	@ManyToOne
	@JoinColumn(name = "away_team_id")
	private Team awayTeam;
	
	@Column(name = "home_score")
	private Integer homeScore;
	
	@Column(name = "away_score")
	private Integer awayScore;
	
	@Column(name = "away_hits")
	private Integer awayHits;
	
	@Column(name = "home_hits")
	private Integer homeHits;
	
	@Column(name = "away_homeruns")
	private Integer awayHomeruns;
	
	@Column(name = "home_homeruns")
	private Integer homeHomeruns;
	
	@Column(name = "end_game")
	private Boolean endGame;
	
	@Column(name = "homerun_limit")
	private Integer homerunLimit;
	
	@Column(name = "inning_ending")
	private Boolean inningEnding;
	
	public Game() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
//	public Integer getGameId() {
//		return gameId;
//	}
//
//	public void setGameId(Integer gameId) {
//		this.gameId = gameId;
//	}

	public Team getAwayTeam() {
		return awayTeam;
	}

	public void setAwayTeam(Team awayTeam) {
		this.awayTeam = awayTeam;
	}

	public Integer getAwayScore() {
		return awayScore;
	}

	public void setAwayScore(Integer score) {
		this.awayScore = score;
	}

	public Team getHomeTeam() {
		return homeTeam;
	}

	public void setHomeTeam(Team homeTeam) {
		this.homeTeam = homeTeam;
	}

	public Integer getHomeScore() {
		return homeScore;
	}

	public void setHomeScore(Integer homeScore) {
		this.homeScore = homeScore;
	}

	public Integer getAwayHits() {
		return awayHits;
	}

	public void setAwayHits(Integer awayHits) {
		this.awayHits = awayHits;
	}

	public Integer getHomeHits() {
		return homeHits;
	}

	public void setHomeHits(Integer homeHits) {
		this.homeHits = homeHits;
	}
	
	public Boolean getEndGame() {
		return endGame;
	}

	public void setEndGame(Boolean endGame) {
		this.endGame = endGame;
	}

	public Integer getAwayHomeruns() {
		return awayHomeruns;
	}

	public void setAwayHomeruns(Integer awayHomeruns) {
		this.awayHomeruns = awayHomeruns;
	}

	public Integer getHomeHomeruns() {
		return homeHomeruns;
	}

	public void setHomeHomeruns(Integer homeHomeruns) {
		this.homeHomeruns = homeHomeruns;
	}

	public Integer getHomerunLimit() {
		return homerunLimit;
	}

	public void setHomerunLimit(Integer homerunLimit) {
		this.homerunLimit = homerunLimit;
	}

	public Boolean getInningEnding() {
		return inningEnding;
	}

	public void setInningEnding(Boolean inningEnding) {
		this.inningEnding = inningEnding;
	}
	
	
}
