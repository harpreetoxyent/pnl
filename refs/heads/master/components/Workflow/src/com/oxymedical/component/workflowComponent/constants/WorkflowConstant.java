/**
 * 
 */
package com.oxymedical.component.workflowComponent.constants;

import com.oxymedical.core.propertyUtil.PropertyUtil;

/**
 * @author vka
 *
 */
public class WorkflowConstant {
	public static final String APPLICATION_NAME = "application";
	public static final String APPLICATION_EXTN = ".esp";
	public static final String WORKFLOW_WINDOWNAME = "name";
	public static final String WORKFLOW_WINDOWID = "id";
	public static final String WORKFLOWNODE = "Node";
	public static final String WORKFLOW_NODENAME = "name";
	public static final String WORKFLOW_NODEID = "id";
	public static final String WORKFLOW_NODEEVENT = "Events";
	public static final String WORKFLOW_NODESTATUS = "workflownode";
	public static final String WORKFLOW_FORMPATTERNS = "FormPatterns";
	public static final String WORKFLOW_DATAPATTERN = "DataPatterns";
	public static final String WORKFLOW_DATAOBJECT = "dataobject";
	public static final String WORKFLOW_DATAOBJECT_STATUS = "status";
	public static final String WORKFLOW_USERPATTERN = "UserPatterns";
	public static final String USERPATTERNID = "userpatternid";
	public static final String NAME = "name";
	public static final String ISSTART = "isstart";
	public static final String SCENARIO = "scenario";
	public static final String ID = "id";
	public static final String FORMPATTERNID = "formpatternid";
	public static final String DATAPATTERNID = "datapatternid";
	public static final String WORKFLOWNODECONNECTOR = "HICNodeConnector";
	public static final String CONNECTOR = "connector";
	public static final String FROM = "from";
	public static final String TO = "to";
	public static final String ONNODE = "OnNode";
	public static final String EXITNODE = "ExitNode";
	public static final String ENTERNODE = "EnterNode";
	public static final String WORKFLOW_FOLDER_NAME ="workflow";
	public static final String WORKFLOW_EXTN = ".xml";
	public static final String APPLICATION_ROOT_NAME = "application";
	public static final String WORKFLOWPATTERN = "WorkflowPattern";
	public static final String FORWARDSLASH = "/";
	public static final String TRUE = "True";
	
	
	public static final String SERVER_NAME = "jdbc:mysql://localhost:3306/appadmin";
	public static final String DBNAME = "appadmin";
	public static final String PACKAGE_CREATE_NAME = "com.oxymedical.component.appadmin.model";
	public static final String PACKAGE_NAME = "com.oxymedical.component.appadmin.model.resources_app";
	public static final String BASEDIR = PropertyUtil.setUpProperties("GLASSFISH_DOMAIN_HOME") + "\\lib\\ext"; 
	public static final String JAR_NAME = "resources_app";
	public static final String DATABASESETTING = "data/datasettings.xml";
	public static final String HibernatePath = "/com/oxymedical/component/appadmin/model/resources_app/hibernate.cfg.xml";
	
	public static final String NODENAME = "nodename";
	public static final String PARAMS = "params";
	public static final String FORMPATTERN_ID = "formpatternid";
	public static final String NODEDESCRIPTION = "nodeDescription";
	public static final String NODEID= "id";
	
	public static final String NODENAME_SUFFIX_FOR_INPUT = "inputscreen";
	public static final String NODE_EXECUTION_STATUS_PROGRESS = "2";
	public static final String NODE_EXECUTION_STATUS_DEFAULT = NODE_EXECUTION_STATUS_PROGRESS;
	public static final String NODE_EXECUTION_STATUS_COMPLETED = "C";
	public static final String NODE_EXECUTION_STATUS_WAITING = "1";
	public static final String NODE_EXECUTION_STATUS_ERROR = "E";
	public static final String NODE_PATTERN_NAME="[\\\\/.'_\\s:*@#\"]";
	public static final String TOOL_OUTPUT_FILE="toolOutputFile";
	
	public static final String INPUT_NODENAME="NODENAME";
	public static final String INPUT_WORKFLOWNAME="WORKFLOWNAME";
	public static final String INPUT_NODESTATUS="NODESTATUS";
	public static final String INPUT_NODEEXECUTIONSTATUS="NODEEXECUTION"; // TODO - What is this constant for?
	public static final String INPUT_UNIQUEID="UNIQUEID"; // TODO - What is this constant for?
	public static final String INPUT_TOOLCATEGORY="cateogryid";
	public static final String INPUT_TOOLCATEGORYNAME="category";
	public static final String COPY="Copy";
	public static final String INPUT_ISVISUALZIER="isVisualizer";
	public static final String INPUT_XML="inputxml";
}
