package org.scenarioo.rest.scenario.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class StepComparison {
	private String stepName;
	private int similarity;
	private String leftURL;
	private String righURL;
	public String getStepName() {
		return stepName;
	}
	public void setStepName(String stepName) {
		this.stepName = stepName;
	}
	public int getSimilarity() {
		return similarity;
	}
	public void setSimilarity(int similarity) {
		this.similarity = similarity;
	}
	public String getLeftURL() {
		return leftURL;
	}
	public void setLeftURL(String leftURL) {
		this.leftURL = leftURL;
	}
	public String getRighURL() {
		return righURL;
	}
	public void setRighURL(String righURL) {
		this.righURL = righURL;
	}
	
}
