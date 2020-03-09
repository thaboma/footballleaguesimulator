package za.co.football.league.simulator.serviceimpl.dto;

public class ClubDto {

	private String name;	
	private String code;
	private int matchesPlayed;
	private int matchesDrawn;
	private int matchesLost;
	private int matchesWon;
	private int goalsFor;
	private int goalsConceded;
	private int goalDiff;
	private int points;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getMatchesPlayed() {
		return matchesPlayed;
	}

	public void setMatchesPlayed(int matchesPlayed) {
		this.matchesPlayed = matchesPlayed;
	}

	public int getMatchesDrawn() {
		return matchesDrawn;
	}

	public void setMatchesDrawn(int matchesDrawn) {
		this.matchesDrawn = matchesDrawn;
	}

	public int getMatchesLost() {
		return matchesLost;
	}

	public void setMatchesLost(int matchesLost) {
		this.matchesLost = matchesLost;
	}

	public int getGoalsFor() {
		return goalsFor;
	}

	public void setGoalsFor(int goalsFor) {
		this.goalsFor = goalsFor;
	}

	public int getGoalsConceded() {
		return goalsConceded;
	}

	public void setGoalsConceded(int goalsConceded) {
		this.goalsConceded = goalsConceded;
	}

	public int getGoalDiff() {
		return goalDiff;
	}

	public void setGoalDiff(int goalDiff) {
		this.goalDiff = goalDiff;
	}
	
	
	public int getMatchesWon() {
		return matchesWon;
	}

	public void setMatchesWon(int matchesWon) {
		this.matchesWon = matchesWon;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	@Override
	public String toString() {
		return "ClubDto [name=" + name + ", code=" + code + ", matchesPlayed=" + matchesPlayed + ", matchesDrawn="
				+ matchesDrawn + ", matchesLost=" + matchesLost + ", matchesWon=" + matchesWon + ", goalsFor="
				+ goalsFor + ", goalsConceded=" + goalsConceded + ", goalDiff=" + goalDiff + ", points=" + points + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + goalDiff;
		result = prime * result * points;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClubDto other = (ClubDto) obj;
		if (goalDiff != other.goalDiff)
			return false;
		if (points != other.points)
			return false;
		return true;
	}

	


}
