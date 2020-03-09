/**
 * 
 */
package za.co.football.league.simulator;

import za.co.football.league.simulator.exceptions.ClubFormatException;
import za.co.football.league.simulator.service.SimulatorService;
import za.co.football.league.simulator.serviceimpl.SimulatorServiceImpl;

public class SimulateGameWeek {

	static SimulatorService simulatorService;

	/**
	 * @param args
	 * @throws ClubFormatException
	 */
	public static void main(String[] args) throws ClubFormatException {
		
		simulatorService = new  SimulatorServiceImpl();
		
		//String[] clubs = new String[]{ "Liverpool (LVPL)", "Manchester City (MCTY)", "Leicester City (LCTY)", "Manchester United (MUTD)", "Chelsea (CLSE)", "Newcastle United (NCSU)", "Everton (EVTN)", "Psybergate FC (PSY)" }; 
		//String[] matchResults = new String[]{ "LVPL(2)-MCTY(2)" , "MUTD(0)-EVTN(4)" }; 
		
		String[] clubs =new String[] { "Manchester United (MUTD)", "Chelsea (CLSE)", "Newcastle United (NCSU)", "Everton (EVTN)", "Psybergate FC (PSY)", "Football Club Of The Year(FCTY)" }; 
		String[] matchResults = new String[]{ "FCTY(8)-MUTD(2)", "CLSE(0)-PSY(4)", "NCSU(1)-FCTY(1)",    "EVTN(1)-CLSE(1)" };
		simulatorService.simulateGameWeek(clubs,matchResults);		

	}

}
