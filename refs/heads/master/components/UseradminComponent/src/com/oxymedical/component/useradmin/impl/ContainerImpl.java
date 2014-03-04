package com.oxymedical.component.useradmin.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.oxymedical.component.db.DBComponent;
import com.oxymedical.component.db.exception.DBComponentException;
import com.oxymedical.component.db.utilities.parameters.NameQueryParameter;
import com.oxymedical.component.useradmin.IContainer;
import com.oxymedical.component.useradmin.UserAdminComponent;
import com.oxymedical.component.useradmin.constants.Constants;
import com.oxymedical.component.useradmin.constants.SQLCommands;
import com.oxymedical.component.useradmin.exception.NoSuchContainerException;
import com.oxymedical.component.useradmin.model.Company;
import com.oxymedical.component.useradmin.model.CompanyCont;
import com.oxymedical.component.useradmin.model.Component;
import com.oxymedical.component.useradmin.model.ContComp;
import com.oxymedical.component.useradmin.model.Container;
import com.oxymedical.component.useradmin.model.ContainerType;
import com.oxymedical.component.useradmin.operations.ConnectionDatabase;
import com.oxymedical.core.containerInfo.ContainerDetails;
import com.oxymedical.core.hicUtil.IModuleDescriptor;
import com.oxymedical.core.hicUtil.TransContainer;



public class ContainerImpl implements IContainer
{
	
	DBComponent dbComponent;
	/**
	 * Setup method for container data
	 * 
	 * @param TransContainer
	 * @return Container
	 * @throws DBComponentException 
	 * @throws DocumentException 
     *  
     */
		
	public void addContainerData(DBComponent userAdminDBComponent,TransContainer containerSettings) throws DBComponentException, DocumentException{
//		UserAdminComponent.logger.log(0,"!!!!!!!!!!!!!!!ContainerImpl called");
//		UserAdminComponent.logger.log(0,"!!!Value of dbcomp is"+ userAdminDBComponent);
		dbComponent = userAdminDBComponent;
		
		Hashtable<ContainerDetails,Hashtable<String, IModuleDescriptor>> containerInfo = containerSettings.getContainerDescriptiorTable();
		Object[] containerDetailsIds = containerInfo.keySet().toArray();
		
//		UserAdminComponent.logger.log(0,"!!!!!value of containerDetailsIds"+containerDetailsIds.length);
		//iterate over each container and persist values in db
		for(int i=0; i<containerDetailsIds.length; i++){//for each container persist container id-component id
			ContainerType contType = new ContainerType();
			CompanyCont companyCont = new CompanyCont();
			Container container = new Container();
			ContainerDetails containerDetails = (ContainerDetails)containerDetailsIds[i];
			
			//set container info			
			container.setContainerId(containerDetails.getContainerId());
			contType.setContainerTypeId(containerDetails.getContainerType());
			container.setContainerType(contType);
			
			persistContData(container);

			Hashtable<String, IModuleDescriptor> module = containerInfo.get(containerDetails);
			Object[] componentIds = module.keySet().toArray();
			
			ContComp contComp = null;
			Component comp = null;
			Container cont = null;
			IModuleDescriptor modDesc = null;
//			UserAdminComponent.logger.log(0,"!!!!!value of componentIds" + componentIds.length);
			for(int j=0; j<componentIds.length; j++){//for each module persist module id-event id
				contComp = new ContComp();
				comp = new Component();
				cont = new Container();
//				UserAdminComponent.logger.log(0,"!!!!!value of componentIds" + componentIds.length);
											
				//set contComp info 
				cont.setContainerId(containerDetails.getContainerId());
				comp.setComponentId(componentIds[j].toString());
				
				contComp.setContainer(cont);
				contComp.setComponent(comp);
				
//				UserAdminComponent.logger.log(0,"!!!!!!!!!!!!!!!!ContainerImpl called-container id"+contComp.getContainer().getContainerId());
//				UserAdminComponent.logger.log(0,"!!!!!!!!!!!!!!Impl called-Component id"+contComp.getComponent().getComponentId());
				//set compCont data
				persistContData(contComp);
				
				//updateContWithUser();
											
			}
		}
		
	}
	
