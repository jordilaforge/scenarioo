package org.scenarioo.rest.scenario.dto;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PageComparison{
	private String pageName;
	private ArrayList<StepComparison> stepList;

	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	public ArrayList<StepComparison> getSteplist() {
		return stepList;
	}

	public void setSteplist(ArrayList<StepComparison> steplist) {
		this.stepList = steplist;
	}
}
