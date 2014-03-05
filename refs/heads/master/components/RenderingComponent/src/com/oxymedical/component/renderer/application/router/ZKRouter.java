package com.oxymedical.component.renderer.application.router;

import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import com.oxymedical.component.renderer.RendererComponent;
import com.oxymedical.component.renderer.application.modeler.BaseModeler;
import com.oxymedical.component.renderer.exception.RendererComponentException;
import com.oxymedical.core.querydata.QueryData;
import com.oxymedical.core.renderdata.DataUnit;
import com.oxymedical.core.renderdata.IDataUnit;


public class ZKRouter {
	
	public void init(){
		RendererComponent.logger.log(0,"ZKRouter called");
	}
	
	public IDataUnit routeToModeler(IDataUnit modelerData){
		RendererComponent.logger.log(0,"------------Inside the ZKRouter class ---------");
        IDataUnit modelerValues = null;
		Class modelerClass;
		Method methodName = null;
		try {
			modelerClass = Class.forName("com.oxymedical.component.renderer.application.modeler.BaseModeler");
			Object modelerObject = modelerClass.newInstance();
			
			String modelerMethod = modelerData.getMethodName();
			//modelerData.setMethodName(methodName)
			RendererComponent.logger.log(0," modeler data method name is "+modelerMethod);
			RendererComponent.logger.log(0,"modeler data formid is "+modelerData.getFormId());
			if ( null != modelerMethod) {
				if (modelerMethod.equalsIgnoreCase("getGridData"))
				{
					//RendererComponent.logger.log(0,"Inside the Get grid data method ");
					methodName = modelerClass.getMethod("getGridData", new Class[]{IDataUnit.class});
					//RendererComponent.logger.log(0,"method name value is "+methodName);
					modelerValues = (IDataUnit)methodName.invoke(modelerObject, new Object[]{modelerData});
					//RendererComponent.logger.log(0,"modeler values in the ZK Router class " + modelerValues.getList());
				}
				if (modelerMethod.equalsIgnoreCase("saveForm"))
				{
					RendererComponent.logger.log(0,"Inside the Save method ");
					methodName = modelerClass.getMethod("saveForm", new Class[]{IDataUnit.class});
					RendererComponent.logger.log(0,"method name value is "+methodName);
					if ( !(modelerData.getFormValues().size()==0))
				   	modelerValues = (IDataUnit)methodName.invoke(modelerObject, new Object[]{modelerData});
					RendererComponent.logger.log(0,"modeler values in the ZK Router class " + modelerValues);
				}
				
				if (modelerMethod.equalsIgnoreCase("getList"))
				{
					RendererComponent.logger.log(0,"Inside the show grid data query method ");
					methodName = modelerClass.getMethod("getList", new Class[]{IDataUnit.class});
					//RendererComponent.logger.log(0,"method name value is "+methodName);
					modelerValues = (IDataUnit)methodName.invoke(modelerObject, new Object[]{modelerData});
					//RendererComponent.logger.log(0,"modeler values in the ZK Router class " + modelerValues.getList());
				}
				//displaying tree data
				if (modelerMethod.equalsIgnoreCase("getList"))
				{
					RendererComponent.logger.log(0,"Inside the getList query method ");
					methodName = modelerClass.getMethod("getList", new Class[]{IDataUnit.class});
					//RendererComponent.logger.log(0,"method name value is "+methodName);
					modelerValues = (IDataUnit)methodName.invoke(modelerObject, new Object[]{modelerData});
					//RendererComponent.logger.log(0,"modeler values in the ZK Router class " + modelerValues.getList());
					
				}
			
				if (modelerMethod.equalsIgnoreCase("getListData"))
				{
					RendererComponent.logger.log(0,"Inside the getList query method ");
					methodName = modelerClass.getMethod("getListData", new Class[]{IDataUnit.class});
					//RendererComponent.logger.log(0,"method name value is "+methodName);
					modelerValues = (IDataUnit)methodName.invoke(modelerObject, new Object[]{modelerData});
					//RendererComponent.logger.log(0,"modeler values in the ZK Router class " + modelerValues.getList());
					
				}
				if (modelerMethod.equalsIgnoreCase("printReport"))
				{
					RendererComponent.logger.log(0,"Inside the printReport  method ");
					methodName = modelerClass.getMethod("getListValue", new Class[]{IDataUnit.class});
					//RendererComponent.logger.log(0,"method name value is "+methodName);
					modelerValues = (IDataUnit)methodName.invoke(modelerObject, new Object[]{modelerData});
					//RendererComponent.logger.log(0,"modeler values in the ZK Router class " + modelerValues.getList());
					
				}
				if (modelerMethod.equalsIgnoreCase("invokecomponent"))
				{
					RendererComponent.logger.log(0,"--------Inside the invokecomponent  method----------");
					methodName = modelerClass.getMethod("invokecomponent", new Class[] {IDataUnit.class});
					RendererComponent.logger.log(0,"--------method name value is------- "+methodName);
					modelerValues = (IDataUnit)methodName.invoke(modelerObject, new Object[]{modelerData});
					//RendererComponent.logger.log(0,"modeler values in the ZK Router class " + modelerValues.getList());
					
				}
			
			
			}
				
			//modelerValues = (DataUnit)modelerMethod.invoke(modelerObject, new Object[0]);
			RendererComponent.logger.log(0,"get form Id "+ modelerData.getFormId());
			
		//	RendererComponent.logger.log(0,"modeler values in the ZK Router class " + modelerValues.getList());
			} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
			return modelerValues;
		
		
		
	}
	
	public static void main(String[] argc) throws RendererComponentException
    {
    
 		RendererComponent.logger.log(0,"Test Class");
 		BaseModeler bm = new BaseModeler();
 		IDataUnit requestData = new DataUnit();
 		QueryData queryData =  new QueryData();
 		queryData.setIdField("parent_id");
 		queryData.setCondition("get parent.parent_id,parent.parent_name from parent");
 		requestData.setQueryData(queryData);
 		//.setSqlQuery("get name from htable");
 		//requestData = bm.getList(requestData);
 		
 		Hashtable<String,List<String>> treeNodeValues = requestData.getQueryData().getRowValues();
		Object[] nodeIds = treeNodeValues.keySet().toArray();
		for(int i=0; i<treeNodeValues.size();i++){
			String rowId = (String)nodeIds[i];
			
			List<String> rowValues = treeNodeValues.get(nodeIds[i]);
			
			RendererComponent.logger.log(0,"rowId" + rowId);
			for(Iterator itr=rowValues.iterator();itr.hasNext();){
				String rowValue = (String)itr.next();
				RendererComponent.logger.log(0,"rowValue" + rowValue);
			}
		}
    }

}
