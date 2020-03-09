package za.co.football.league.simulator.service;


import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import junit.framework.TestCase;
import za.co.football.league.simulator.exceptions.ClubFormatException;
import za.co.football.league.simulator.serviceimpl.SimulatorServiceImpl;
import za.co.football.league.simulator.serviceimpl.dto.ClubDto;

public class SimulatorServiceTest extends TestCase{
	static SimulatorService simulatorService;

	
	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
		simulatorService = new SimulatorServiceImpl();
	}	

	@Test
	public void testGetClubInfoFromName() throws ClubFormatException {
		System.out.println("testGetClubInfoFromName");

		String clubInfo="Psybergate FC (PSY)";
		ClubDto clubDto =simulatorService.getClubInfoFromName(clubInfo);
	 	System.out.println(clubDto.getName()+ " is the name of the club.\r\n");
		Assert.assertNotNull(clubDto);
		Assert.assertEquals("PSY", clubDto.getCode());
		Assert.assertEquals("This is the name of the club ","Psybergate FC", clubDto.getName());
	}



	@Test
	public void testGetClubInfoFromNames() {
		System.out.println("testGetClubInfoFromNames");
		
		List<String> clubs = Arrays.<String>asList("Liverpool (LVPL)", "Manchester City (MCTY)");
		List<ClubDto> clubDtos =simulatorService.getClubInfoFromNames(clubs);
		
	 	System.out.println(clubDtos+"\r\n");
		Assert.assertNotNull(clubDtos.get(0));
		Assert.assertEquals("LVPL", clubDtos.get(0).getCode());
		Assert.assertEquals("This is the name of the club ","Liverpool", clubDtos.get(0).getName());

		Assert.assertNotNull(clubDtos.get(1));
		Assert.assertEquals("MCTY", clubDtos.get(1).getCode());
		Assert.assertEquals("This is the name of the club ","Manchester City", clubDtos.get(1).getName());
		
	}

	@Test
	public void testGetClubInfoFromResult() throws ClubFormatException {
	 	System.out.println("testGetClubInfoFromResult");
		
	 	String result="FCTY(8)-MUTD(2)";
		List<ClubDto> clubDtos = simulatorService.getClubInfoFromResult(result);
	 	System.out.println(clubDtos+"\r\n");
		Assert.assertNotNull(clubDtos.get(0));
		Assert.assertNotNull(clubDtos.get(1));
		Assert.assertEquals("FCTY", clubDtos.get(0).getCode());
		Assert.assertEquals("MUTD", clubDtos.get(1).getCode());
		Assert.assertEquals(8, clubDtos.get(0).getGoalsFor());
		Assert.assertEquals(2, clubDtos.get(1).getGoalsFor());
		//Assert.assertEquals(2, clubDtos.get(1).getGoalsFor());;

	}

	@Test
	public void testGetClubInfoFromResults() throws ClubFormatException {
		System.out.println("testGetClubInfoFromResults");
 		List<String> matchResults = Arrays.<String>asList("FCTY(8)-MUTD(2)", "CLSE(0)-PSY(4)");
 		
		List<String> clubsInfo = Arrays.<String>asList("Manchester United (MUTD)", "Chelsea (CLSE)",
		"Newcastle United (NCSU)", "Everton (EVTN)", "Psybergate FC (PSY)", "Football Club Of The Year(FCTY)");
 		
		List<ClubDto> clubDtos = simulatorService.getClubInfoFromNames(clubsInfo);
 		
 		List<ClubDto> matchResult1 =simulatorService.getClubInfoFromResult(matchResults.get(0));
	 	System.out.println("result "+matchResults.get(0)+" ["+matchResult1);
 		Map<String, String> codeNameMap1 = simulatorService.getCodeNameMap(clubDtos);
 		System.out.println("codeNameMap1 "+codeNameMap1);
 		List<ClubDto> clubDtos1 =simulatorService.getClubInfoFromResults(matchResults,codeNameMap1);
 		System.out.println("clubDtos1 "+clubDtos1+"\r\n");
	 	System.out.println(clubDtos1);
		Assert.assertNotNull(matchResult1.get(0));
		Assert.assertNotNull(matchResult1.get(1));
		Assert.assertEquals("FCTY", matchResult1.get(0).getCode());
		Assert.assertEquals("MUTD", matchResult1.get(1).getCode());
		Assert.assertEquals(8, matchResult1.get(0).getGoalsFor());
		Assert.assertEquals(2, matchResult1.get(1).getGoalsFor());
 		
 		List<ClubDto> matchResult2 =simulatorService.getClubInfoFromResult(matchResults.get(1)); 		
 		Map<String, String> codeNameMap2 = simulatorService.getCodeNameMap(clubDtos); 	
 		List<ClubDto> clubDtos2 =simulatorService.getClubInfoFromResults(matchResults,codeNameMap2);
 		
 		System.out.println("clubDtos2 "+clubDtos2+"\r\n"); 
		Assert.assertNotNull(matchResult2.get(0));
		Assert.assertNotNull(matchResult2.get(1));
		Assert.assertEquals("CLSE", matchResult2.get(0).getCode());
		Assert.assertEquals("PSY", matchResult2.get(1).getCode());
		Assert.assertEquals(0, matchResult2.get(0).getGoalsFor());
		Assert.assertEquals(4, matchResult2.get(1).getGoalsFor());

	}

	@Test
	public void testPrintLog() {
		System.out.println("testPrintLog");
 		
		String[] clubs = new String[]{ "Liverpool (LVPL)", "Manchester City (MCTY)", "Leicester City (LCTY)", "Manchester United (MUTD)", "Chelsea (CLSE)", "Newcastle United (NCSU)", "Everton (EVTN)", "Psybergate FC (PSY)" }; 
		String[] matchResults = new String[]{ "LVPL(2)-MCTY(2)" , "MUTD(0)-EVTN(4)" }; 
		simulatorService.simulateGameWeek(clubs,matchResults);
	}

	@Test
	public void testGetCodeNameMap() {
		System.out.println("testGetCodeNameMap");
		
		List<String> clubs = Arrays.<String>asList("Liverpool (LVPL)", "Manchester City (MCTY)");
		List<ClubDto> clubDtos =simulatorService.getClubInfoFromNames(clubs);
		
	 	System.out.println(clubDtos);

		Map<String, String> codeNameMap = simulatorService.getCodeNameMap(clubDtos);
		System.out.println(codeNameMap);
		
		Assert.assertNotNull(clubDtos.get(0));
		Assert.assertNotNull(clubDtos.get(1));
		Assert.assertEquals("LVPL", clubDtos.get(0).getCode());
		Assert.assertEquals("MCTY", clubDtos.get(1).getCode());
		Assert.assertEquals("Liverpool", clubDtos.get(0).getName());
		Assert.assertEquals("Manchester City", clubDtos.get(1).getName());
	}

	@Test
	public void testGetClubInfoFromClubs() {
		System.out.println("testGetClubInfoFromClubs");
		List<String> clubsInfo = Arrays.<String>asList("Manchester United (MUTD)", "Chelsea (CLSE)",
		"Newcastle United (NCSU)", "Everton (EVTN)", "Psybergate FC (PSY)", "Football Club Of The Year(FCTY)");
 		
		List<ClubDto> clubDtos = simulatorService.getClubInfoFromNames(clubsInfo);
		Map<String, ClubDto> code2DtoMap = simulatorService.getClubInfoFromClubs(clubDtos);
	
		System.out.println(code2DtoMap);
		
		Assert.assertNotNull(clubDtos);
		Assert.assertTrue(clubDtos.size() ==6);
		Assert.assertTrue(clubDtos.get(0).getPoints()==0);
		Assert.assertTrue(code2DtoMap.keySet().contains("NCSU"));
		
	}

}
