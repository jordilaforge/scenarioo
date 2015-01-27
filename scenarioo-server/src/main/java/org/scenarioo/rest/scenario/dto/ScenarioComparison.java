package org.scenarioo.rest.scenario.dto;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ScenarioComparison {
	private ArrayList<PageComparison> pageList;
	
	

	
	public ArrayList<PageComparison> getPagelist() {
		return pageList;
	}
	public void setPagelist(ArrayList<PageComparison> pagelist) {
		this.pageList = pagelist;
	}
	
}
