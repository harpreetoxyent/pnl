/**
 * 
 */
package com.oxymedical.component.workflowComponent.nodeConnectorBuilder;

/**
 * @author vka
 *
 */
public class NodeConnector implements INodeConnector 
{
	private String fromNodeId = null;
	private String toNodeId = null;
	/**
	 * @return the fromNodeId
	 */
	public String getFromNodeId() {
		return fromNodeId;
	}
	/**
	 * @param fromNodeId the fromNodeId to set
	 */
	public void setFromNodeId(String fromNodeId) {
		this.fromNodeId = fromNodeId;
	}
	
	/**
	 * @return the toNodeId
	 */
	public String getToNodeId() {
		return toNodeId;
	}
	/**
	 * @param toNodeId the toNodeId to set
	 */
	public void setToNodeId(String toNodeId) {
		this.toNodeId = toNodeId;
	}

	public boolean equals(Object obj) 
	{
		if (obj instanceof NodeConnector)
		{
			NodeConnector nc = (NodeConnector) obj;
			if ((nc.fromNodeId.equals(this.fromNodeId)) && (nc.toNodeId.equals(this.toNodeId))) return true;
		}
		return false;
	}
}
