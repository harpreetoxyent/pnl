package com.oxymedical.component.renderer.command;


public class CommandFactory {
	
	public static IUiLibraryCompositeCommand getUiLibraryCommand(String methodName)
	{
		IUiLibraryCompositeCommand com = null;
		if (methodName.equalsIgnoreCase("StoreDataToStorage"))
		{
			com = new StoreDataToStorageCommand();
		}
		else if (methodName.equalsIgnoreCase("RetrieveDataFromStorage"))
		{
			com = new RetrieveDataFromStorageCommand();
		}
		else if (methodName.equalsIgnoreCase("extractfields"))
		{
			com = new ExtractFieldsCommand();
		}
		else if (methodName.equalsIgnoreCase("authenticateUserEx"))
		{
			com = new AuthenticateUserExCommand();
		}
		else if(methodName.equalsIgnoreCase("moveForm"))
		{
			com = new MoveFormCommand();
		}
		else if(methodName.equalsIgnoreCase("executeList")||methodName.equalsIgnoreCase("executeListUserAdmin")||methodName.equalsIgnoreCase("executeListWorkflow"))
		{
			com = new ExecuteListCommand();
		}
		else if(methodName.equalsIgnoreCase("updateRecord")||methodName.equalsIgnoreCase("updateDataWorkflow"))
		{
			com = new UpdateRecordCommand();
		}
		else if (methodName.equalsIgnoreCase("deleteUserFromApplication")
				|| methodName.equalsIgnoreCase("SoftDeleteUser")
				|| methodName.equalsIgnoreCase("getComboDataFromUserAdmin")
				|| methodName.equalsIgnoreCase("GetFields")
				|| methodName.equalsIgnoreCase("GetRights")
				|| methodName.equalsIgnoreCase("GetListUserAdmin"))
		{
			com = new GeneralUserAdminCommand();
		}
		else if (methodName.equalsIgnoreCase("changedostatusdynamic")
				|| methodName.equalsIgnoreCase("processNextWorkflowTool"))
		{
			com = new ChangeDOStatusDynamicWorkflowCommand();
		}
		else if(methodName.equalsIgnoreCase("changeDOStatus"))
		{
			com = new ChangeDOStatusCommand();
		}
		else if(methodName.equalsIgnoreCase("reportData"))
		{
			com = new ReportDataCommand();
		}
		else if(methodName.equalsIgnoreCase("save")||methodName.equalsIgnoreCase("addUserFromApplication")||methodName.equalsIgnoreCase("AddUserPattern")
				||methodName.equalsIgnoreCase("AddNewNodeFromUI"))
		{
			com = new SaveCommand();
		}
		else if(methodName.equalsIgnoreCase("invokeClientCall")|| methodName.equalsIgnoreCase("retrivedata"))
		{
			com = new InvokeClientCallCommand();
		}
		else if(methodName.equalsIgnoreCase("sqlQuery"))
		{
			com = new SqlQueryCommand();
		}
		else if(methodName.equalsIgnoreCase("queryBuilder"))
		{
			com = new QueryBuilderCommand();
		}
		else if(methodName.equalsIgnoreCase("dbStructure")||methodName.equalsIgnoreCase("DeleteWorkflow"))
		{
			com = new GetDbStructureCommnad();
		}
		else if(methodName.equalsIgnoreCase("createquery"))
		{
			com = new CreateQueryCommand();
		}
		else if(methodName.equalsIgnoreCase("logoutUser"))
		{
			com = new CreateLogoutCommand();
		}
		else if(methodName.equalsIgnoreCase("exportCSV"))
		{
			com = new createExportCSVCommand();
		}
		else if(methodName.equalsIgnoreCase("exportToEpic") || methodName.equalsIgnoreCase("BrainKParamInfo") || methodName.equalsIgnoreCase("getStudyListfromPACS"))
		{
			com = new ExportToEpicCommand();
		}
		else if(methodName.equalsIgnoreCase("ImportHL7Data"))
		{
			com = new ImportFromEpicCommand();
		}
		else if(methodName.equalsIgnoreCase("SendMail"))
		{
			com = new CommunicationCommand();
		}
		else if(methodName.equalsIgnoreCase("AttachElectronicSignature"))
		{
			com = new AttachSignatureCommand();
		}
		else if (methodName.equalsIgnoreCase("alignEdf"))
		{
			com = new PSGCommand();
		}
		else if(methodName.equalsIgnoreCase("scoring")){
			com = new PSGCommand();
		}
		else if(methodName.equalsIgnoreCase("ProcessRevertOperation")|| methodName.equalsIgnoreCase("ProcessStopOperation"))
		{
			com = new RevertWorkflowStatusCommand();
		}
		else if(methodName.equalsIgnoreCase("getworkflowtoolstatus"))
		{
			com = new GetWorkFlowStatusToolCommand();
		}
		
		else if(methodName.equalsIgnoreCase("OpenVisualiser"))
		{
			com = new OpenVisualizer();
		}
		else if(methodName.equalsIgnoreCase("authenticateUserInLDAP") || methodName.equalsIgnoreCase("searchInLDAP"))
		{
			com = new LDAPCommand();
		}
		else 
		{
			com = new GenericCommand();
		}
		return com;
	}

}
