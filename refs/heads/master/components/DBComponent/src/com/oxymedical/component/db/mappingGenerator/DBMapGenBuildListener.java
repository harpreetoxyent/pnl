/**
 * 
 */
package com.oxymedical.component.db.mappingGenerator;

import org.apache.tools.ant.BuildEvent;
import org.apache.tools.ant.BuildListener;

import com.oxymedical.component.db.DBComponent;

/**
 * @author hs
 *
 */
public class DBMapGenBuildListener implements BuildListener
{

	/* (non-Javadoc)
	 * @see org.apache.tools.ant.BuildListener#buildStarted(org.apache.tools.ant.BuildEvent)
	 */
	public void buildStarted(BuildEvent buildEvent)
	{
		DBComponent.logger.log(0, "[DB Build started]");
	}

	/* (non-Javadoc)
	 * @see org.apache.tools.ant.BuildListener#buildFinished(org.apache.tools.ant.BuildEvent)
	 */
	public void buildFinished(BuildEvent buildEvent)
	{
		DBComponent.logger.log(0, new StringBuffer("[DB Build started]; Exception: ").append(buildEvent.getException().getMessage()).toString());
	}

	/* (non-Javadoc)
	 * @see org.apache.tools.ant.BuildListener#messageLogged(org.apache.tools.ant.BuildEvent)
	 */
	public void messageLogged(BuildEvent buildEvent)
	{
		DBComponent.logger.log(0, new StringBuffer("      [DB Build message logged]; Message: ").append(buildEvent.getMessage()).toString());
	}

	/* (non-Javadoc)
	 * @see org.apache.tools.ant.BuildListener#targetFinished(org.apache.tools.ant.BuildEvent)
	 */
	public void targetFinished(BuildEvent buildEvent)
	{
		DBComponent.logger.log(0, new StringBuffer("  [DB Build Target Finished]; Target: ").append(buildEvent.getTarget().getName()).toString());
	}

	/* (non-Javadoc)
	 * @see org.apache.tools.ant.BuildListener#targetStarted(org.apache.tools.ant.BuildEvent)
	 */
	public void targetStarted(BuildEvent buildEvent)
	{
		DBComponent.logger.log(0, new StringBuffer("  [DB Build Target Started]; Target: ").append(buildEvent.getTarget().getName()).toString());
	}

	/* (non-Javadoc)
	 * @see org.apache.tools.ant.BuildListener#taskFinished(org.apache.tools.ant.BuildEvent)
	 */
	public void taskFinished(BuildEvent buildEvent)
	{
		DBComponent.logger.log(0, new StringBuffer("    [DB Build Task Finished]; Task: ").append(buildEvent.getTask().getTaskName()).toString());
	}

	/* (non-Javadoc)
	 * @see org.apache.tools.ant.BuildListener#taskStarted(org.apache.tools.ant.BuildEvent)
	 */
	public void taskStarted(BuildEvent buildEvent)
	{
		DBComponent.logger.log(0, new StringBuffer("    [DB Build Task Started]; Task: ").append(buildEvent.getTask().getTaskName()).toString());
	}

}
