package com.oxymedical.component.workflowComponent.command;

import com.oxymedical.component.workflowComponent.command.tool.NodeDOQueue;
import com.oxymedical.component.workflowComponent.command.tool.ToolCompletionObserver;

public class InvokeWorkflowToolCommand extends BaseWorkflowToolCommand/* implements IWorkflowToolCommand*/
{
	/*static InvokeWorkflowToolCommand thisCommand = null;*/
	
	// Private constructor for singleton implementation
	/*private InvokeWorkflowToolCommand() { }*/


	/**
	 * Singleton instance since all DataObject queues pertaining to each node
	 * are maintained in this class.
	 * 
	 * @return class singleton instance
	 */
	/*public static InvokeWorkflowToolCommand getInstance()
	{
		if (thisCommand == null) thisCommand = new InvokeWorkflowToolCommand();
		return thisCommand;
	}*/
	
	public synchronized void execute()
	{
		/*InvokeComponentThreadCommand command = new InvokeComponentThreadCommand(_router, _data, _nextNode, _function, _currNode, _observer);
		command.execute();*/
		
		if (!queues.containsKey(_currNode))
		{
			NodeDOQueue queue = new NodeDOQueue();
			/*new Thread(queue).start();*/
			queues.put(_currNode, queue);
		}
		if (queues.containsKey(_currNode))
		{
			NodeDOQueue queue = queues.get(_currNode);
			queue.addThreadCommand(_router, _data, _nextNode, _function, _currNode, _observer, _event);
		}
	}
	
	
	
	public static void main (String[] args)
	{
		InvokeWorkflowToolCommand com = new InvokeWorkflowToolCommand();
		com._currNode = com._nextNode = null;
		com._data = null; com._function = null; 
		com._observer = new ToolCompletionObserver(null); 
		com._router = null;
		com.execute();
	}

}
