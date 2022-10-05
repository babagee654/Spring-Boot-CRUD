package ca.sheridancollege.controllers;

/**
 * Author: Jerome Acosta
 */

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.sheridancollege.beans.Mission;
import ca.sheridancollege.beans.Spy;
import ca.sheridancollege.databases.DatabaseAccess;

@Controller
public class HomeController {
	
	private DatabaseAccess db;
	
	public HomeController(DatabaseAccess db) {
		this.db = db;
	}
	
	// Reused redirect string often so remade as private method
	private String redirectToAgentMissions(int id) {
		return "redirect:/viewAgentMission/" + id;
	}
	
	// Get Home page
	@GetMapping("/")
	public String goHome(Model model) {
		
		// Retrieve List of spies for select input.
		List<Spy> spyList = db.getSpies();
		
		model.addAttribute("spyList", spyList);
		
		return "index";
	}
	
	// Retrieve spy_id from Home Page form to redirect to correct list of missions
	@PostMapping("/viewAgent")
	public String viewAgent(@RequestParam int spy_id) {
		return redirectToAgentMissions(spy_id);
	}
	
	// Get list of missions
	@GetMapping("/viewAgentMission/{id}")
	public String viewAgentMission(@PathVariable int id, Model model) {
		
		// Retrieve specific spy
		Spy currentSpy = db.getSpy(id);
		
		// Retrieve the list of missions with the same spy_id
		List<Mission> missions = db.getMissions(id);
		
		model.addAttribute("spy", currentSpy);
		model.addAttribute("missionsList", missions);
		
		return "viewMission";
	}
	
	// Get Edit form
	@GetMapping("/editMission/{missionId}")
	public String editAgentMission(@PathVariable int missionId, Model model) {
		
		// Retrieve specific mission based on mission id
		Mission mission = db.getMission(missionId);
		// Retrieve mission owner based on mission's spy_id
		Spy missionOwner = db.getSpy(mission.getSpy_id());
		
		model.addAttribute("mission", mission);
		model.addAttribute("missionOwner", missionOwner);
		
		return "editform";
	}
	
	// Update item in DB
	@PostMapping("/editMission/{mission_id}")
	public String updateAgentMission(@ModelAttribute Mission mission) {
		
		int updateResult = db.updateMission(mission);
		
		System.out.println("Updated rows: " + updateResult);
		
		// Redirect back to table of agent's missions
		return redirectToAgentMissions(mission.getSpy_id());
	}
	
	// Delete item from DB
	@GetMapping("/deleteMission/{missionId}")
	public String deleteAgentMission(@PathVariable int missionId) {
		
		// Retrieving mission to before deleting to get spy_id for redirection.
		Mission mission = db.getMission(missionId);

		int deleteResult = db.deleteMission(missionId);
		
		System.out.println("Deleted rows: " + deleteResult);
		
		return redirectToAgentMissions(mission.getSpy_id());
	}
	
	// Get Create form
	@GetMapping("/createMission")
	public String createMission(Model model) {
		List<Spy> spyList = db.getSpies();
		
		// Provide a Mission object and List of spies for the form.
		model.addAttribute("newMission", new Mission());
		model.addAttribute("spyList", spyList);
		
		
		return "createForm";
	}
	
	// Add item to DB
	@PostMapping("/createMission")
	public String addMission(@ModelAttribute Mission mission) {

		int createResult = db.createMission(mission);
		
		System.out.println("Created rows: " + createResult);
		
		return redirectToAgentMissions(mission.getSpy_id());
	}
}
