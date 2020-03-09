package za.co.football.league.simulator.serviceimpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import za.co.football.league.simulator.exceptions.ClubFormatException;
import za.co.football.league.simulator.service.SimulatorService;
import za.co.football.league.simulator.serviceimpl.dto.ClubDto;

public class SimulatorServiceImpl implements SimulatorService {

	static Logger log = Logger.getLogger(SimulatorServiceImpl.class.getName());

	@Override
	public ClubDto getClubInfoFromName(String club) throws ClubFormatException {
		ClubDto clubDto = new ClubDto();
		// System.out.println("........."+club);

		StringBuilder name = new StringBuilder();
		StringBuilder code = new StringBuilder();

		if (club != null && !club.isEmpty()) {

			String[] tokens = club.split(" ");

			if (tokens.length < 2) {
				throw new ClubFormatException("Club name not in a correct format");
			} else if (tokens.length == 2) {
				name.append(tokens[0].trim());
				code = code.append(tokens[1].replace("(", "").replace(")", "").toString());
			} else {
				name.append(club.substring(0, club.indexOf("(")).toString()).toString();
				code.append(club.substring(club.lastIndexOf("(") + 1, club.lastIndexOf(")")).toString().toString());
			}

			clubDto.setName(name.toString().trim());
			clubDto.setCode(code.toString().trim());

		}
		return clubDto;
	}

	@Override
	public List<ClubDto> getClubInfoFromNames(List<String> clubNames) {
		List<ClubDto> clubDtos = new ArrayList<>();
		clubNames.forEach(cn -> {
			try {
				clubDtos.add(getClubInfoFromName(cn));
			} catch (ClubFormatException e) {
				// log.warning("Name format issue with club name ["+cn+"] "+e);
				System.out.println("Name format issue with club name [" + cn + "] " + e);
			}
		});
		return clubDtos;
	}

	@Override
	public List<ClubDto> getClubInfoFromResult(String result) throws ClubFormatException {

		List<ClubDto> clubs = new ArrayList<ClubDto>();
		String[] clubResultsTokens = result.split("-");

		if (clubResultsTokens.length != 2) {
			throw new ClubFormatException("Club results not in a correct format");
		} else {
			StringBuilder clubToken1 = new StringBuilder(clubResultsTokens[0]);
			StringBuilder clubToken2 = new StringBuilder(clubResultsTokens[1]);

			ClubDto clubDto1 = new ClubDto();
			ClubDto clubDto2 = new ClubDto();

			String clubCode1 = clubToken1.substring(0, clubToken1.indexOf("("));
			clubDto1.setCode(clubCode1);

			String clubCode2 = clubToken2.substring(0, clubToken2.indexOf("("));
			clubDto2.setCode(clubCode2);

			String goalsForClub1 = clubToken1.substring(clubToken1.indexOf("(") + 1, clubToken1.indexOf(")"));
			int club1Goals = Integer.parseInt(goalsForClub1);
			clubDto1.setGoalsFor(club1Goals);

			String goalsForClub2 = clubToken2.substring(clubToken2.indexOf("(") + 1, clubToken2.indexOf(")"));
			int club2Goals = Integer.parseInt(goalsForClub2);
			clubDto2.setGoalsFor(club2Goals);

			clubDto2.setGoalsConceded(club1Goals);
			clubDto1.setGoalsConceded(club2Goals);

			int club1GoalDiff = club1Goals - club2Goals;
			clubDto1.setGoalDiff(club1GoalDiff);

			int club2GoalDiff = club2Goals - club1Goals;
			clubDto2.setGoalDiff(club2GoalDiff);

			if (club1Goals - club2Goals == 0) {
				clubDto1.setPoints(clubDto1.getPoints() + 1);
				clubDto2.setPoints(clubDto2.getPoints() + 1);

				clubDto1.setMatchesDrawn(1);
				clubDto2.setMatchesDrawn(1);
			} else if (club1Goals > club2Goals) {
				clubDto1.setPoints(clubDto1.getPoints() + 3);
				clubDto2.setMatchesLost(clubDto2.getMatchesLost() + 1);
				clubDto1.setMatchesWon(clubDto1.getMatchesWon() + 1);
			} else {
				clubDto2.setPoints(clubDto2.getPoints() + 3);
				clubDto1.setMatchesLost(clubDto1.getMatchesLost() + 1);
				clubDto2.setMatchesWon(clubDto2.getMatchesWon() + 1);
			}

			clubDto1.setMatchesPlayed(clubDto1.getMatchesPlayed() + 1);
			clubDto2.setMatchesPlayed(clubDto2.getMatchesPlayed() + 1);

			clubs.add(clubDto1);
			clubs.add(clubDto2);
		}

		return clubs;
	}

