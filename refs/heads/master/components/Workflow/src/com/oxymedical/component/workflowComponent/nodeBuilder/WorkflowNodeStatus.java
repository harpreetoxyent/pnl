/**
 * 
 */
package com.oxymedical.component.workflowComponent.nodeBuilder;

/**
 * @author vka
 *
 */
public class WorkflowNodeStatus implements IWorkflowNodeStatus {
	String id = null;

	// These two are not used. Should be removed.
	private String isStart = null;
	String scenario = null;
	
	/**
	 * @return the Workflow id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the Workflow id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the isStart
	 */
	// Not used. Should be removed.
	public String getIsStart() {
		return isStart;
	}
	/**
	 * @param isStart the isStart to set
	 */
	// Not used. Should be removed.
	public void setIsStart(String isStart) {
		this.isStart = isStart;
	}
	/**
	 * @return the scenario
	 */
	// Not used. Should be removed.
	public String getScenario() {
		return scenario;
	}
	/**
	 * @param scenario the scenario to set
	 */
	// Not used. Should be removed.
	public void setScenario(String scenario) {
		this.scenario = scenario;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		else if (obj == null) return false;
		else if (this.getClass() != obj.getClass()) return false;
		
		WorkflowNodeStatus wns = (WorkflowNodeStatus)obj;
		return (id == wns.id || (id != null && id.equals(wns.id)));
	}
	
	@Override
	public int hashCode()
	{
		int hash = 7;
		hash = 31 * hash + (null == id ? 0 : id.hashCode());
		return hash;
	}

}
