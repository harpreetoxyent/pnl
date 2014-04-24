package com.oxymedical.component.workflowComponent.util.db;

public class NodeInfo {
	int id;
	String formpattern=null;
	String datapattern=null;

	String nodeName=null;
	String userPatternId=null;
	String scenario=null;

	String info=null;
	String data=null;
	int nodeDetailId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFormpattern() {
		return formpattern;
	}
	public void setFormpattern(String formpattern) {
		this.formpattern = formpattern;
	}
	public String getDatapattern() {
		return datapattern;
	}
	public void setDatapattern(String datapattern) {
		this.datapattern = datapattern;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	
	public String getUserPatternId() {
		return userPatternId;
	}
	public void setUserPatternId(String userPatternId) {
		this.userPatternId = userPatternId;
	}
	public String getScenario() {
		return scenario;
	}
	public void setScenario(String scenario) {
		this.scenario = scenario;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public int getNodeDetailId() {
		return nodeDetailId;
	}
	public void setNodeDetailId(int nodeDetailId) {
		this.nodeDetailId = nodeDetailId;
	}  
}