	@Override
	public List<ClubDto> getClubInfoFromResults(List<String> results, Map<String, String> codeNameMap) {

		Map<String, List<ClubDto>> code2ClubDtoMap = new HashMap<>();
		List<ClubDto> resultDtos = new ArrayList<ClubDto>();

		results.forEach(r -> {
			List<ClubDto> gameResultDtos;

			try {
				gameResultDtos = getClubInfoFromResult(r);
				gameResultDtos.forEach(g -> {

					String code = g.getCode();
					g.setName(codeNameMap.get(code));

					if (code2ClubDtoMap.get(code) == null || code2ClubDtoMap.get(code).isEmpty()) {
						code2ClubDtoMap.put(code, new ArrayList<ClubDto>());
						code2ClubDtoMap.get(code).add(g);
					} else {

						code2ClubDtoMap.get(code).forEach(d -> {
							if (code != null && code.equalsIgnoreCase(d.getCode())) {
								d.setMatchesPlayed(d.getMatchesPlayed() + g.getMatchesPlayed());
								d.setMatchesDrawn(d.getMatchesDrawn() + g.getMatchesDrawn());
								d.setMatchesLost(d.getMatchesLost() + g.getMatchesLost());
								d.setMatchesWon(d.getMatchesWon() + g.getMatchesWon());
								d.setGoalsFor(d.getGoalsFor() + g.getGoalsFor());
								d.setGoalsConceded(d.getGoalsConceded() + g.getGoalsConceded());
								d.setGoalDiff(d.getGoalDiff() + g.getGoalDiff());
								d.setPoints(d.getPoints() + g.getPoints());
							}
						});

						List<ClubDto> codeList = new ArrayList<ClubDto>(code2ClubDtoMap.get(code));
						code2ClubDtoMap.get(code).clear();
						code2ClubDtoMap.put(code, codeList);
					}
				});
			} catch (ClubFormatException e) {
				System.out.println(e);
			}
		});

		code2ClubDtoMap.forEach((String c, List<ClubDto> dtos) -> {
			resultDtos.addAll(dtos);
		});

		return 	resultDtos;
	}

	@Override
	public void printLog(List<ClubDto> clubs) {

		String nameFormat = " %1$-25s  ";
		String matchesPlayed = " %2$3s";
		String matchesWon = " %3$4s";
		String matchesDrawn = " %4$3s";
		String matchesLost = " %5$3s";
		String goalsFor = " %6$3s";
		String goalsConceded = " %7$3s";
		String goalDiff = " %8$3s";
		String points = " %9$3s";
		String newLine = "\n";

		String format = nameFormat.concat(matchesPlayed).concat(matchesWon).concat(matchesDrawn).concat(matchesLost)
				.concat(goalsFor).concat(goalsConceded).concat(goalDiff).concat(points).concat(newLine);

		System.out.printf(format, "Club", "MP", "W", "D", "L", "GF", "GA", "GD", "Pts");
		System.out.println("-------------------------------------------------------------");
		clubs.forEach(d -> {
			System.out.printf(format, d.getName(), d.getMatchesPlayed(), d.getMatchesWon(), d.getMatchesDrawn(),
					d.getMatchesLost(), d.getGoalsFor(), d.getGoalsConceded(), d.getGoalDiff(), d.getPoints());
		});
	}

	@Override
	public Map<String, ClubDto> getClubInfoFromClubs(List<ClubDto> clubDtos) {
		Map<String, ClubDto> code2ClubDtoMap = new TreeMap<>();
		clubDtos.forEach(cb -> {
			String code = cb.getCode();
			code2ClubDtoMap.put(code, cb);
		});

		return code2ClubDtoMap;
	}

	@Override
	public Map<String, String> getCodeNameMap(List<ClubDto> clubDtos) {
		Map<String, String> code2ClubDtoMap = new TreeMap<>();
		clubDtos.forEach(cb -> {
			String code = cb.getCode().trim();
			String name = cb.getName().trim();
			code2ClubDtoMap.put(code, name);
		});

		return code2ClubDtoMap;
	}

	@Override
	public void simulateGameWeek(String[] clubs, String[] matchResults) {

		List<String> clubsInfo = Arrays.<String>asList(clubs);
		List<String> matchResultsInfo = Arrays.<String>asList(matchResults);

		List<ClubDto> clubDtos = getClubInfoFromNames(clubsInfo);

		Map<String, String> codeNameMap = getCodeNameMap(clubDtos);

		List<ClubDto> resultDtos = getClubInfoFromResults(matchResultsInfo, codeNameMap);
		Map<String, ClubDto> code2DtoMap = getClubInfoFromClubs(clubDtos);

		List<ClubDto> playedAlready = new ArrayList<ClubDto>();

		List<String> clubCodesList = new ArrayList<String>(codeNameMap.keySet());
		List<String> idleClubs = new ArrayList<>();

		resultDtos.forEach(r -> {
			if (clubCodesList.contains(r.getCode())) {
				idleClubs.add(r.getCode());
			}
		});

		Set<String> unplayedSet = code2DtoMap.keySet();
		unplayedSet.removeAll(idleClubs);

		unplayedSet.forEach(u -> {
			resultDtos.add(code2DtoMap.get(u));
		});

		resultDtos.addAll(playedAlready);

		Comparator<ClubDto> clubDtoComparator = (ClubDto o2, ClubDto o1) -> {
			int value1 = o1.getPoints() - o2.getPoints();
			if (value1 == 0) {
				int value2 = o1.getGoalDiff() - o2.getGoalDiff();
				if (value2 == 0) {
					int value3 = o1.getGoalsFor() - o2.getGoalsFor();
					if (value3 == 0) {
						return o2.getName().compareTo(o1.getName());
					} else {
						return value3;
					}
				} else {
					return value2;
				}
			}
			return value1;
		};

		List<ClubDto> logDtos = resultDtos.stream().sorted(clubDtoComparator).collect(Collectors.toList());
		printLog(logDtos);

	}

}
