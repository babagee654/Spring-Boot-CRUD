package ca.sheridancollege.databases;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import ca.sheridancollege.beans.Mission;
import ca.sheridancollege.beans.Spy;

@Repository
public class DatabaseAccess {
	
	private NamedParameterJdbcTemplate jdbc;
	
	public DatabaseAccess(NamedParameterJdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}
	
	// Retrieve all spies
	public List<Spy> getSpies(){
		
		String query = "SELECT * FROM spies";
		
		BeanPropertyRowMapper<Spy> mapper = new BeanPropertyRowMapper<>(Spy.class);
		
		List<Spy> spyList = jdbc.query(query, mapper);
		
		return spyList;
	}
	
	// Retrieve specific Spy based on spy_id
	public Spy getSpy(int id) {
		
		String query = "SELECT * FROM spies WHERE spy_id = :id";
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", id);
		
		BeanPropertyRowMapper<Spy> mapper = new BeanPropertyRowMapper<>(Spy.class);
		
		List<Spy> spy = jdbc.query(query, params, mapper);
		
		return spy.get(0);
	}
	
	// Retrieve all missions based on spy_id
	public List<Mission> getMissions(int id) {
		String query = "SELECT * FROM missions WHERE spy_id = :id";
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", id);
		
		BeanPropertyRowMapper<Mission> mapper = new BeanPropertyRowMapper<>(Mission.class);
		
		List<Mission> missions = jdbc.query(query, params, mapper);
		
		return missions;
	}
	
	// Retrieve specific mission based on mission_id
	public Mission getMission(int id) {
		
		String query = "SELECT * FROM missions WHERE mission_id = :id";
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", id);
		
		BeanPropertyRowMapper<Mission> mapper = new BeanPropertyRowMapper<>(Mission.class);
		
		List<Mission> mission = jdbc.query(query, params, mapper);
		
		return mission.get(0);
	}
	
	// Create/Add a mission
	public int createMission(Mission createdMission) {
		
		String query = "INSERT INTO missions (mission_name, gadget_1, gadget_2, spy_id)"
				+ "VALUES (:mission_name, :gadget_1, :gadget_2, :spy_id)";
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("mission_name", createdMission.getMission_name())
			.addValue("gadget_1", createdMission.getGadget_1())
			.addValue("gadget_2", createdMission.getGadget_2())
			.addValue("spy_id", createdMission.getSpy_id());
		
		int queryResult = jdbc.update(query, params);
		
		return queryResult;
	}
	
	// Update a mission
	public int updateMission(Mission updatedMission) {
		String query = "UPDATE missions SET mission_name = :mission_name, gadget_1 = :gadget_1, gadget_2 = :gadget_2 WHERE mission_id = :mission_id";
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		params.addValue("mission_id", updatedMission.getMission_id())
			.addValue("gadget_1", updatedMission.getGadget_1())
			.addValue("gadget_2", updatedMission.getGadget_2())
			.addValue("mission_name", updatedMission.getMission_name());
		
		int queryResult = jdbc.update(query, params);
		
		return queryResult;
	}
	
	// Delete a mission
	public int deleteMission(int id) {
		String query = "DELETE FROM missions WHERE mission_id = :mission_id";
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		params.addValue("mission_id", id);
		
		int queryResult = jdbc.update(query, params);
		
		return queryResult;
	}

}
