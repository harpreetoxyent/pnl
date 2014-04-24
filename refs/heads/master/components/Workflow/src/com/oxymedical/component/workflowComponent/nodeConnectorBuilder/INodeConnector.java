/**
 * 
 */
package com.oxymedical.component.workflowComponent.nodeConnectorBuilder;

/**
 * @author vka
 *
 */
public interface INodeConnector 
{
	/**
	 * @return the fromNodeId
	 */
	public String getFromNodeId();
	/**
	 * @param fromNodeId the fromNodeId to set
	 */
	public void setFromNodeId(String fromNodeId);
	/**
	 * @return the toNodeId
	 */
	public String getToNodeId();
	/**
	 * @param toNodeId the toNodeId to set
	 */
	public void setToNodeId(String toNodeId);
}
