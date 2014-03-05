/**
 * 
 */
package com.oxymedical.component.renderer.uiBuilder.zk.library;

import java.util.Hashtable;
import java.util.Timer;
import java.util.TimerTask;

import org.zkoss.zk.ui.Session;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.impl.InputElement;

import com.oxymedical.component.renderer.command.UiLibraryCompositeCommand;
import com.oxymedical.core.router.IDataUnitRouter;
import com.oxymedical.core.router.IRouter;
import com.oxymedical.core.router.RouterClass;
import com.oxymedical.servlet.HICServlet.DataUnitRouter;


/**
 * @author vka
 *This class used for Login session timeout
 *
 */
public class LogOutCommand {
	
		public String userId;
		Timer timer=null;
		private Session session;
		 private IDataUnitRouter router;
		
	  public LogOutCommand(int seconds,String user) {
	    
		 userId = user;
	    seconds = seconds-2;
	    if(userId!=null)
	    {
	    	timer = new Timer(userId);
	    }
	    else
	    {
	    	timer = new Timer();
	    }
	    
	    timer.schedule(new SessionTimeOut(), seconds * 1000);
	  }
	  
	  public void taskCancel()
	  {
		  if(timer!=null)
		  {
		  timer.cancel();
		  }
		  
	  }
	  

	  class SessionTimeOut extends TimerTask {
		    
		  
	    public void run() {
	    	UiLibraryCompositeCommand command = new UiLibraryCompositeCommand();
	    	command.setClassname("com.oxymedical.component.useradmin.UserAdminComponent");
	    	command.setComponentId("com.oxymedical.component.useradmin");
	    	command.setMethodName("logoutUser");
	    	command.setRouter(router);
	        command.setDataPatternId(null);
	        command.setFormPatternId(null);
	        command.setFormValues(null);
	    	command.setRootFormValue(null);
	    	command.setParamList(userId);
	    	command.setSession(session);
	    	command.setComboSelectedValue(null);
	    	command.setValidListRequest(false);
	    	command.setPagingId(null); 	
	    	
	    	command.execute();
	    	/*try {
				Messagebox.show(" Your Session has been Time out, Please Refresh browser and Login Again");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}*/
	      
	      timer.cancel(); 
	    }
	  }

	  public static void main(String args[]) {
	    System.out.println("About to schedule task.");
	    Session ses=null;
	    LogOutCommand logout = new LogOutCommand(10,"testuserone");
	    IDataUnitRouter router = (IDataUnitRouter) new DataUnitRouter();
	    logout.setRouter(router);
	    logout.setSession(ses);
	    
	    //logout.taskCancel();
	    
	  }
	  
	  
		/**
		 * @return the router
		 */
		public IDataUnitRouter getRouter() {
			return router;
		}

		/**
		 * @param router the router to set
		 */
		public void setRouter(IDataUnitRouter router) {
			this.router = router;
		}

		/**
		 * @return the session
		 */
		public Session getSession() {
			return session;
		}

		/**
		 * @param session the session to set
		 */
		public void setSession(Session session) {
			this.session = session;
		}


}
