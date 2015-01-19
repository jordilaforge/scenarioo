package org.scenarioo.rest.comparison;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.log4j.Logger;
import org.scenarioo.api.ScenarioDocuReader;
import org.scenarioo.business.comparison.CompareScreenshot;
import org.scenarioo.model.docu.entities.Step;
import org.scenarioo.rest.scenario.dto.PageComparison;
import org.scenarioo.rest.scenario.dto.ScenarioComparison;
import org.scenarioo.rest.scenario.dto.StepComparison;
import org.scenarioo.rest.step.StepResource;
	
@Path("/rest/branch/{branchName}/build/{buildName}/usecase/{usecaseName}/scenario/")
public class ComparisonResource {

	private static final Logger LOGGER = Logger.getLogger(StepResource.class);
	
	@GET
	@Produces({ "application/xml" })
	@Path("{scenarioName}/compareBranch/{compareBranch}/compareBuild/{compareBuild}")
	public ScenarioComparison readScenarioWithPagesAndSteps(@PathParam("branchName") final String branchName,
			@PathParam("buildName") final String buildName, @PathParam("usecaseName") final String usecaseName,
			@PathParam("scenarioName") final String scenarioName, @PathParam("compareBranch") final String compareBranch,
			@PathParam("compareBuild") final String compareBuild) {
		
		//return createDummy(scenarioName, usecaseName,branchName, buildName, compareBranch, compareBuild);
		return createCompare(scenarioName, usecaseName,branchName, buildName, compareBranch, compareBuild);
	}

	private ScenarioComparison createCompare(String scenarioName, String usecaseName, String branchName, String buildName, String compareBranch, String compareBuild) {
		ScenarioComparison compare = new ScenarioComparison();
		compare.setScenarioName(scenarioName);
		compare.setUsecaseName(usecaseName);
		compare.setBranchName(branchName);
		compare.setBuildName(buildName);
		compare.setCompareBranch(compareBranch);
		compare.setCompareBuild(compareBuild);
		ScenarioDocuReader root = new ScenarioDocuReader(new File("/home/scenarioo/scenarioo-ip5/scenarioo-docu-generation-example/build/scenarioDocuExample"));
		List<Step> steps = root.loadSteps(branchName, buildName, usecaseName, scenarioName);
		ArrayList<File> screenshots = new ArrayList<File>();
		for(int i=0;i<steps.size();++i){
			screenshots.add(root.getScreenshotFile(branchName, buildName, usecaseName, scenarioName, steps.get(i).getStepDescription().getScreenshotFileName()));
			LOGGER.info(screenshots.get(i).getAbsolutePath());
		}
		List<Step> steps_compare = root.loadSteps(branchName, buildName, usecaseName, scenarioName);
		ArrayList<File> screenshots_compare = new ArrayList<File>();
		for(int i=0;i<steps_compare.size();++i){
			screenshots_compare.add(root.getScreenshotFile(branchName, buildName, usecaseName, scenarioName, steps.get(i).getStepDescription().getScreenshotFileName()));
			LOGGER.info(screenshots_compare.get(i).getAbsolutePath());
		}
		CompareScreenshot compare_screenshot = new CompareScreenshot();
		

		ArrayList<PageComparison> pageList = new ArrayList<PageComparison>();
		PageComparison page = new PageComparison();
		ArrayList<StepComparison> stepListPerPage =new ArrayList<StepComparison>();
		int similarity=0;
		int similarity_prev=0;
		for(int i=0;i<screenshots.size();++i){
			StepComparison step= new StepComparison();
			for(int j=0;j<screenshots_compare.size();++j){
					LOGGER.info("Comparison: "+screenshots.get(i).getAbsolutePath()+" with: "+screenshots_compare.get(j).getAbsolutePath());
				    similarity = compare_screenshot.compare(screenshots.get(i).getAbsolutePath(), screenshots_compare.get(j).getAbsolutePath());
				    LOGGER.info("Similarity: "+similarity);
				    if(similarity>similarity_prev){
						step.setSimilarity(similarity);
						step.setStepName(root.loadStep(branchName, buildName, usecaseName, scenarioName, j).getStepDescription().getTitle());
						step.setLeftURL("http://localhost:8080/scenarioo/rest/branch/"+branchName+"/build/"+buildName+"/usecase/"+usecaseName+"/scenario/"+scenarioName+"/image/"+screenshots.get(i).getName());
						step.setRighURL("http://localhost:8080/scenarioo/rest/branch/"+compareBranch+"/build/"+compareBuild+"/usecase/"+usecaseName+"/scenario/"+scenarioName+"/image/"+screenshots_compare.get(j).getName());
						
					}
					similarity_prev=similarity;
					
			}
			stepListPerPage.add(step);
			String pageName = root.loadStep(branchName, buildName, usecaseName, scenarioName, i).getPage().getName();
			page.setPageName(pageName);	

		}
		page.setSteplist(stepListPerPage);
		pageList.add(page);
		
		
		
		compare.setPagelist(pageList);
		return compare;
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
