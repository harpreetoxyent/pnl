package com.oxymedical.component.workflowComponent.constants;


public class WorkflowSqlCommands {
	public static final String CONST_USERID = "userid";
	public static final String CONST_UNIQUEID = "uniqueid";
	public static final String CONST_NODEEXECUTIONSTATUS = "nodeexecutionstatus";
	public static final String CONST_DATAOBJECT_ID = "dataobjectid";
	public static final String CONST_DATAPATTERN = "datapattern";
	public static final String CONST_STATUS = "status";
	public static final String CONST_FORMPATTERN = "formpattern";
	public static final String CONST_USERPATTERNS = "userpatterns";
	public static final String CONST_WORKFLOWNODEINFO = "workflownodeinfo";
	public static final String CONST_WORKFLOWNODE_WFID = "workflowinfo";
	public static final String CONST_WORKFLOWNODE_NODEID = "nodeinfo";
	public static final String CONST_DOMETADATA_DOID = "dataobject";
	public static final String CONST_DOMETADATA_KEY = "datakey";
	public static final String CONST_DOMETADATA_VALUE = "datavalue";
	public static final String eventtype = "eventtype";
	public static final String nodeName = "nodename";
	public static final String workflowid = "workflowid";
	public static final String workflowname = "workflowname";
	public static final String nodeid = "nodeid";
	public static final String workflownodeinfoid = "workflownodeinfoid";
	public static final String toolcategoryid="toolcategoryid";
	
	public static final String DATAOBJECT_USER_CONDITION = " dataobject.userid = :" + CONST_USERID;
	public static final String DATAOBJECT_FORMPATTERN_CONDITION = " dataobject.formpattern = :" + CONST_FORMPATTERN;
	public static final String DATAOBJECT_DATAPATTERN_CONDITION = " dataobject.datapattern = :" + CONST_DATAPATTERN;
	public static final String DATAOBJECT_USERPATTERN_CONDITION = " dataobject.userpatterns = :" + CONST_USERPATTERNS;
	public static final String DATAOBJECT_UNIQUEID_CONDITION = " dataobject.uniqueid = :" + CONST_UNIQUEID;
	public static final String DATAOBJECT_UNIQUEID_LIKE_CONDITION = " dataobject.uniqueid like :" + CONST_UNIQUEID;
	public static final String DATAOBJECT_STATUS_CONDITION = " dataobject.status = :" + CONST_STATUS;
	public static final String DATAOBJECT_NODEEXECUTIONSTATUS_CONDITION = " dataobject.nodeexecutionstatus = :" + CONST_NODEEXECUTIONSTATUS;
	public static final String DATAOBJECT_ID_CONDITION = " dataobject.id = :" + CONST_DATAOBJECT_ID;
	public static final String WORKFLOWINFO_NAME_CONDITION = " workflowinfo.name = :" + workflowname;
	public static final String NODEINFO_NODENAME_CONDITION = " nodeinfo.nodename = :" + nodeName;
	public static final String DATAOBJECTMETADATA_DATAKEY_CONDITION = " dataobjectmetadata.datakey = :" + CONST_DOMETADATA_KEY;
	public static final String DATAOBJECTMETADATA_DATAVALUE_CONDITION = " dataobjectmetadata.datavalue = :" + CONST_DOMETADATA_VALUE; 
	public static final String DATAOBJECTMETADATA_DATAOBJECT_ID_CONDITION = " dataobjectmetadata.dataobject.id = :" + CONST_DOMETADATA_DOID; 
	public static final String WORKFLOWNODEINFO_OBJECT_CONDITION = " workflownodeinfo = :" + CONST_WORKFLOWNODEINFO;
	public static final String CONDITION_JOINER_AND = " and "; 
	public static final String CONDITION_JOINER_OR = " or "; 
	public static final String CONDITION_JOINER_WHERE = " where "; 
	
