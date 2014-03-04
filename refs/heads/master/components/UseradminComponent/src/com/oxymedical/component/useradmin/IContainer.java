package com.oxymedical.component.useradmin;


import org.dom4j.DocumentException;

import com.oxymedical.component.db.DBComponent;
import com.oxymedical.component.db.exception.DBComponentException;
import com.oxymedical.component.useradmin.model.Container;
import com.oxymedical.core.hicUtil.TransContainer;


public interface IContainer
{
	public void addContainerData(DBComponent userAdminDBComponent,TransContainer containerSettings) throws DBComponentException, DocumentException;
	public Container addContainer(String containerId , String containerTypeId )throws DBComponentException;
	public void deleteContainer(String containerId);
	
 
	
}
