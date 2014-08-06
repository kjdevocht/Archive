package shared.comms;

import java.util.ArrayList;
import shared.model.Project;

public class GetProjectsResult {
	ArrayList<Project> projects = new ArrayList<Project>();

	
	public GetProjectsResult (){
		
	}
	
	public GetProjectsResult (ArrayList<Project> projects0){
		projects = projects0;
	}
	
	/**
	 * @return the projects
	 */
	public ArrayList<Project> getProjects() {
		return projects;
	}

	/**
	 * @param projects the projects to set
	 */
	public void setProjects(ArrayList<Project> projects) {
		this.projects = projects;
	}	
}
