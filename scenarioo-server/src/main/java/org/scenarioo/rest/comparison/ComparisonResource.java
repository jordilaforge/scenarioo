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
import org.scenarioo.dao.aggregates.AggregatedDataReader;
import org.scenarioo.dao.aggregates.ScenarioDocuAggregationDAO;
import org.scenarioo.model.docu.aggregates.scenarios.ScenarioPageSteps;
import org.scenarioo.model.docu.entities.Step;
import org.scenarioo.model.docu.entities.StepDescription;
import org.scenarioo.repository.ConfigurationRepository;
import org.scenarioo.repository.RepositoryLocator;
import org.scenarioo.rest.base.BuildIdentifier;
import org.scenarioo.rest.base.ScenarioIdentifier;
import org.scenarioo.rest.scenario.dto.PageComparison;
import org.scenarioo.rest.scenario.dto.ScenarioComparison;
import org.scenarioo.rest.scenario.dto.StepComparison;
import org.scenarioo.rest.step.StepResource;
	
@Path("/rest/branch/{branchName}/build/{buildName}/usecase/{usecaseName}/scenario/")
public class ComparisonResource {

	private final ConfigurationRepository configurationRepository = RepositoryLocator.INSTANCE
			.getConfigurationRepository();
	
	private final AggregatedDataReader aggregatedDataReader = new ScenarioDocuAggregationDAO(
			configurationRepository.getDocumentationDataDirectory());
	
	private static final Logger LOGGER = Logger.getLogger(StepResource.class);
	
	@GET
	@Produces({ "application/xml" })
	@Path("{scenarioName}/compareBranch/{compareBranch}/compareBuild/{compareBuild}")
	public ScenarioComparison readScenarioWithPagesAndSteps(@PathParam("branchName") final String branchName,
			@PathParam("buildName") final String buildName, @PathParam("usecaseName") final String usecaseName,
			@PathParam("scenarioName") final String scenarioName, @PathParam("compareBranch") final String compareBranch,
			@PathParam("compareBuild") final String compareBuild) {
		
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
		
		ScenarioIdentifier scenarioIdentifier = new ScenarioIdentifier(new BuildIdentifier(branchName, buildName), usecaseName, scenarioName);
		ScenarioPageSteps pageSteps = aggregatedDataReader.loadScenarioPageSteps(scenarioIdentifier);
		ScenarioIdentifier scenarioIdentifierCompare = new ScenarioIdentifier(new BuildIdentifier(compareBranch, compareBuild), usecaseName, scenarioName);
		ScenarioPageSteps pageStepsCompare = aggregatedDataReader.loadScenarioPageSteps(scenarioIdentifierCompare);
		ArrayList<PageComparison> pageList = new ArrayList<PageComparison>();
		for(int i=0; i<pageSteps.getPagesAndSteps().size();++i){
				PageComparison page=compareSteps(pageSteps.getPagesAndSteps().get(i).getSteps(),pageStepsCompare.getPagesAndSteps().get(i).getSteps(),compareBranch,compareBuild,branchName, buildName, usecaseName, scenarioName);
				page.setPageName(pageSteps.getPagesAndSteps().get(i).getPage().getName());
				pageList.add(page);
			
		}
		compare.setPagelist(pageList);
		return compare;
	}
	
	private PageComparison compareSteps(List<StepDescription> steps, List<StepDescription> stepsCompare, String compareBranch, String compareBuild, String branchName, String buildName, String usecaseName, String scenarioName) {
		ScenarioDocuReader root = new ScenarioDocuReader(configurationRepository.getDocumentationDataDirectory());
		PageComparison page = new PageComparison();
		ArrayList<StepComparison> stepListPerPage =new ArrayList<StepComparison>();
		int similarity=0;
		int similarity_prev=0;
		for(int i=0;i<steps.size();++i){
			StepComparison step= new StepComparison();
			for(int j=0;j<stepsCompare.size();++j){
					String path=root.getScreenshotFile(branchName, buildName, usecaseName, scenarioName, steps.get(i).getScreenshotFileName()).getAbsolutePath();
					String pathCompare=root.getScreenshotFile(compareBranch, compareBuild, usecaseName, scenarioName, stepsCompare.get(j).getScreenshotFileName()).getAbsolutePath();
				    CompareScreenshot compare_screenshot = new CompareScreenshot();
					similarity = compare_screenshot.compare(path,pathCompare);
					LOGGER.info(similarity);
				    if(similarity>similarity_prev){
						step.setSimilarity(similarity);
						step.setStepName(steps.get(i).getTitle());
						step.setLeftURL("branch/"+branchName+"/build/"+buildName+"/usecase/"+usecaseName+"/scenario/"+scenarioName+"/image/"+steps.get(i).getScreenshotFileName());
						step.setRighURL("branch/"+compareBranch+"/build/"+compareBuild+"/usecase/"+usecaseName+"/scenario/"+scenarioName+"/image/"+stepsCompare.get(j).getScreenshotFileName());
						
					}
					similarity_prev=similarity;
					
			}
			stepListPerPage.add(step);
			
		}
		page.setSteplist(stepListPerPage);	
		return page;
	}




}
	
