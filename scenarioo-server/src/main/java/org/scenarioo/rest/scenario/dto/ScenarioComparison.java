package org.scenarioo.rest.scenario.dto;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ScenarioComparison {
	private String scenarioName;
	private String usecaseName; 
	private String branchName; 
	private String buildName;
	private String compareBranch; 
	private String compareBuild;
	private ArrayList<PageComparison> pageList;
	
	
	public String getScenarioName() {
		return scenarioName;
	}
	public void setScenarioName(String scenarioName) {
		this.scenarioName = scenarioName;
	}
	public String getUsecaseName() {
		return usecaseName;
	}
	public void setUsecaseName(String usecaseName) {
		this.usecaseName = usecaseName;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getBuildName() {
		return buildName;
	}
	public void setBuildName(String buildName) {
		this.buildName = buildName;
	}
	public String getCompareBranch() {
		return compareBranch;
	}
	public void setCompareBranch(String compareBranch) {
		this.compareBranch = compareBranch;
	}
	public String getCompareBuild() {
		return compareBuild;
	}
	public void setCompareBuild(String compareBuild) {
		this.compareBuild = compareBuild;
	}
	
	public ArrayList<PageComparison> getPagelist() {
		return pageList;
	}
	public void setPagelist(ArrayList<PageComparison> pagelist) {
		this.pageList = pagelist;
	}
	
}
