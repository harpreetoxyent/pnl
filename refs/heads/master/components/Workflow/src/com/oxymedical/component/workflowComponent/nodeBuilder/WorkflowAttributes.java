/**
 * 
 */
package com.oxymedical.component.workflowComponent.nodeBuilder;

/**
 * @author vka
 *
 */
public class WorkflowAttributes implements IWorkflowAttributes 
{
	String id = null;

	
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
	
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		else if (obj == null) return false;
		else if (this.getClass() != obj.getClass()) return false;
		
		WorkflowAttributes wns = (WorkflowAttributes)obj;
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
