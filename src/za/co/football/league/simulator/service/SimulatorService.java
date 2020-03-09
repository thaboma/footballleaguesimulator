package za.co.football.league.simulator.service;

import java.util.List;
import java.util.Map;

import za.co.football.league.simulator.exceptions.ClubFormatException;
import za.co.football.league.simulator.serviceimpl.dto.ClubDto;

public interface SimulatorService {
/**
 * 
 * @param club - A string with club info
 * @return - return a class\Dto representing the given club info
 * @throws ClubFormatException
 */
	ClubDto getClubInfoFromName(String club) throws ClubFormatException;
/**
 * 
 * @param clubNames - an array of strings containing information for league clubs
 * @return return a list of classes\Dtos representing the given club info
 */
	List<ClubDto> getClubInfoFromNames(List<String> clubNames);
/**
 * 
 * @param result
 * @return
 * @throws ClubFormatException
 */
	List<ClubDto> getClubInfoFromResult(String result) throws ClubFormatException;
/**
 * 
 * @param results
 * @param codeNameMap
 * @return
 */
	List<ClubDto> getClubInfoFromResults(List<String> results,Map<String, String> codeNameMap);
/**
 * 
 * @param clubs
 */
	void printLog(List<ClubDto> clubs);
/**
 * 
 * @param clubDtos
 * @return
 */
	public Map<String, String> getCodeNameMap(List<ClubDto> clubDtos);
	/**
	 * 
	 * @param clubDtos
	 * @return
	 */
	Map<String, ClubDto> getClubInfoFromClubs(List<ClubDto> clubDtos);
	/**
	 * 
	 * @param clubs
	 * @param matchResults
	 */
	void simulateGameWeek(String [] clubs, String[] matchResults);
}