	/**
	 * Parser for UserCont.xml which has user-container info
	 * 
	 * @return UserCont
	 * @throws DBComponentException 
     *  
     */
	private CompanyCont parseContUserXML() throws DocumentException{
		CompanyCont companyCont = null;
		Company company = null;
		Container cont = null;
		
		String fileName = Constants.GIP_TIME_USER;
		SAXReader reader = new SAXReader();
		InputStream modulefileStream =
			this.getClass().getResourceAsStream(fileName);
		Document document = reader.read(modulefileStream);
		
		Element root = document.getRootElement();
		
		for(Iterator elemItr=root.elementIterator();elemItr.hasNext();){
			Element elem = (Element)elemItr.next();
			company = new Company();
			cont = new Container();
			if(null != elem && elem.getName().equals("contId")){
				cont.setContainerId(elem.getText());
				//companyCont.setContainer(cont);
			}
			if(null != elem && elem.getName().equals("userId")){
				company.setCompanyId(elem.getText());
				companyCont.setCompany(company);
			}
		}
			
		return companyCont;
	}
	/**
	 * Persisting data of container and user
	 * 
	 * @param Object
	 * @throws DBComponentException 
	 *  
	 *  New setUpDataConfiguration method added
	 *  This method generate dbserverurl and makes connection.
	 *  changes by wasim, 21-May-2009
	 *  
     */
	private void persistContData(Object contData) throws DBComponentException
	{
		
		dbComponent.setUpDataConfiguration(
				UserAdminComponent.dataBaseInfo.getUserName(),
				UserAdminComponent.dataBaseInfo.getPassword(),
				UserAdminComponent.dataBaseInfo.getDbName(),
				UserAdminComponent.dataBaseInfo.getDbServerName(),
				UserAdminComponent.dataBaseInfo.getDBPort(),
				UserAdminComponent.dataBaseInfo.getDbType(),
				Constants.PACKAGE_NAME,
				Constants.BASEDIR);
		//dbComponent.setUpDataConfiguration(UserAdminComponent.dataBaseInfo.getUserName(), UserAdminComponent.dataBaseInfo.getPassword(), dbServerAddress,Constants.PACKAGE_NAME,Constants.BASEDIR);
		 
		InputStream cfgInputStream = sendHibernatecfgToDB();
		dbComponent.CreateConfigurationFromInputStream(cfgInputStream);
		dbComponent.saveObject(contData);
		
		//ConnectionDatabase.GetInstanceOfDatabaseComponent().saveObject(contData);
	}
	
	public static InputStream sendHibernatecfgToDB()
	{
		InputStream	modulefileStream =ConnectionDatabase.class.getResourceAsStream(Constants.HibernatePath);
		return modulefileStream;
	
	}
	/*public static void main(String args[]){
		addContainerData(containerSettings);
		updateContWithUser();
	}*/

	public Container addContainer(String containerId, String containerTypeId) throws DBComponentException
	{
		Container container = new Container();
		container.setContainerId(containerId);
		ConnectionDatabase.GetInstanceOfDatabaseComponent().saveObject(container);
		return container;
	}

	public static Container findByContainerId(String containerId)throws NoSuchContainerException ,DBComponentException
 	{
//		UserAdminComponent.logger.log(0,"************* Inside Container ContainerImpl  Find ******");
		Container existingContainer = null;

		String sqlQuery = SQLCommands.Find_Container;
		ArrayList<NameQueryParameter> listParam = new ArrayList<NameQueryParameter>();
		NameQueryParameter containerNameId = new NameQueryParameter(Constants.containerId, containerId);
		listParam.add(containerNameId);
		List list = (List)ConnectionDatabase.GetInstanceOfDatabaseComponent().executeHSQLQueryWithNameParameter(sqlQuery,listParam);			
		if ((list == null) || (list.size()==0))
		{
			throw (new NoSuchContainerException("No container with this  Name Found"));
		}	
		else 
		{
			for(Iterator containerRow=list.iterator();containerRow.hasNext();)
			{
				Object containerRowData= containerRow.next();
				existingContainer = (Container)containerRowData;
				
			}		
		}
			
			
		return existingContainer;
		
 	}
	public void deleteContainer(String containerId) {
		// TODO Auto-generated method stub
		
	}
	
	

}
