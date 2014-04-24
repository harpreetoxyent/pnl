/**
 * 
 */
package com.oxymedical.component.workflowComponent.exception;

/**
 * @author vka
 *
 */
public class WorkflowComponentException extends Exception {
	
private static final long serialVersionUID = 1L;
	

	public WorkflowComponentException()
	{
		super();
	}
	public WorkflowComponentException(String message)
	{
		super(message);
	}
	public WorkflowComponentException(String message,Throwable cause)
	{
		super(message,cause);
	}
	public WorkflowComponentException(Throwable cause)
	{
		super(cause);
	}

}
