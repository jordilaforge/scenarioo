package org.scenarioo.rest.comparison;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.scenarioo.rest.scenario.dto.ScenarioComparison;
	
@Path("/rest/branch/{branchName}/build/{buildName}/usecase/{usecaseName}/scenario/")
public class ComparisonResource {

	
	@GET
	@Produces({ "application/xml" })
	@Path("{scenarioName}/compareBranch/{compareBranch}/compareBuild/{compareBuild}")
	public ScenarioComparison readScenarioWithPagesAndSteps(@PathParam("branchName") final String branchName,
			@PathParam("buildName") final String buildName, @PathParam("usecaseName") final String usecaseName,
			@PathParam("scenarioName") final String scenarioName, @PathParam("compareBranch") final String compareBranch,
			@PathParam("compareBuild") final String compareBuild) {
		
		// TODO Return Dummy Object
		
		return createDummy();
	}

	private ScenarioComparison createDummy() {
		ScenarioComparison dummy = new ScenarioComparison();
		dummy.setLeftBranchName("left");
		dummy.setRightBranchName("right");
		return dummy;
	}
	
}