	public static final String Find_EventType_From_Event = "from Eventtype as eventtype"
		+ " where eventtype.eventtype = :eventtype";
	public static final String Find_Node_Based_On_NodeName = "from Nodeinfo as nodeinfo"
		+ " where nodeinfo.nodename = :nodename";
	public static final String Find_WorkFlow_Based_On_Id = "from Workflowinfo as workflowinfo"
		+ " where workflowinfo.id = :workflowid";
	public static final String Find_WorkFlow_Based_On_Name = "from Workflowinfo as workflowinfo"
		+ " where workflowinfo.name = :workflowname";
	public static final String Find_Node_Based_On_NoId = "from Nodeinfo as nodeinfo"
		+ " where nodeinfo.id = :nodeid";	
	public static final String find_All_workflow = "from Workflowinfo as workflowinfo"
		+ " where workflowinfo.deleted ='0'";
	public static final String find_All_dataobject = "from Dataobject as dataobject";
	public static final String find_WorkFlowNodesInfo_Based_On_WorkFlowId ="from Workflownodeinfo workflowinfo left join workflowinfo.nodeinfo"
		+" where workflowinfo.workflowinfo.id= :workflowid";
	public static final String find_WorkFlowNodesInfo_Based_On_WorkFlowName =
		"select wni.status, wni.nodeinfo.formpatternid, wni.nodeinfo.datapatternid, wni.nodeinfo.userpatternid, wni.nodeinfo.nodename"
		+ " from Workflownodeinfo wni"
		+ " left join wni.nodeinfo"
		+ " left join wni.workflowinfo"
		+ " where wni.workflowinfo.name = :workflowname";
		
		/*"from Workflownodeinfo wni" 
		+ " left join wni.nodeinfo"
		+ " where wni.workflowinfo.name = :workflowname";*/

	public static final String FIND_WORKFLOWNODES_WITH_ACTION_AND_PACKAGE =
		"select wi.name, nd.action, nd.pacakge, wni.nodeinfo.nodename"
		+ " from Workflownodeinfo wni"
		+ " left join wni.nodeinfo.nodedetail nd"
		+ " left join wni.workflowinfo wi"
		+ " where wi.deleted = '0' and wni.nodeinfo.formpatternid = null and nd.action != null and nd.pacakge!= null";

	public static final String Find_Nodeevents_Based_On_NodeId = "from Nodeevent as nodeevent"
		+ " where nodeevent.workflownodeinfo.id = :workflownodeinfoid";
	public static final String Find_NodeConnector_Based_On_WorkflowId = "from Nodeconnector as nodeconnector"
		+ " where nodeconnector.workflowinfo.id = :workflowid";
	public static final String Find_Workflownodeinfo_Based_On_WorkflowID = "from Workflownodeinfo as workflownodeinfo"
		+ " where workflownodeinfo.workflowinfo.id = :workflowid";
	public static final String Delete_Nodeconnector_Based_On_WorkflowID = "from Nodeconnector as nodeconnector"
		+ " where workflownodeinfo.workflowinfo.id = :workflowid";
	
	public static final String Delete_NodeInfo_Based_On_Id = "delete from Nodeinfo as " +
			"nodeinfo where nodeinfo.id = :nodeid"; 
	public static final String Find_DataObjectInQueue = "from Dataobject as dataobject"
		+ " where dataobject.userid = :userid"
		+ " and dataobject.uniqueid = :uniqueid"
		+ " and dataobject.nodeexecutionstatus = :nodeexecutionstatus";
	public static final String Find_AllDataObjectInQueue = "from Dataobject as dataobject"
		+ " where dataobject.userid = :userid"
		+ " and dataobject.uniqueid = :uniqueid";
	public static final String Find_AllDataObjectInQueueEx = "from Dataobject as dataobject"
		+ " where dataobject.userid = :userid"
		+ " and dataobject.uniqueid = :uniqueid"
		/*+ " and dataobject.datapattern = :datapattern"*/
		+ " and dataobject.status = :status"
		/*+ " and dataobject.userpatterns = :userpatterns"*/
		/*+ " and dataobject.formpattern = :formpattern"*/;
	public static final String Find_AllDataObjectInQueueEx_DataPattern = " and dataobject.datapattern = :datapattern";
	public static final String Find_AllDataObjectInQueueEx_FormPattern = " and dataobject.formpattern = :formpattern";
	public static final String Find_AllDataObjectInQueueEx_UserPattern = " and dataobject.userpatterns = :userpatterns";
	public static final String Find_AllDataObjectInQueueEx_NodeExecutionStatus = " and dataobject.nodeexecutionstatus = :nodeexecutionstatus";
	
