package com.oxymedical.component.workflowComponent.erl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import com.oxymedical.component.logging.LoggingComponent;
import com.oxymedical.component.workflowComponent.WorkflowComponent;
import com.oxymedical.component.workflowComponent.nodeInfo.NodeObject;
import com.oxymedical.component.workflowComponent.nodeInfo.WorkflowNodeCollection;
import com.oxymedical.core.commonData.IApplication;
import com.oxymedical.core.propertyUtil.PropertyUtil;

public class ERLCreator implements ERLConstants
{
	static final String workflowERLPath = PropertyUtil.setUpProperties("GLASSFISH_DOMAIN_HOME") + "/config/rules/erls";
	static final String workflowERLFileName = "RSetWorkflow.erl";
	
	static LoggingComponent logger = WorkflowComponent.logger;
	
//	static final String workflowNodeSep = "QWERTYUIOP";
	List<String> ruleList=null;

	StringBuffer varList = null;
	int ruleCounter = 1;
	private String applicationName = null;

	
	public void createERL(WorkflowNodeCollection workflowCollection, IApplication application)
	{
		this.applicationName = application.getApplicationName();
		createERL(workflowCollection);
	}

	public void createERL(WorkflowNodeCollection workflowCollection)
	{
		// Iterate through workflow collection
		
		// For each node
		
			// Add a variable to the variable-list
		
			// Get Form pattern condition
		
			// Get Data pattern condition
		
			// Get User pattern condition
		
			// Get Status condition
		
			// Get data object unique id condition
		
			// Create a new test rule
			
				// Add salience attribute

				// Create if condition
		
				// Create then consequence
		//implementation changed for creating the erl as one erl is for the one workflow.
		delteERL();
		System.out.println("------Inside create ERL workflowCollection---"+workflowCollection);
		if(workflowCollection != null){
			Hashtable<String, Hashtable<String, NodeObject>> coll = workflowCollection.getNodeCollection();
			System.out.println("------Inside create ERL coll---"+coll);
	
			Set<String> workflowIds = coll.keySet();
			Iterator<String> workflowIdIterator = workflowIds.iterator();
			while (workflowIdIterator.hasNext()){
				String workflowId = (String) workflowIdIterator.next();
				Object workflowObj = workflowCollection.getNodeCollection().get(workflowId);
				createERLBasedOnWorkflowObject(workflowId,workflowObj,this.applicationName);
			}
		}
	}
	public void delteERL()
	{
		File dir = new File(workflowERLPath);
	    String[] children = dir.list();
	    if(children!=null && children.length>0)
	    {
	    	for(int i=0;i<children.length;i++)
	    	{
	    		 String filename = children[i];
	    		 File tempFile = new File(dir,filename);
	    		 tempFile.delete();
	    	}
	    	
	    }
	}
	//method added the createERL baes on workflowid and nodeobject.
	public void createERLBasedOnWorkflowObject(String workflowId,Object workflowObj,String applicationNameValue)
	{
		this.applicationName=applicationNameValue;
		ruleCounter=1;
		ruleList = new ArrayList<String>();
		varList = new StringBuffer(tokenImage[openvariables]);
		getDataObjectVarDeclaration();
		StringBuffer erl = new StringBuffer(tokenImage[openrulesetattr].concat(workflowId).concat("\"").concat(tokenImage[RA]));
		getInfoFromWorkflow(workflowObj,workflowId);
		erl.append(varList);
		erl.append(tokenImage[endvariables]);
		for (int i=0; i < ruleList.size(); i++)
		{
			erl.append(ruleList.get(i));
		}
		erl.append(tokenImage[endruleset]);
		
		logger.log(0, "[ERLCreator][createERL][erl][****************************************]\n" + erl.toString());
		
		// Write it to file
		File erlFile = new File(workflowERLPath, ((this.applicationName == null) ? workflowERLFileName :applicationName+workflowId + workflowERLFileName));
    
        // Write to file
        BufferedWriter out;
		try
		{
			out = new BufferedWriter(new FileWriter(erlFile));
			out.write(erl.toString());
			out.flush();
			out.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
}

	private void getInfoFromWorkflow(Object workflowObj,String workflowId)
	{
		
			
			logger.log(0, "[WorkflowManager][getMatchingNodesCollection][obj.getClass().getName()]".concat(workflowObj.getClass().getName()));
			Hashtable<String, NodeObject> nodeMgrHashTable = (Hashtable<String, NodeObject>)workflowObj;
			Set<String> nodeIdKeys = nodeMgrHashTable.keySet();
			Iterator<String> nodeIdIterator = nodeIdKeys.iterator();

			while (nodeIdIterator.hasNext())
			{
				String nodeId = (String) nodeIdIterator.next();
				Object nodeObj = nodeMgrHashTable.get(nodeId);
				NodeObject node = (NodeObject)nodeObj;
				updateVariableList(node, workflowId, nodeId);
				createRule(node, workflowId, nodeId);
				ruleCounter++ ;
			}
		
	}
	
	
	private void updateVariableList(NodeObject node, String workflowId, String nodeId)
	{
		varList.append(tokenImage[openvariable]);
			varList.append(tokenImage[openType]);
				varList.append("com.oxymedical.component.workflowComponent.nodeInfo.NodeObject");
			varList.append(tokenImage[endType]);
			varList.append(tokenImage[openID]);
				varList.append(workflowId.concat(workflowNodeSep).concat(nodeId));
			varList.append(tokenImage[endID]);
		varList.append(tokenImage[endvariable]);
	}
	

	private void getDataObjectVarDeclaration()
	{
		varList.append(tokenImage[openvariable]);
			varList.append(tokenImage[openType]);
				varList.append("com.oxymedical.core.commonData.HICData");
			varList.append(tokenImage[endType]);
			varList.append(tokenImage[openID]);
				varList.append("dataObject");
			varList.append(tokenImage[endID]);
		varList.append(tokenImage[endvariable]);
	}
	
	private String createRule(NodeObject node, String workflowId, String nodeId)
	{
		String condition = getCondition(node);
		if (condition.equals("")) return null;
		
		final String urlName = " \"http://www.google.com\" ";
		int ruleNameConditionCounter = 1;
		int ruleNameConsequenceCounter = 1;
		String salience = " 0 ";
		String ruleNamePrefix = "TestRule";
		String conditionPrefix = "cond";
		String consequencePrefix = "c";
		String nodeFunctionName = "fireOnNode()";
		
		StringBuffer rule = new StringBuffer("");
		
		rule.append(tokenImage[openruleattr].concat(ruleNamePrefix).concat(""+ruleCounter).concat("\"").concat(tokenImage[RA]));
			rule.append(tokenImage[opensalience]);
				rule.append(salience);
			rule.append(tokenImage[endsalience]);
			rule.append(tokenImage[openif]);
				rule.append(tokenImage[openconditionlist]);
					rule.append(tokenImage[openconditionattr].concat(conditionPrefix).concat(""+ruleNameConditionCounter).concat("\"").concat(tokenImage[RA]));
						ruleNameConditionCounter++ ;
						
						// Add condition
						rule.append(condition);
						
					rule.append(tokenImage[endcondition]);
				rule.append(tokenImage[endconditionlist]);
			rule.append(tokenImage[endif]);
			rule.append(tokenImage[openthen]);
				rule.append(tokenImage[openconsequencelist]);
					rule.append(tokenImage[openconsequenceattr].concat(consequencePrefix).concat(""+ruleNameConsequenceCounter).concat("\"").concat(tokenImage[RA]));
						ruleNameConsequenceCounter++ ;
						
						// Add consequence
						rule.append(workflowId.concat(workflowNodeSep).concat(nodeId).concat(".").concat(nodeFunctionName));
						
					rule.append(tokenImage[endconsequence]);
				rule.append(tokenImage[endconsequencelist]);
			rule.append(tokenImage[endthen]);
			rule.append(tokenImage[openurl]);
				rule.append(urlName);
			rule.append(tokenImage[endurl]);
		rule.append(tokenImage[endrule]);

		ruleList.add(rule.toString());
		return rule.toString();
	}

	
	private String getCondition(NodeObject node)
	{
		String condition = "";
		final String andCondition = " && ";
		final String orCondition = " || ";
		final String replacer = "XQXWXEXRXTXYXUXIXOXP";
		final String dpConditionStr = "(dataObject.getData().getDataPattern().getDataPatternId() == \"XQXWXEXRXTXYXUXIXOXP\")";
		final String fpConditionStr = "(dataObject.getData().getFormPattern().getFormId() == \"XQXWXEXRXTXYXUXIXOXP\")";
		final String upConditionStr = "(dataObject.getData().getUserPatternString() == \"XQXWXEXRXTXYXUXIXOXP\")";
		String statusCondition = getStatusCondition(node);
		String fpCondition = getFormPatternCondition(node);
		String dpCondition = getDataPatternCondition(node);
		String upCondition = getUserPatternCondition(node);
		String applCondition = getApplicationCondition();
		
		Pattern p = Pattern.compile(",", Pattern.LITERAL);
		String[] fpConds = {null, null, null, null, null};
		String[] dpConds = {null, null, null, null, null};
		String[] upConds = {null, null, null, null, null};
		
		if ((statusCondition != null) || (fpCondition != null) || (dpCondition != null) || (upCondition != null))
		{
			if (fpCondition != null) fpConds = p.split(fpCondition);
			if (dpCondition != null) dpConds = p.split(dpCondition);
			if (upCondition != null) upConds = p.split(upCondition);
			
			outer:
			for (int i=0; i<fpConds.length; i++)
			{
				middle:
				for (int j=0; j<dpConds.length; j++)
				{
					inner:
					for (int k=0; k<upConds.length; k++)
					{
						String currFp = fpConds[i];
						String currDp = dpConds[j];
						String currUp = upConds[k];
						
						String eachCondition = "";
						if (statusCondition != null)
						{
							eachCondition = "(".concat(statusCondition);
						}
						
						if (currFp != null)
						{
							currFp = fpConditionStr.replaceAll(replacer, currFp);
							eachCondition = (eachCondition.equals("") ? "(" : eachCondition.concat(andCondition)).concat(currFp) ;
						}
						
						if (currDp != null)
						{
							currDp = dpConditionStr.replaceAll(replacer, currDp);
							eachCondition = (eachCondition.equals("") ? "(" : eachCondition.concat(andCondition)).concat(currDp) ;
						}
						
						if (currUp != null)
						{
							currUp = upConditionStr.replaceAll(replacer, currUp);
							eachCondition = (eachCondition.equals("") ? "(" : eachCondition.concat(andCondition)).concat(currUp) ;
						}
						
						if ((applCondition != null) && (!eachCondition.equals("")))
						{
							eachCondition = eachCondition.concat(andCondition).concat(applCondition) ;
						}
						
						eachCondition = (eachCondition.equals("") ? "" : eachCondition.concat(")"));
						if (!eachCondition.equals(""))
							condition = (condition.equals("") ? "(" : condition.concat(orCondition)).concat(eachCondition) ;
						
						logger.log(0, "[eachCondition]"+eachCondition);
						logger.log(0, "[condition]"+condition);
						
						if (currUp == null) break inner;
					}
					if (dpConds[j] == null) break middle;
				}
				if (fpConds[i] == null) break outer;
			}
		}
		condition = (condition.equals("") ? "" : condition.concat(")")) ;
		return condition;
	}
	
	private String getStatusCondition(NodeObject node)
	{
		if ((node != null && node.getDataObject() != null && node.getDataObject().getStatus() != null && !node.getDataObject().getStatus().trim().equals("")))
			return "(dataObject.getData().getStatus() == \"" + node.getDataObject().getStatus() + "\")";
		
		return null;
	}
	
	
	private String getFormPatternCondition(NodeObject node)
	{
		/*String fpId = node.getFormPatternString();
		if ((node != null) && (fpId != null))
			return "(dataObject.getData().getFormPattern().getFormId() == \"" + fpId + "\")";
		
		return null;*/
		return node.getFormPatternString();
	}
	
	
	private String getDataPatternCondition(NodeObject node)
	{
		/*String dpId = node.getDataPatternString();
		if ((node != null) && (dpId != null))
			return "(dataObject.getData().getDataPattern().getDataPatternId() == \"" + dpId + "\")";
		
		return null;*/
		return node.getDataPatternString();
	}
	
	private String getUserPatternCondition(NodeObject node)
	{
		/*String upId = node.getUserPatternString();
		if ((node != null) && (upId != null))
			return "(dataObject.getData().getUserPatternString() == \"" + upId + "\")";
		
		return null;*/
		return node.getUserPatternString();
	}
	
	private String getApplicationCondition()
	{
		if (this.applicationName != null)
			return "(dataObject.getApplication().getApplicationName() == \"" + this.applicationName + "\")";
		
		return null;
	}

	public void setApplication(IApplication application)
	{
		this.applicationName = application.getApplicationName();
	}
}
