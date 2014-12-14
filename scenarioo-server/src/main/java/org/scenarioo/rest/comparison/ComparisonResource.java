package org.scenarioo.rest.comparison;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.scenarioo.rest.scenario.dto.PageComparison;
import org.scenarioo.rest.scenario.dto.ScenarioComparison;
import org.scenarioo.rest.scenario.dto.StepComparison;
	
@Path("/rest/branch/{branchName}/build/{buildName}/usecase/{usecaseName}/scenario/")
public class ComparisonResource {

	
	@GET
	@Produces({ "application/xml" })
	@Path("{scenarioName}/compareBranch/{compareBranch}/compareBuild/{compareBuild}")
	public ScenarioComparison readScenarioWithPagesAndSteps(@PathParam("branchName") final String branchName,
			@PathParam("buildName") final String buildName, @PathParam("usecaseName") final String usecaseName,
			@PathParam("scenarioName") final String scenarioName, @PathParam("compareBranch") final String compareBranch,
			@PathParam("compareBuild") final String compareBuild) {
		
		return createDummy(scenarioName, usecaseName,branchName, buildName, compareBranch, compareBuild);
	}

	private ScenarioComparison createDummy(String scenarioName,String usecaseName, String branchName, String buildName, String compareBranch, String compareBuild) {
		ScenarioComparison dummy = new ScenarioComparison();
		dummy.setScenarioName(scenarioName);
		dummy.setUsecaseName(usecaseName);
		dummy.setBranchName(branchName);
		dummy.setBuildName(buildName);
		dummy.setCompareBranch(compareBranch);
		dummy.setCompareBuild(compareBuild);
		StepComparison step1page1 = new StepComparison();
		step1page1.setStepName("Page1Step1");
		step1page1.setSimilarity(100);
		step1page1.setLeftURL("leftmein.jpg");
		step1page1.setRighURL("rightmein.jpg");
		StepComparison step2page1 = new StepComparison();
		step2page1.setStepName("Page1Step2");
		step2page1.setSimilarity(50);
		step2page1.setLeftURL("leftmein.jpg");
		ArrayList<StepComparison> stepListPage1 = new ArrayList<StepComparison>();
		StepComparison step1page2 = new StepComparison();
		step1page2.setStepName("Page2Step1");
		step1page2.setSimilarity(80);
		step1page2.setRighURL("rightmein.jpg");
		StepComparison step2page2 = new StepComparison();
		step2page2.setStepName("Page2Step2");
		step2page2.setSimilarity(50);
		step2page2.setLeftURL("leftmein.jpg");
		stepListPage1.add(step1page1);
		stepListPage1.add(step2page1);
		ArrayList<StepComparison> stepListPage2 = new ArrayList<StepComparison>();
		stepListPage2.add(step1page2);
		stepListPage2.add(step2page2);
		PageComparison page1 = new PageComparison();
		page1.setPageName("page1");
		page1.setSteplist(stepListPage1);
		PageComparison page2 = new PageComparison();
		page2.setPageName("page2");
		page2.setSteplist(stepListPage2);
		ArrayList<PageComparison> pageList = new ArrayList<PageComparison>();
		pageList.add(page1);
		pageList.add(page2);
		
		
		dummy.setPagelist(pageList);
		return dummy;
	}
	
}
