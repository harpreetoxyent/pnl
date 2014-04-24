package com.oxymedical.component.workflowComponent.command.tool;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.oxymedical.component.workflowComponent.WorkflowComponent;
import com.oxymedical.component.workflowComponent.command.WorkflowToolInfo;
import com.oxymedical.component.workflowComponent.command.WorkflowToolOperation;
import com.oxymedical.component.workflowComponent.nodeInfo.NodeObject;
import com.oxymedical.component.workflowComponent.nodemanager.NodeEvents;
import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.router.IRouter;

public class NodeDOQueue /*implements Runnable*/ /*extends Thread*/
{
	public static final int MAX_CONCURRENT_DO_PER_NODE = 1;
	
	/*BlockingQueue<InvokeComponentThreadCommand> threadCommands 
			= new LinkedBlockingQueue<InvokeComponentThreadCommand>();*/
	HashMap<String, CommandFutureMapper> futureMap 
			= new HashMap<String, CommandFutureMapper>();
	
	ExecutorService es = Executors.newFixedThreadPool(MAX_CONCURRENT_DO_PER_NODE);
	
	/*boolean _queueTerminateRequested = false;
	InvokeComponentThreadCommand currCommand = null;
	Thread currThread = null;*/

	/*public NodeDOQueue()
	{
		super();
	}*/

	/*@Override
	public void run()
	{
		InvokeComponentThreadCommand command = null;
		WorkflowComponent.log(0, "Inside Run");
		try
		{
			WorkflowComponent.log(0, "Starting while loop");
			while ((command = threadCommands.take()) != null)
			{
				WorkflowComponent.log(0, "Starting Command execution");
				try 
				{
					currCommand = command;
					command.execute();
					
					currThread = new Thread(currCommand);
					currThread.start();
					currCommand = null;
				} 
				catch (NullPointerException e) 
				{
					e.printStackTrace();
				}
			}
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}*/

	public void terminate()
	{
		/*this._queueTerminateRequested = queueTerminateRequested;*/
		es.shutdown();
	}
	
	
	public void addThreadCommand(IRouter router, IHICData data, NodeObject nextNode, String function, 
			NodeObject currNode, IObserver observer, NodeEvents event)
	{
		InvokeComponentThreadCommand command 
				= new InvokeComponentThreadCommand(router, data, nextNode, function, currNode, observer, event);
		/*try
		{
			WorkflowComponent.log(0, "Putting Command into queue");
			threadCommands.put(command);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}*/
		
		WorkflowComponent.log(0, "[ADDING COMMAND TO EXECUTOR SERVICE AND GETTING FUTURE] command for DO - " + command.getHICData().getUniqueID());
		Future<InvokeComponentThreadCommand> future = es.submit(command, command);
		CommandFutureMapper mapper = new CommandFutureMapper();
		mapper.command = command;
		mapper.future = future;
		futureMap.put(data.getUniqueID(), mapper);
	}
	
	public boolean performAction(WorkflowToolOperation operation, IHICData data, NodeObject currNode, 
			String function)
	{
		boolean actionPerformed = false;
		
		WorkflowComponent.log(0, "[NodeDOQueue][getInfo][IHICData]" + data);
		WorkflowComponent.log(0, "[NodeDOQueue][getInfo][futureMap]" + futureMap);
		CommandFutureMapper mapper = futureMap.get(data.getUniqueID());
		if (mapper != null)
		{
			Future<InvokeComponentThreadCommand> future = mapper.future;
			InvokeComponentThreadCommand command = mapper.command;

			if (command != null) command.performAction(operation);
			
			// Check if operation is to be performed on current running thread
			if (operation.equals(WorkflowToolOperation.STOP_OPERATION))
			{
				if (future != null)
				{
					future.cancel(true);
					actionPerformed = future.isCancelled();
				}
			}
		}
		return actionPerformed;
	}
	
	
	public Object getInfo(WorkflowToolInfo info, IHICData data, NodeObject currNode, String function)
	{
		Object retVal = null;
		WorkflowComponent.log(0, "[NodeDOQueue][getInfo][IHICData]" + data);
		WorkflowComponent.log(0, "[NodeDOQueue][getInfo][futureMap]" + futureMap);
		CommandFutureMapper mapper = futureMap.get(data.getUniqueID());

		if (mapper != null)
		{
//			Future<InvokeComponentThreadCommand> future = mapper.future;
			InvokeComponentThreadCommand command = mapper.command;
			
			if (command != null)
			{
				WorkflowComponent.log(0, "[NodeDOQueue][getInfo][futureMap]" + futureMap);
				retVal = command.getInfo(info);
			}
		}
		return retVal;
	}
	
}
