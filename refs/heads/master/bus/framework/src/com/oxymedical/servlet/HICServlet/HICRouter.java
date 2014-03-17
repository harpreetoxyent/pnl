package com.oxymedical.servlet.HICServlet;

import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.router.IRouter;
import com.oxymedical.hic.application.JAAPRuntime;
import com.oxymedical.hic.application.NOLISRuntime;
import com.oxymedical.hic.application.eventmanagement.PublicationScope;

public class HICRouter implements IRouter {

	public void init() {
	}

	public IHICData routeToComponent(IHICData hicData) 
	{
		IHICData returnDO = null;

		try 
		{
			if (hicData == null) throw new Exception(" hicData is null ");

			Object[] objectArray = { hicData };
			returnDO = hicData;
// COMMENTED BY RAVNEET ON 17TH MARCH 2014 FOR PNL DEMO			
//			returnDO = NOLISRuntime.FireEvent("GetUserDataObject", objectArray,
//					PublicationScope.Global);
//			
//			if (returnDO != null) 
//			{
//				hicData.setUniqueID(returnDO.getUniqueID());
//				hicData.getData().setWorkflowPattern(returnDO.getData().getWorkflowPattern());
//			}
//			
//			if (hicData.getData().getMethodName().equalsIgnoreCase("authenticateUserEx")) 
//			{
//				objectArray[0] = hicData;
//				IHICData workflowOutput = NOLISRuntime.FireEvent("InvokeWorkflow", objectArray,
//						PublicationScope.Global);
//				
//				if (workflowOutput != null)
//					hicData.setUniqueID(workflowOutput.getUniqueID());
//			}
			
			returnDO = hicData;

			/*
			 * If returnDO does not contain userId; assume it to be an
			 * invalidated user; move to Default screen since user is not
			 * authenticated.
			 */
			if (!"authenticateUserEx".equalsIgnoreCase(hicData.getData().getMethodName())) 
			{
				/*
				 * Assume that if returnDO is completely null then there is some
				 * other problem. We check only for userId in dataObject. If
				 * that is null, then user is not authenticated; take him to
				 * default screen.
				 */
				/*if ((returnDO != null) 
						&& (returnDO.getData() != null) 
						&& (returnDO.getData().getUserId() == null)) 
				{
					System.out.println("[returnDO == null]");
					objectArray[0] = returnDO;
					System.out.println("[Going to open OpenDefaultScreen]");
					NOLISRuntime.FireEvent("OpenDefaultScreen", objectArray, PublicationScope.Global);
					return returnDO;
				}*/
			}	
			
			System.out.println("-----Inside HIC Router-----before calling the component method");
			/*
			 * Send Notification to everyone who wants to know about new HICDATA
			 * in system
			 */
			if (hicData.getData().getInvokeComponentId() != null) 
			{
				if (NOLISRuntime.getComponent(hicData.getData().getInvokeComponentId()) != null) 
				{
					objectArray[0] = hicData;
					returnDO = NOLISRuntime.FireEvent(hicData.getData()
							.getMethodName(), objectArray,
							PublicationScope.Global);
				}
				else
				{
					// TODO Need to implement for external components
				}
			}
			// COMMENTED BY RAVNEET ON 17TH MARCH 2014 FOR PNL DEMO					
//			objectArray[0] = returnDO;
//			returnDO = NOLISRuntime.FireEvent("InvokeWorkflow", objectArray,
//					PublicationScope.Global);

			// If userId is null; move to Default screen since user is not authenticated
			/*if (!"authenticateUserEx".equalsIgnoreCase(hicData.getData().getMethodName())) 
			{
				if ((returnDO != null) 
						&& (returnDO.getData() != null) 
						&& (returnDO.getData().getUserId() == null)) 
				{
					System.out.println("[returnDO == null]");
					objectArray[0] = returnDO;
					System.out.println("[Going to open OpenDefaultScreen]");
					NOLISRuntime.FireEvent("OpenDefaultScreen", objectArray, PublicationScope.Global);
				}
			}*/
			
			return returnDO;
			
		}
		catch (Exception e)
		{
			System.out.println("[HICRouter][routeToComponent][Exception]");
			e.printStackTrace();
			/*if (!"authenticateUserEx".equalsIgnoreCase(hicData.getData().getMethodName())) 
			{
				if ((returnDO != null) 
						&& (returnDO.getData() != null) 
						&& (returnDO.getData().getUserId() == null)) 
				{
					System.out.println("[returnDO == null]");
					Object[] objectArray = { returnDO };
					System.out.println("[Going to open OpenDefaultScreen]");
					try
					{
						NOLISRuntime.FireEvent("OpenDefaultScreen", objectArray, PublicationScope.Global);
					}
					catch (Exception e1)
					{
						System.out.println("[HICRouter][routeToComponent][Inside second exception]");
						e1.printStackTrace();
					}
				}
			}*/
		}
		return returnDO;
	}

	public IHICData routeToComponentEx(IHICData hicData, String methodName) {
		IHICData returnDO = null;

		try {
			Object[] objectArray = { hicData };
			objectArray[0] = hicData;
			returnDO = NOLISRuntime.FireEvent(methodName, objectArray,
					PublicationScope.Global);
			return returnDO;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnDO;
	}

	public IHICData getHICData() {
		IHICData hicData = (IHICData) NOLISRuntime.getHICData();
		return hicData;
	}

}
