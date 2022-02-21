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
@Table(name="team")
public class Team {
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
    @Column(name = "id")
	private Integer id;
	
	@Column(name = "team_name")
	private String teamName;
	
	@Column(name = "team_abbr")
	private String teamAbbr;
	
	@Column(name = "wins")
	private Integer wins;
	
	@Column(name = "losses")
	private Integer losses;
	
	@ManyToOne
    @JoinColumn(name = "league_id")
	private League league;
	
	@Column(name = "win_percentage")
	private Double winPercentage;
	
	@Column(name = "games_back")
	private Double gamesBack;
	
	@Column(name = "photo")
	private byte[] photo; 
	
	public Team() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getTeamAbbr() {
		return teamAbbr;
	}

	public void setTeamAbbr(String teamAbbr) {
		this.teamAbbr = teamAbbr;
	}

	public Integer getWins() {
		return wins;
	}

	public void setWins(Integer wins) {
		this.wins = wins;
	}

	public Integer getLosses() {
		return losses;
	}

	public void setLosses(Integer losses) {
		this.losses = losses;
	}

	public League getLeague() {
		return league;
	}

	public void setLeague(League league) {
		this.league = league;
	}

	public Double getWinPercentage() {
		return (double)wins/(wins + losses);
	}

	public void setWinPercentage(Double winPercentage) {
		this.winPercentage = winPercentage;
	}

	public Double getGamesBack() {
		return gamesBack;
	}

	public void setGamesBack(Double gamesBack) {
		this.gamesBack = gamesBack;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}
	
	
}
