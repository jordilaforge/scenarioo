package org.scenarioo.rest.scenario.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ScenarioComparison {

	private String leftBranchName;
	private String rightBranchName;
	
	public String getLeftBranchName() {
		return leftBranchName;
	}
	public void setLeftBranchName(String leftBranchName) {
		this.leftBranchName = leftBranchName;
	}
	public String getRightBranchName() {
		return rightBranchName;
	}
	public void setRightBranchName(String rightBranchName) {
		this.rightBranchName = rightBranchName;
	}
	
}
