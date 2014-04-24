package com.oxymedical.component.workflowComponent.util.db;

public class NodeDetails {

	int id;
	String action;
	String formPattern;
	String pacakge;
	String deleted="0";
	String nodeDescription=null; 
	int toolCategoryId;
	Boolean isVisualizer=false;
    String inputxml=null; 
	
	
	public int getToolCategoryId() {
		return toolCategoryId;
	}
	public void setToolCategoryId(int toolCategoryId) {
		this.toolCategoryId = toolCategoryId;
	}
	public String getNodeDescription() {
		return nodeDescription;
	}
	public void setNodeDescription(String nodeDescription) {
		this.nodeDescription = nodeDescription;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getFormPattern() {
		return formPattern;
	}
	public void setFormPattern(String formPattern) {
		this.formPattern = formPattern;
	}
	public String getPacakge() {
		return pacakge;
	}
	public void setPacakge(String pacakge) {
		this.pacakge = pacakge;
	}
	public String getDeleted() {
		return deleted;
	}
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
	public Boolean getIsVisulaizer() {
		return isVisualizer;
	}
	public void setIsVisulaizer(Boolean isVisualizer) {
		this.isVisualizer = isVisualizer;
	}
	public String getInputxml() {
		return inputxml;
	}
	public void setInputxml(String inputxml) {
		this.inputxml = inputxml;
	}
}