	public static final String Find_DataObjectMetaData = "from Dataobjectmetadata as dataobjectmetadata"
		+ " where dataobjectmetadata.dataobject = :dataobject"
		+ " and dataobjectmetadata.datakey = :datakey"
		/*+ CONDITION_JOINER_AND + DATAOBJECTMETADATA_DATAVALUE_CONDITION*/;// " and dataobjectmetadata.datavalue = :datavalue";
	
	public static final String UPDATE_DATAOBJECTINQUEUE = "update Dataobject"
			+ " set nodeexecutionstatus = :nodeexecutionstatus" 
			+ " where uniqueid = :uniqueid"
			+ " and workflownodeinfo = :workflownodeinfo";

	public static final String FIND_WORKFLOWNODE_BASED_ON_ID = "from Workflownodeinfo as workflownodeinfo"
		+ " where workflownodeinfo.workflowinfo = :workflowinfo"
		+ " and workflownodeinfo.nodeinfo = :nodeinfo";

	public static final String FIND_WORKFLOWNODE_BASED_ON_NAME = "from Workflownodeinfo as wni"
		+ " left join wni.workflowinfo as wi"
		+ " left join wni.nodeinfo as ni"
		+ " where wi.name=:workflowname and ni.nodename=:nodename";

	public static final String FIND_NODEDETAIL_BASED_ON_NODEID = "from Nodedetail as nodedetail"
		+ " where nodedetail.nodeinfo.id = :nodeid";
		
	public static final String DELETE_WORKFLOW_BASED_ON_ID = "update Workflowinfo"
		+ " set deleted=1 where id= :workflowid";
	
	public static final String UPDATE_WORKFLOW_NAME = "update Workflowinfo"
		+ " set displayname= :workflowname where id= :workflowid";
	public static final String nodedetailid = "nodedetailid";
	
	public static final String FIND_NODEDETAIL_BASED_ON_NODEDETIALID = "from Nodedetail as nodedetail"
		+ " where nodedetail.id = :nodedetailid";
	
	public static final String FIND_NODES_BASED_NODEDTAILID = "from Nodeinfo as nodeinfo"
		+ " where nodeinfo.nodedetail.id = :nodedetailid";
	
	
	public static final String FIND_MATCHING_DATAOBJECT 
	= "select distinct dataobject, workflowinfo, nodeinfo "
		+ " from Dataobject as dataobject"
		+ " left join dataobject.workflownodeinfo.workflowinfo workflowinfo"
		+ " left join dataobject.workflownodeinfo.nodeinfo nodeinfo"
		+ " left join dataobject.dataobjectmetadatas dataobjectmetadata";
		
	
	public static final String FIND_MATCHING_DATAOBJECT_FOR_USER 
		= FIND_MATCHING_DATAOBJECT
			+ " where" + DATAOBJECT_USER_CONDITION;
	
	public static final String FIND_DATAOBJECT_BASEDON_UNIQUEID 
		= FIND_MATCHING_DATAOBJECT_FOR_USER
			+ CONDITION_JOINER_AND + DATAOBJECT_UNIQUEID_CONDITION 
			+ CONDITION_JOINER_AND + WORKFLOWINFO_NAME_CONDITION
			+ CONDITION_JOINER_AND + NODEINFO_NODENAME_CONDITION;
	/*= "from Dataobject"
		+ " where uniqueid = :uniqueid"
		+ " and workflownodeinfo = :workflownodeinfo";*/
	
