/**
 * 
 */
package com.oxymedical.component.workflowComponent.nodeBuilder;


/**
 * @author vka
 *
 */
public class EventAttributes implements IEventAttributes 
{
	private String onNode =null;
	private String onExit = null;
	private String onEnter = null;
	/**
	 * @return the onNode
	 */
	public String getOnNode()
	{
		return onNode;
	}
	/**
	 * @param onNode the onNode to set
	 */
	public void setOnNode(String onNode)
	{
		this.onNode = onNode;
	}
	/**
	 * @return the onExit
	 */
	public String getOnExit()
	{
		return onExit;
	}
	/**
	 * @param onExit the onExit to set
	 */
	public void setOnExit(String onExit)
	{
		this.onExit = onExit;
	}
	/**
	 * @return the onEnter
	 */
	public String getOnEnter()
	{
		return onEnter;
	}
	/**
	 * @param onEnter the onEnter to set
	 */
	public void setOnEnter(String onEnter)
	{
		this.onEnter = onEnter;
	}
	
	
}