	public static final String FIND_ALL_METADATA_FOR_DATAOBJECT 
		= "from Dataobjectmetadata dataobjectmetadata "
			+ " where " + DATAOBJECTMETADATA_DATAOBJECT_ID_CONDITION;
	
	public static final String FIND_ALL_METADATA_FOR_PATIENTID 
		= "from Dataobjectmetadata dataobjectmetadata "
			+ " where " + DATAOBJECTMETADATA_DATAKEY_CONDITION 
			+ CONDITION_JOINER_AND + DATAOBJECTMETADATA_DATAVALUE_CONDITION;
	
	public static final String FIND_MATCHING_DATAOBJECT_FOR_ID 
		= "select distinct dataobject, workflowinfo, nodeinfo "
			+ " from Dataobject as dataobject"
			+ " left join dataobject.workflownodeinfo.workflowinfo workflowinfo"
			+ " left join dataobject.workflownodeinfo.nodeinfo nodeinfo"
			+ " left join dataobject.dataobjectmetadatas dataobjectmetadata"
			+ " where" + DATAOBJECT_ID_CONDITION;

	public static final String FIND_ERROR_BASED_ON_DATAOBJECTID = "from Errorinfo as errorinfo"
		+ " where errorinfo.dataobject.id = :dataobjectid";
	public static final String DATAOBJECTID="dataobjectid";	

	
	public static final String FIND_DATAOBJECT_STATUS = "from Dataobjectmetadata as dataobjectmetadata,Workflownodeinfo as workflownodeinfo,Nodeinfo as nodeinfo "
			+ "left outer join nodeinfo.nodedetail as nodedetails left outer join workflownodeinfo.workflowinfo as workflowinfo "
			+ "left outer join dataobjectmetadata.dataobject as dataobject where dataobject.workflownodeinfo =workflownodeinfo.id "
			+ "and workflownodeinfo.nodeinfo =nodeinfo.id and	dataobjectmetadata.datakey ='SCHEDULEWORKAREA' and "
			+ "dataobject.userid =:"
			+ CONST_USERID
			+ " and dataobject.nodeexecutionstatus like :"+CONST_NODEEXECUTIONSTATUS;
			
	public static final String FIND_DATAOBJECT_STATUS_CONDITIONS = "from Dataobjectmetadata as dataobjectmetadata,Workflownodeinfo as "
			+ "workflownodeinfo,Nodeinfo as nodeinfo left outer join nodeinfo.nodedetail as nodedetails left outer join workflownodeinfo.workflowinfo as "
			+ "workflowinfo left outer join dataobjectmetadata.dataobject as dataobject where dataobject.workflownodeinfo = workflownodeinfo.id and "
			+ "workflownodeinfo.nodeinfo = nodeinfo.id  and dataobject.userid =:"
			+ CONST_USERID
			+ " and dataobject.nodeexecutionstatus like :"+CONST_NODEEXECUTIONSTATUS
			+" and dataobject.uniqueid like :"+CONST_UNIQUEID+" and dataobjectmetadata.datakey ='SCHEDULEWORKAREA'";
	
	public static final String Find_TOOL_BASED_ON_TOOLID="from Toolcateogry as toolcateogry where" +
			" toolcateogry.id=:toolcategoryid";
	
	public static final String Find_NODEDETAILS_BASED_ON_WORKFLOWNAME="from Workflownodeinfo as workflownodeinfo,Nodedetail as nodedetails," +
			"Nodeinfo as nodeinfo where workflownodeinfo.workflowinfo.displayname=:workflowname and " +
			"workflownodeinfo.nodeinfo =nodeinfo.id and nodeinfo.nodedetail =nodedetails.id order by workflownodeinfo.id";
	
	public static final String Find_WORKFLOW_FOR_VISUALIZER="from Workflowinfo as workflowinfo,Workflownodeinfo as workflownodeinfo" +
	              "  where workflownodeinfo.status=:nodeexecutionstatus and workflowinfo.id=workflownodeinfo.workflowinfo.id";
		
	
	

}
